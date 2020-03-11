'''
Algo de reconaissance des composantes connexes d'un tableau composé de cases ayant soit 0 soit 1 pour valeur
image : tableau de 0 et 1
'''

import numpy as np

## MAIN

def main(img):
    a, b = np.shape(img)
    inversionImage(img)
    determinationComposantes(img)
    interieur = determineInterieurCadre(img)
    return (img, interieur)

# renvoie le numéro de la plus grande composante connexe de l'inverse de l'image qui ne touche pas le bord, donc l'intérieur du cadre
def determineInterieurCadre(image):
    a, b = np.shape(image)
    composantes = [0]
    for i in range(a):
        for j in range(b):
            if image[i, j] not in composantes:
                composantes.append(image[i, j])
    composantesBord = [0]
    for i in range(a):
        if image[i, 0] not in composantesBord:
            composantesBord.append(image[i, 0])
        if image[i, b-1] not in composantesBord:
            composantesBord.append(image[i, b-1])
    for j in range(b):
        if image[0, j] not in composantesBord:
            composantesBord.append(image[0, j])
        if image[a-1, j] not in composantesBord:
            composantesBord.append(image[a-1, j])
    tailleComposantesPasBord = []
    for elem in composantes:
        if elem not in composantesBord:
            tailleComposantesPasBord.append([elem, 0])
    for i in range(a):
        for j in range(b):
            pixel = image[i, j]
            if Existe(tailleComposantesPasBord, pixel):
                newVal = Recup(tailleComposantesPasBord, pixel) + 1
                Modif(tailleComposantesPasBord, pixel, newVal)
    maxi = 0
    plusGrandeComposante = -1
    for couple in tailleComposantesPasBord:
        if couple[1] > maxi:
            maxi = couple[1]
            plusGrandeComposante = couple[0]
    return plusGrandeComposante

def Recup(tab, cle):
    for elem in tab:
        if elem[0] == cle:
            return elem[1]

def Modif(tab, cle, val):
    for elem in tab:
        if elem[0] == cle:
            elem[1] = val

def Existe(tab, cle):
    for elem in tab:
        if elem[0] == cle:
            return True
    return False

## FONCTIONS

def triCorrespondances(tab):
    for k, e in enumerate(tab):
        temp = e
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
        while continu and k < len(tab):
            k += 1
            if tab[k] == val:
                tab[k] = valinf + 1
            else:
                continu = False

def verif_image(image):
    (a, b) = np.shape(image)
    for i in range(a):
        for j in range(b):
            val = image[i, j]
            if val not in [0, 1]:
                return False
    return True

def numsPrecedent(table, pixel, image):
    (i, j) = pixel
    res = []
    if i > 0 and image[i-1, j] == 1:
        res.append(HashRecup(table, (i-1, j)))
    if j > 0 and image[i, j-1] == 1:
        res.append(HashRecup(table, (i, j-1)))
    return res

def inversionImage(image):
    (a, b) = np.shape(image)
    for i in range(a):
        for j in range(b):
            image[i, j] = (image[i, j] + 1)%2

def determinationComposantes(image):
    (a, b) = np.shape(image)
    if not verif_image(image):
        return "mauvais format d'image"
    composantes = HashCreation(a, b)
    etiquette = 0
    correspondances = [0]
    for i in range(a):
        for j in range(b):
            if image[i, j] == 1:
                etiquettesPrec = numsPrecedent(composantes, (i, j), image)
                long = len(etiquettesPrec)
                if long == 0:
                    HashAjout(composantes, (i, j), etiquette)
                    etiquette += 1
                    correspondances.append(etiquette)
                elif long == 1:
                    HashAjout(composantes, (i, j), etiquettesPrec[0])
                else:
                    num1 = int(etiquettesPrec[0])
                    num2 = int(etiquettesPrec[1])
                    HashAjout(composantes, (i, j), num1)
                    if num1 > num2:
                        correspondances[num1] = num2
                    elif num1 != num2:
                        correspondances[num2] = num1
    triCorrespondances(correspondances)
    for i in range(a):
        for j in range(b):
            composantes[i, j] = correspondances[int(composantes[i, j])]
    #for elem in composantes:
    #    elem[1] = correspondances[elem[1]]
    for i in range(a):
        for j in range(b):
            if image[i, j] == 1:
                image[i, j] = HashRecup(composantes, (i, j)) + 1

## Dictionnaire sous forme de tableau (complexité en temps bien meilleure)

def HashCreation(a, b):
    res = np.zeros((a, b))
    for i in range(a):
        for j in range(b):
            res[i, j] = -1
    return res

def HashAjout(table, cle, elem):
    table[cle] = elem

def HashRecup(table, cle):
    return table[cle]

def HashExiste(table, cle):
    return table[cle] != -1

def HashModif(table, cle, newVal):
    table[cle] = newVal

def HashSuppr(table, cle):
    table[cle] = -1
