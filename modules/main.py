import sys
import json
import cv2

from SLAM.car import Car
from COMCS import clientRobot

car = Car(debug=True)
eleves = json.loads(clientRobot.initConnexion())

try:
    car.turn_straight()
    car.forward()
    car.speed = 30

    for i in range(121):
        car.turn((i % 11) - 5)
        car.camera_to_position(85 + (i % 11), 95 - (i % 11))

        if i % 10 == 0:
            img = car.capture()
            if img is None:
                print("Photo error")
            else:
                cv2.imwrite("/tmp/cv2-%i.png" % i, img)
except KeyboardInterrupt:
    print("Interrupted !", file=sys.stderr)
finally:
    car.turn_straight()
    car.stop()
