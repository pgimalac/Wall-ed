#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May  1 10:52:28 2020

@author: romain
"""

import cv2

def read():
    try:
        while True:
            cap = cv2.VideoCapture('/home/walled/pact32/modules/CONVA/regard3bis.mkv')
            cv2.namedWindow("frame", cv2.WND_PROP_FULLSCREEN)
            cv2.setWindowProperty("frame", cv2.WND_PROP_FULLSCREEN, cv2.WINDOW_FULLSCREEN)

            if not cap.isOpened():
                print("Error opening video file")

            while cap.isOpened():
                ret, frame = cap.read()
                if ret:

                    cv2.imshow('frame', frame)
                    if cv2.waitKey(25) & 0xFF == ord('q'):
                        break
                else:
                    break
    finally:
        cap.release()
        cv2.destroyAllWindows()

if __name__ == '__main__':
    read()
