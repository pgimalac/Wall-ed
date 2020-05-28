import time
import sys
import json
import cv2

from SLAM.car import Car
from COMCS import clientRobot
from CONVA import proposition2, recherche, noms

imgPath = "/tmp/cv2-img.png"

car = Car(debug=True)
rawStudents = json.loads(clientRobot.initConnexion())

nbEleves = rawStudents["numberOfStudents"]
students = {}
for i in range(nbEleves):
    students[rawStudents["ids"][i]] = rawStudents["firstnames"], rawStudents["lastnames"]

conva = recherche.read()
tries = 20
try:
    while True:
        car.randomMove()
        img = car.capture()
        if img is None:
            print("Photo error", file=sys.stderr)
            continue

        cv2.imwrite(imgPath, img)
        rep = json.loads(clientRobot.sendImage(imgPath))
        if not rep:
            continue

        for trash, pos in rep.items():
            if len(pos) != 2:
                print("Invalid coordinates (two values needed)", file=sys.stderr)
                continue
            x = int(pos[0])
            y = int(pos[1])
            width = img.size
            height = img[0].size
            print(trash, x, y, width, height)
            for _ in range(tries):
                if car.goNear(width, height, x, y, 0, 0):
                    # on est à côté d'un déchet
                    ret, t = proposition2.askForWaste(trash, conva)
                    if ret is not None:
                        noms.affiche_noms(noms.choix_noms(list(map(lambda x: x[0], students.values()))))
                        # TODO récupérer un id
                        clientRobot.interactionAnswer(True, 0, trash, t, ret) #TODO bracelet id
                        time.sleep(3)
                    recherche.read(c=conva)
                    break
            else:
                print("Could not reach target after {} tries".format(tries))
            break

except KeyboardInterrupt:
    print("Interrupted !", file=sys.stderr)
finally:
    car.speed = 0
    car.turn_straight()
    car.stop()
    conva.stop()
    clientRobot.stopConnexion()
