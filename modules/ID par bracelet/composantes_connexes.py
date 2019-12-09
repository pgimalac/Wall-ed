'''
Algo de reconaissance des composantes connexes d'un tableau composé de cases ayant soit 0 soit 1 pour valeur
image : tableau de 0 et 1
'''

import numpy as np

(a, b) = image.shape()

def main():
    if not verif_image(image):
        return "mauvais format"


def verif_image(ima):
    for i in range(a):
        for j in range(b):
            val = image[i,j]
            if not (val == 1 or val == 0):
                return False
    return True