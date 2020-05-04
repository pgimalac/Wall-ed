#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May  1 10:52:28 2020

@author: romain
"""

import numpy as np
import cv2

def lire(chn=0):

    cap = cv2.VideoCapture('regard3.mkv')
    cv2.namedWindow("frame", cv2.WND_PROP_FULLSCREEN)
    cv2.setWindowProperty("frame",cv2.WND_PROP_FULLSCREEN,cv2.WINDOW_FULLSCREEN)
    
    if (cap.isOpened() == False):
        print("Error opening video file")
        
    while(cap.isOpened()):
        ret, frame = cap.read()
        if ret == True:
    
            cv2.imshow('frame',frame)
            if cv2.waitKey(25) & 0xFF == ord('q'):
                break
        else:
            break
    
    cap.release()
    cv2.destroyAllWindows()
    exec(open("./lectreg1.py").read())
    
if __name__ == '__main__':
    lire()