# -*- coding: utf-8 -*-
"""
Éditeur de Spyder

Ceci est un script temporaire.
"""

import cv2
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np


def seuillageGris(img, seuil):
    image = Image.open(img)
    ImageTab = np.array(image)
    imgHSV = cv2.cvtColor(ImageTab, cv2.COLOR_RGB2HSV)
    dim = imgHSV.shape
    res = np.zeros((dim[0], dim[1]))
    for i in range(dim[0]):
        for j in range(dim[1]):
            if imgHSV[i][j][2] < seuil:
                imgHSV[i][j][2] = 0
                res[i, j] = 1
            else:
                imgHSV[i][j][2] = 255
                imgHSV[i][j][1] = 0
    return res


resultat = seuillageGris("images de test/Bracelets cadre épais/im1.jpg", 100)

plt.imshow(resultat)
plt.show()
