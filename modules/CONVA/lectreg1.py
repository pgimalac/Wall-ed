#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May  1 10:05:17 2020

@author: romain
"""

from tkinter import*
import cv2

def read():
    try:
        while 1:
            cap = cv2.VideoCapture('regard1bis.mkv')
            height = cap.get(cv2.CAP_PROP_FRAME_HEIGHT)
            width = cap.get(cv2.CAP_PROP_FRAME_WIDTH)
            print(height, width)
            cv2.namedWindow("frame", cv2.WND_PROP_FULLSCREEN)
            cv2.setWindowProperty("frame",cv2.WND_PROP_FULLSCREEN,cv2.WINDOW_FULLSCREEN)

            if not cap.isOpened():
                print("Error opening video file")

            while cap.isOpened():
                ret, frame = cap.read()
                if ret:

                    cv2.imshow('frame', frame)
                    if cv2.waitKey(24) & 0xFF == ord('q'):
                        break
                else:
                    break

        cap.release()
        cv2.destroyAllWindows()
    except KeyboardInterrupt:
        cap.release()
        cv2.destroyAllWindows()


if __name__ == '__main__':
    read()
