# -*- coding: utf-8 -*-
"""
Created on Mon Jan  6 17:30:46 2020

@author: Nico
"""

import cv2
from PIL import Image
import numpy as np


def convertToHSI(img):
    image = Image.open(img)
    ImageTab = np.array(image)
    return cv2.cvtColor(ImageTab, cv2.COLOR_RGB2HSV)


def histo(img, imgConnex, n):
    ImHSV = convertToHSI(img)
    histogramme = np.zeros((256, 2))
    for i in range(256):
        histogramme[i, 0] = i
    dim = ImHSV.shape
    for i in range(dim[0]):
        for j in range(dim[1]):
            if imgConnex[i][j] == n:
                histogramme[ImHSV[i][j][0], 1] += 1
    return histogramme
