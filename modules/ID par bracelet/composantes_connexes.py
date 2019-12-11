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

def HashCreation():
    return []

def HashAjout(table, cle, elem):
    table.append([cle, elem])

def HashRecup(table, cle):
    for elem in table:
        if elem[0] == cle:
            return elem[1]

def HashExiste(table, cle):
    for elem in table:
        if elem[0] == cle:
            return True

def HashModif(table, cle, newVal):
    for elem in table:
        if elem[0] == cle:
            elem[1] = newVal

def HashSuppr(table, cle):
    for i in range(len(table)):
        if table[i][0] == cle:
            table.pop(i)