import sys
import json
import cv2

from SLAM.car import Car
from COMCS import clientRobot

imgPath = "/tmp/cv2-img.png"

car = Car(debug=True)
eleves = json.loads(clientRobot.initConnexion())

count = 20
try:
    for _ in range(count):
        car.randomMove()
        img = car.capture()
        if img is None:
            print("Photo error", file=sys.stderr)
        else:
            cv2.imwrite(imgPath, img)
            rep = json.loads(clientRobot.sendFile(imgPath))
            # TODO gérer la réponse du serveur

except KeyboardInterrupt:
    print("Interrupted !", file=sys.stderr)
finally:
    car.speed = 0
    car.turn_straight()
    car.stop()
    clientRobot.stopConnexion()
