'''
Algo de reconaissance des composantes connexes d'un tableau composé de cases ayant soit 0 soit 1 pour valeur
image : tableau de 0 et 1
'''

import numpy as np

## MAIN

def main(ima):
    img = np.array(ima)
    (a,b) = np.shape(img)
    inversionImage(img)
    determinationComposantes(img)
    interieur = determineInterieurCadre(img)
    return (img,interieur)

# renvoie le numéro de la plus grande composante connexe de l'inverse de l'image qui ne touche pas le bord, donc l'intérieur du cadre
def determineInterieurCadre(image):
    (a,b) = np.shape(image)
    composantes = [0]
    for i in range(a):
        for j in range(b):
            if image[i,j] not in composantes:
                composantes.append(image[i,j])
    composantesBord = [0]
    for i in range(a):
        if image[i,0] not in composantesBord:
            composantesBord.append(image[i,0])
        if image[i,b-1] not in composantesBord:
            composantesBord.append(image[i,b-1])
    for j in range(b):
        if image[0,j] not in composantesBord:
            composantesBord.append(image[0,j])
        if image[a-1,j] not in composantesBord:
            composantesBord.append(image[a-1,j])
    tailleComposantesPasBord = HashCreation()
    for elem in composantes:
        if elem not in composantesBord:
            HashAjout(tailleComposantesPasBord, elem, 0)
    for i in range(a):
        for j in range(b):
            pixel = image[i,j]
            if HashExiste(tailleComposantesPasBord, pixel):
                newVal = HashRecup(tailleComposantesPasBord, pixel) + 1
                HashModif(tailleComposantesPasBord, pixel, newVal)
    max = 0
    plusGrandeComposante = -1
    for couple in tailleComposantesPasBord:
        if couple[1] > max:
            max = couple[1]
            plusGrandeComposante = couple[0]
    return plusGrandeComposante

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

def verif_image(image):
    (a,b) = np.shape(image)
    for i in range(a):
        for j in range(b):
            val = image[i,j]
            if not (val == 1 or val == 0):
                return False
    return True

def numsPrecedent(table, pixel, image):
    (i, j) = pixel
    res = []
    if i > 0 and image[i-1,j] == 1:
        res.append(HashRecup(table, (i-1,j)))
    if j > 0 and image[i,j-1] == 1:
        res.append(HashRecup(table, (i, j-1)))
    return res

def inversionImage(image):
    (a,b) = np.shape(image)
    for i in range(a):
        for j in range(b):
            image[i,j] = (image[i,j] + 1)%2

def determinationComposantes(image):
    (a,b) = np.shape(image)
    if not verif_image(image):
        return "mauvais format d'image"
    composantes = HashCreation()
    etiquette = 0
    correspondances = [0]
    for i in range(a):
        for j in range(b):
            if image[i,j] == 1:
                etiquettesPrec = numsPrecedent(composantes, (i,j),image)
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

## Dictionnaire

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