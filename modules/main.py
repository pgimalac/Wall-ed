import RPi.GPIO as GPIO
import cv2
import time
import sys
import json
import cv2

from SLAM.car import Car
from COMCS import clientRobot
from CONVA import proposition2, recherche, noms
from IDB import prog

imgPath = "/tmp/cv2-img.png"

car = Car(debug=True)
rawStudents = clientRobot.initConnexion()

# pour l'instant on n'a pas besoin des noms des élèves
# (on n'affiche pas leur nom, premier arrivé premier servi)
# nbEleves = rawStudents["numberOfStudents"]
# students = {}
# for i in range(nbEleves):
#     ident = rawStudents["IDs"][i]
#     students[ident] = rawStudents["firstNames"][str(i)], rawStudents["lastNames"][str(i)]

conva = recherche.read()

def scan_bracelet():
    pin1 = 16
    pin2 = 20                              #broche utilisé en entrée

    GPIO.setwarnings(False)                 #désactive le mode warning
    GPIO.setmode(GPIO.BCM)                  #utilisation des numéros de ports du
                                            #processeur
    GPIO.setup(pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

    conva.stop()

    cv2.namedWindow("Capturing", cv2.WND_PROP_FULLSCREEN)
    cv2.setWindowProperty("Capturing", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

    car.camera_to_position(90, 150)

    imgs = []
    try:
        while True:
            frame = car.capture()
            if frame is None:
                print("Image error")
                car.camera_to_position(90, 90)
                return "J", "R"
            cv2.imshow("Capturing", frame)
            if not GPIO.input(pin1) or not GPIO.input(pin2):
                break

            cv2.waitKey(1)

        for _ in range(1):
            frame = car.capture()
            if frame is None:
                print("Image error")
                continue
            imgs.append(frame)
            time.sleep(0.1)
        return prog.mainServeur(imgs)
    finally:
        car.camera_to_position(90, 90)
    return "Jaune", "Rouge"

tries = 20
try:
    while True:
        car.randomMove()
        img = car.capture()
        if img is None:
            print("Photo error", file=sys.stderr)
            continue

        cv2.imwrite(imgPath, img)
        rep = clientRobot.sendImage(imgPath)

        for trash, pos in rep.items():
            if len(pos) != 2:
                print("Invalid coordinates (two values needed)", file=sys.stderr)
                continue
            x, y = map(int, pos)
            width = img[0].size
            height = img.size
            print(trash, x, y, width, height)
            for _ in range(tries):
                if car.goNear(width, height, x, y):
                    # on est à côté d'un déchet
                    # c1, c2 = scan_bracelet()
                    ret, t = proposition2.askForWaste(trash, conva)
                    if ret is not None:
                        # clientRobot.interactionAnswer(True, "".join([c1[0], c2[0]]), trash, t, ret)
                        clientRobot.interactionAnswer(True, "YR", trash, t, ret)
                        time.sleep(3)
                    else:
                        clientRobot.interactionAnswer(False, None, None, None, None)
                    recherche.read(c=conva)
                    break

                clientRobot.interactionAnswer(False, None, None, None, None)
                img = car.capture()
                if img is None:
                    print("Photo error", file=sys.stderr)
                    continue

                cv2.imwrite(imgPath, img)
                rep = clientRobot.sendImage(imgPath).get(trash, default=None)
                if rep is None:
                    print("Trash lost")
                    break
                x, y = rep

            else:
                print("Could not reach target after {} tries".format(tries))
                clientRobot.interactionAnswer(False, None, None, None, None)
                break
            break
        else:
            clientRobot.interactionAnswer(False, None, None, None, None)


except KeyboardInterrupt:
    print("Interrupted !", file=sys.stderr)
finally:
    car.speed = 0
    car.turn_straight()
    car.stop()
    conva.stop()
    clientRobot.stopConnexion()
