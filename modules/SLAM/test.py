# Code taken from
# https://github.com/kevinam99/capturing-images-from-webcam-using-opencv-python/blob/master/webcam-capture-v1.01.py
# (no licence) and then modified.

import os
import sys
import cv2


def find_video():
    path = '/dev/'
    for _, _, f in os.walk(path):
        for file in f:
            if file.startswith("video"):
                cam = cv2.VideoCapture(int(file[5:]))

                if cam.isOpened():
                    print("CHOOSE", file)
                    return cam

                cam.release()

    return None


webcam = find_video()

if webcam is None:
    sys.exit(0)

while True:
    try:
        check, frame = webcam.read()
        print(len(frame[0]), len(frame))
        # print(check) #prints true as long as the webcam is running
        # print(frame) #prints matrix values of each framecd
        cv2.imshow("Capturing", frame)

        key = cv2.waitKey(1)
        if key == ord('s'):  # takes a picture
            cv2.imwrite(filename='saved_img.jpg', img=frame)
            webcam.release()
            img_new = cv2.imread('saved_img.jpg', cv2.IMREAD_GRAYSCALE)
            img_new = cv2.imshow("Captured Image", img_new)
            cv2.waitKey(1650)
            cv2.destroyAllWindows()
            print("Processing image...")
            img_ = cv2.imread('saved_img.jpg', cv2.IMREAD_ANYCOLOR)
            print("Converting RGB image to grayscale...")
            gray = cv2.cvtColor(img_, cv2.COLOR_BGR2GRAY)
            print("Converted RGB image to grayscale...")
            print("Resizing image to 28x28 scale...")
            img_ = cv2.resize(gray, (28, 28))
            print("Resized...")
            img_resized = cv2.imwrite(filename='saved_img-final.jpg', img=img_)
            print("Image saved!")
            break

        if key == ord('q'):  # stops the camera
            print("Turning off camera.")
            webcam.release()
            print("Camera off.")
            print("Program ended.")
            cv2.destroyAllWindows()
            break

    except KeyboardInterrupt:
        print("Turning off camera.")
        webcam.release()
        print("Camera off.")
        print("Program ended.")
        cv2.destroyAllWindows()
        break
