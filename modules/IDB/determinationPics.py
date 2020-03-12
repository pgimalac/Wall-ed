import math
import numpy as np

def determinationPics(histogramme):
    premierPic = max(histogramme)
    deuxiemePic = max2(histogramme)
    return (premierPic,deuxiemePic)

def max(tab):
    max = -math.inf
    indice_max = -1
    for i in range(len(tab)):
        if tab[i,1] > max:
            max = tab[i,1]
            indice_max = tab[i,0]
    return indice_max

def max2(tab,max1):
    max = -math.inf
    indice_max = -1
    for i in range(len(tab)):
        if (i < (max1 - 64) or i > (max1 + 64)) and tab[i,1] > max:
            max = tab[i,1]
            indice_max = tab[i,0]
    return indice_max