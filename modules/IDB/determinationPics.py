import math
import numpy as np

def tableauCorresp():
    tab = []
    for i in range(21):
        tab.append("ROUGE")
    for i in range(43):
        tab.append("VERT")
    for i in range(43):
        tab.append("TURQUOISE")
    for i in range(42):
        tab.append("BLEU")
    for i in range(43):
        tab.append("MAGENTA")
    for i in range(42):
        tab.append("JAUNE")
    for i in range(22):
        tab.append("ROUGE")
    return np.array(tab)

def determinationPics(histogramme):
    premierPic = max(histogramme)
    deuxiemePic = max2(histogramme, premierPic)
    return (premierPic,deuxiemePic)

def determinationLABEL(histogramme):
    p1,p2 = determinationPics(histogramme)
    t = tableauCorresp()
    return (t[p1], t[p2])

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