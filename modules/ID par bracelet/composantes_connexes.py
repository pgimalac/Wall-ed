'''
Algo de reconaissance des composantes connexes d'un tableau composé de cases ayant soit 0 soit 1 pour valeur
image : tableau de 0 et 1
'''

import numpy as np

## MAIN

(a,b) = np.shape(image)

def main():
    if not verif_image(image):
        return "mauvais format"
    composantes = HashCreation()
    etiquette = 0
    correspondances = [0]
    for i in range(a):
        for j in range(b):
            if image[i,j] == 1:
                etiquettesPrec = numsPrecedent(composantes, (i,j))
                long = len(etiquettesPrec)
                if long == 0:
                    HashAjout(composantes, (i,j), etiquette)
                    etiquette += 1
                    correspondances.append(etiquette)
                elif long == 1:
                    HashAjout(composantes, (i,j), etiquettesPrec[0])
                else:
                    num1 = etiquettesPrec[0]
                    num2 = etiquettesPrec[1]
                    HashAjout(composantes, (i,j), num1)
                    if num1 > num2:
                        correspondances[num1] = num2
                    elif num1 == num2:
                        None
                    else:
                        correspondances[num2] = num1
    triCorrespondances(correspondances)
    for elem in composantes:
        elem[1] = correspondances[elem[1]]
    for i in range(a):
        for j in range(b):
            if image[i,j] == 1:
                image[i,j] = HashRecup(composantes, (i,j)) + 1

## FONCTIONS

def triCorrespondances(tab):
    for k in range(len(tab)):
        temp = tab[k]
        tab[k] = tab[temp]
    k = 1
    while k < len(tab):
        continu = True
        val = tab[k]
        valinf = tab[k-1]
        if val > valinf:
            tab[k] = valinf + 1
        if k == len(tab) - 1:
            break
        while continu:
            k += 1
            if tab[k] == val:
                tab[k] = valinf + 1
            else:
                continu = False

def verif_image(ima):
    for i in range(a):
        for j in range(b):
            val = image[i,j]
            if not (val == 1 or val == 0):
                return False
    return True

def numsPrecedent(table, pixel):
    (i, j) = pixel
    res = []
    if i > 0 and image[i-1,j] == 1:
        res.append(HashRecup(table, (i-1,j)))
    if j > 0 and image[i,j-1] == 1:
        res.append(HashRecup(table, (i, j-1)))
    return res

## TABLE DE HASHAGE

# pas vraiment une table de hashage

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