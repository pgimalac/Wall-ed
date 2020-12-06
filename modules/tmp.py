# une copie du main mais qui fonctionne sans communication avec le serveur
# l'utilisateur rentre à la main les coordonnées des déchets sur l'image
# pratique pour faire des tests du main quand le serveur n'est pas disponible
# et pour tester l'approche d'un déchet

import time
import sys
import cv2
import RPi.GPIO as GPIO

from SLAM.car import Car
# from COMCS import clientRobot
from CONVA import proposition2, recherche, content, triste
from IDB import prog

# chemin où seront écrites les images prises
imgPath = "/tmp/cv2-img.png"

# le nombre d'images prise et envoyées à la reconnaissance de bracelet
nb_images = 1

# nombre d'essais pour s'approcher d'un dêchet
# si on n'est pas assez proche du dêchet au bout de ce nombre d'essais on abandonne
tries = 20

car = Car(debug=True)
conva = recherche.read()
# rawStudents = clientRobot.initConnexion()

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
    pin2 = 20  #broche utilisé en entrée
    #processeur
    GPIO.setup(pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)

    # l'écran et les leds modifient les couleurs perçues par la reconnaissance de bracelet
    # conva.stop()

    # cv2.namedWindow("Capturing", cv2.WND_PROP_FULLSCREEN)
    # cv2.destroyWindow("Capturing")
    # cv2.setWindowProperty("Capturing", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

    # on incline la caméra vers le haut
    # car.camera_to_position(90, 150)

    # le tableau d'images que l'on va donner à la caméra
    imgs = []
    try:
        # on affiche ce que la caméra voit pour bien s'aligner
        while True:
            frame = car.capture()
            if frame is None:
                print("Image error")
                return "Jaune", "Rouge"
            # cv2.imshow("Capturing", frame)
            if not GPIO.input(pin1) or not GPIO.input(pin2):
                # si l'un des boutons a été appuyé, on sort de la boucle
                break

            # sans cet appel l'affichage ne se fait pas...
            # cv2.waitKey(1)

        for _ in range(nb_images):
            frame = car.capture()
            if frame is None:
                print("Image error")
                continue
            imgs.append(frame)
            time.sleep(1. / nb_images)
        return "Y", "R"
        return prog.mainServeur(imgs)
    finally:
        car.camera_to_position(90, 90)
        cv2.destroyAllWindows()
    return "Jaune", "Rouge"


time.sleep(3)

scan_bracelet()
time.sleep(3)

# proposition2.askForWaste("glass", conva)
content.read(c=conva)
time.sleep(3)

recherche.read(c=conva)
sys.exit(0)

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
        # cv2.imwrite(imgPath, img)
        # rep = clientRobot.sendImage(imgPath)
        cv2.imshow("Show", img)
        cv2.waitKey(3000)
        print("Entrer les coordonnées du déchet: ", end='')
        x, y = map(int, input().split())
        rep = {"glass": (x, y)}

        for trash, pos in rep.items():
            if len(pos) != 2:
                print("Invalid coordinates (two values needed)",
                      file=sys.stderr)
                continue

            x, y = map(int, pos)
            height, width, _ = img.shape
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
                        # clientRobot.interactionAnswer(True, "".join([c1[0], c2[0]]), trash, t, ret)
                        time.sleep(5)
                    recherche.read(c=conva)
                    break

                img = car.capture()
                if img is None:
                    print("Photo error", file=sys.stderr)
                    continue

                cv2.imshow("Show", img)
                cv2.waitKey(3000)
                print("Entrer les coordonnées du déchet: ", end='')
                x, y = map(int, input().split())
                rep = {"glass": (x, y)}

                if rep.get(trash, None) is None:
                    # le type de déchet n'apparait plus...
                    print("Trash lost")
                    break
                x, y = rep[trash]
            else:
                print("Could not reach target after {} tries".format(tries))
            break

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
    # clientRobot.stopConnexion()
