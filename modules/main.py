# le fichier principal du projet, controle le robot, la coque, communique avec le serveur, etc

import time
import sys
import cv2
import RPi.GPIO as GPIO

from SLAM.car import Car
from COMCS import clientRobot
from CONVA import proposition2, recherche
from IDB import prog

# chemin où seront écrites les images prises
imgPath = "/tmp/cv2-img.png"

# le nombre d'images prise et envoyées à la reconnaissance de bracelet
nb_images = 4

# nombre d'essais pour s'approcher d'un dêchet
# si on n'est pas assez proche du dêchet au bout de ce nombre d'essais on abandonne
tries = 20


car = Car(debug=True)
conva = recherche.read()
rawStudents = clientRobot.initConnexion()

# pour l'instant on n'a pas besoin des noms des élèves
# (on n'affiche pas leur nom, premier arrivé premier servi)
# nbEleves = rawStudents["numberOfStudents"]
# students = {}
# for i in range(nbEleves):
#     ident = rawStudents["IDs"][i]
#     students[ident] = rawStudents["firstNames"][str(i)], rawStudents["lastNames"][str(i)]

def scan_bracelet():
    # pour lire les boutons
    pin1 = 16
    pin2 = 20                              #broche utilisé en entrée

    GPIO.setwarnings(False)                 #désactive le mode warning
    GPIO.setmode(GPIO.BCM)                  #utilisation des numéros de ports du
                                            #processeur
    GPIO.setup(pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

    # l'écran et les leds modifient les couleurs perçues par la reconnaissance de bracelet
    conva.stop()

    cv2.namedWindow("Capturing", cv2.WND_PROP_FULLSCREEN)
    cv2.setWindowProperty("Capturing", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

    # on incline la caméra vers le haut
    car.camera_to_position(90, 150)

    # le tableau d'images que l'on va donner à la caméra
    imgs = []
    try:
        # on affiche ce que la caméra voit pour bien s'aligner
        while True:
            frame = car.capture()
            if frame is None:
                print("Image error")
                return "Jaune", "Rouge"
            cv2.imshow("Capturing", frame)
            if not GPIO.input(pin1) or not GPIO.input(pin2):
                # si l'un des boutons a été appuyé, on sort de la boucle
                break

            # sans cet appel l'affichage ne se fait pas...
            cv2.waitKey(1)

        for _ in range(nb_images):
            frame = car.capture()
            if frame is None:
                print("Image error")
                continue
            imgs.append(frame)
            time.sleep(1. / nb_images)
        return prog.mainServeur(imgs)
    finally:
        car.camera_to_position(90, 90)
    return "Jaune", "Rouge"

try:
    while True:
        # on bouge de manière aléatoire
        car.randomMove(delay=3)

        # on prend une photo
        img = car.capture()
        if img is None:
            print("Photo error", file=sys.stderr)
            continue

        # on l'envoie au serveur et on regarde si des dêchets sont trouvés
        cv2.imwrite(imgPath, img)
        rep = clientRobot.sendImage(imgPath)

        answered = False
        for trash, pos in rep.items():
            if len(pos) != 2:
                print("Invalid coordinates (two values needed)", file=sys.stderr)
                continue

            x, y = map(int, pos)
            width = img[0].size
            height = img.size
            print(trash, x, y, width, height)
            # on essaie de s'approcher du déchet
            for _ in range(tries):
                if car.goNear(width, height, x, y):
                    # on est à côté d'un déchet
                    # on scanne le bracelet pour savoir avec qui on communique
                    c1, c2 = scan_bracelet()
                    # on affiche notre proposition de déchet
                    ret, t = proposition2.askForWaste(trash, conva)
                    if ret is not None:
                        # ret est vrai/faux selon si on a donné le bon type de déchet
                        clientRobot.interactionAnswer(True, "".join([c1[0], c2[0]]), trash, t, ret)
                        answered = True
                        time.sleep(5)
                    recherche.read(c=conva)
                    break

                img = car.capture()
                if img is None:
                    print("Photo error", file=sys.stderr)
                    continue

                if not answered:
                    # cette fonction doit être appelée pour indiquer au serveur si un déchet a été atteint
                    clientRobot.interactionAnswer(False, None, None, None, None)
                    answered = True

                cv2.imwrite(imgPath, img)
                # on renvoie une nouvelle image et on cherche le même type de déchet
                rep = clientRobot.sendImage(imgPath).get(trash, default=None)
                answered = False
                if rep is None:
                    # le type de déchet n'apparait plus...
                    print("Trash lost")
                    break
                x, y = rep
            else:
                print("Could not reach target after {} tries".format(tries))
            break

        if not answered:
            # cette fonction doit être appelée pour indiquer au serveur si un déchet a été atteint
            clientRobot.interactionAnswer(False, None, None, None, None)


except KeyboardInterrupt:
    print("Interrupted !", file=sys.stderr)
finally:
    # arrêt de la voiture
    car.speed = 0
    car.turn_straight()
    car.stop()
    # arrêt de la coque
    conva.stop()
    # arrêt de la connexion au serveur
    clientRobot.stopConnexion()
