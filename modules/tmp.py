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
# rawStudents = json.loads(clientRobot.initConnexion())

# nbEleves = rawStudents["numberOfStudents"]
# students = {}
# for i in range(nbEleves):
#     students[rawStudents["ids"][i]] = rawStudents["firstnames"], rawStudents["lastnames"]

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
    car.camera_to_position(90, 140)

    try:
        while True:
            frame = car.capture()
            if frame is None:
                print("Image error")
                return "J", "R"
            cv2.imshow("Capturing", frame)
            cv2.waitKey(1)
            if not GPIO.input(pin1) or not GPIO.input(pin2):
                break

        imgs = []
        for _ in range(8):
            cv2.imwrite(filename='saved_img.jpg', img=frame)
            img_new = cv2.imread('saved_img.jpg', cv2.IMREAD_GRAYSCALE)
            time.sleep(0.1)

        print("ok")
        return prog.mainServeur(imgs)
    finally:
        car.camera_to_position(90, 90)
    return "J", "R"


# print(scan_bracelet())
# sys.exit(0)

tries = 20
try:
    while True:
        car.randomMove(delay=3)
        continue
        img = car.capture()
        if img is None:
            print("Photo error", file=sys.stderr)
            continue

        cv2.imwrite(imgPath, img)
        rep = json.loads(clientRobot.sendImage(imgPath))

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
                    ret, t = proposition2.askForWaste(trash, conva)
                    if ret is not None:
                        c1, c2 = scan_bracelet()
                        clientRobot.interactionAnswer(True, "".join([c1[0], c2[0]]), trash, t, ret)
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
                rep = json.loads(clientRobot.sendImage(imgPath)).get(trash, default=None)
                if rep is None:
                    print("Trash lost")
                    break
                x, y = rep

            else:
                print("Could not reach target after {} tries".format(tries))
                clientRobot.interactionAnswer(False, None, None, None, None)
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
    # clientRobot.stopConnexion()
