## MAIN

'''
découpage de l'algorithme en quatres étapes, détermination du seuillage, de la composante connexe, de l'histogramme et enfin des pics
il y a donc 4 parties qui suivent, chacune implémentant une étape en une ou plusieurs fonctions
'''
def mainServeur(img1,img2,img3,img4,img5,img6):
    L =[]
    Color = [["JAUNE",0]
            ["VERT" , 0]
            ["MAGENTA" , 0]
            ["BLEU", 0]
            ["TURQUOISE", 0]
            ["ROUGE", 0]]
    
    
trouverCouleurImg(img1,Color)
trouverCouleurImg(img2,Color)
trouverCouleurImg(img3,Color)
trouverCouleurImg(img4,Color)
trouverCouleurImg(img5,Color)
trouverCouleurImg(img6,Color)
     
max1 = 0
i1 = 0
max2 = 0
i2 = 0
for i in range(6):
    if Color[i][1] > max1:
        max1 = Color[i][1]
        i1 = i
        
L.append[Color[i1][0]]

del Color[i1]

for i in range(6):
    if Color[i][1] > max2:
        max2 = Color[i][1]
        i2 = i
L.append(Color[i2][0])

return L



def trouverCouleurImg(img,Color):
    if main(img)[0] == "JAUNE":
        Color[0][1] = Color[0][1] + 1
    elif main(img)[0] == "VERT":
        Color[0][1] = Color[1][1] + 1
    elif main(img)[0] == "MAGENTA":
        Color[0][1] = Color[2][1] + 1
    elif main(img)[0] == "BLEU":
        Color[0][1] = Color[3][1] + 1
    elif main(img)[0] == "TURQUOISE":
        Color[0][1] = Color[4][1] + 1
    elif main(img)[0] == "ROUGE":
        Color[0][1] = Color[5][1] + 1
        
    
    if main(img)[1] == "JAUNE":
        Color[0][1] = Color[0][1] + 1
    elif main(img)[1] == "VERT":
        Color[0][1] = Color[1][1] + 1
    elif main(img)[1] == "MAGENTA":
        Color[0][1] = Color[2][1] + 1
    elif main(img)[1] == "BLEU":
        Color[0][1] = Color[3][1] + 1
    elif main(img)[1] == "TURQUOISE":
        Color[0][1] = Color[4][1] + 1
    elif main(img)[1] == "ROUGE":
        Color[0][1] = Color[5][1] + 1
        
        
    
    
def main(img):
    imageFiltree = seuillageGris(img, 90)
    imageComp, composante = main_composantes(imageFiltree)
    histogramme = histo(img, imageComp, composante)
    l1, l2 = determinationLABEL(histogramme)
    return (l1,l2)

## FILTRE IMAGE

'''
algorithme de seuillage de l'image
'''

import cv2
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np



def seuillageGris(img, seuil):
    image = Image.open(img)
    ImageTab = np.array(image)
    imgHSI = cv2.cvtColor(ImageTab, cv2.COLOR_RGB2HSV)
    dim = imgHSI.shape
    res = np.zeros((dim[0],dim[1]))
    for i in range(dim[0]):
        for j in range(dim[1]):
            if imgHSI[i][j][2] < seuil:
                res[i,j] =  1
    return res

## COMPOSANTES CONNEXES

'''
Algo de reconaissance des composantes connexes d'un tableau composé de cases ayant soit 0 soit 1 pour valeur
image : tableau de 0 et 1
'''

import numpy as np

# Main

'''
découpage de cette étape en sous étape, car cette étape est la plus longue et la plus compliquée
On inverse d'abord l'image, puis on détermine les composantes connexes de l'image, puis celle qui nous intéresse
'''

def main_composantes(img):
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
    tailleComposantesPasBord = []
    for elem in composantes:
        if elem not in composantesBord:
            tailleComposantesPasBord.append([elem, 0])
    for i in range(a):
        for j in range(b):
            pixel = image[i,j]
            if Existe(tailleComposantesPasBord, pixel):
                newVal = Recup(tailleComposantesPasBord, pixel) + 1
                Modif(tailleComposantesPasBord, pixel, newVal)
    max = 0
    plusGrandeComposante = -1
    for couple in tailleComposantesPasBord:
        if couple[1] > max:
            max = couple[1]
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
    else:
        return False

# Fonctions

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
        while continu and k<len(tab):
            k += 1
            if tab[k] == val:
                tab[k] = valinf + 1
            else:
                continu = False

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
    composantes = HashCreation(a,b)
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
                    num1 = int(etiquettesPrec[0])
                    num2 = int(etiquettesPrec[1])
                    HashAjout(composantes, (i,j), num1)
                    if num1 > num2:
                        correspondances[num1] = num2
                    elif num1 == num2:
                        None
                    else:
                        correspondances[num2] = num1
    triCorrespondances(correspondances)
    for i in range(a):
        for j in range(b):
            composantes[i,j] = correspondances[int(composantes[i,j])]
    for i in range(a):
        for j in range(b):
            if image[i,j] == 1:
                image[i,j] = HashRecup(composantes, (i,j)) + 1

# Dictionnaire sous forme de tableau (complexité en temps bien meilleure)

def HashCreation(a,b):
    res = np.zeros((a,b))
    for i in range(a):
        for j in range(b):
            res[i,j] = -1
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

## CONSTRUCTION DE L'HISTOGRAMME ET DETERMINATION DES PICS

import cv2
import matplotlib.pyplot as plt
from PIL import Image
import numpy as np

def convertToHSI(img):
    image = Image.open(img)
    ImageTab = np.array(image)
    return cv2.cvtColor(ImageTab, cv2.COLOR_RGB2HSV)

def histo(img, imgConnex, n):
    ImHSV = convertToHSI(img)
    histogramme = np.zeros((180,2))
    for i in range(180):
        histogramme[i,0] = i
    dim = ImHSV.shape
    for i in range(dim[0]):
        for j in range(dim[1]):
            if imgConnex[i][j] == n :
                histogramme[ImHSV[i][j][0],1]+= 1
    return histogramme

import math

def tableauCorresp():
    tab = []
    for i in range(15):
        tab.append("ROUGE")
    for i in range(30):
        tab.append("JAUNE")
    for i in range(30):
        tab.append("VERT")
    for i in range(30):
        tab.append("TURQUOISE")
    for i in range(30):
        tab.append("BLEU")
    for i in range(30):
        tab.append("MAGENTA")
    for i in range(15):
        tab.append("ROUGE")
    return np.array(tab)

def determinationPics(histogramme):
    premierPic = max(histogramme)
    deuxiemePic = max2(histogramme, premierPic)
    return (premierPic,deuxiemePic)

def determinationLABEL(histogramme):
    p1,p2 = determinationPics(histogramme)
    t = tableauCorresp()
    print(p1,p2)
    return (t[int(p1)], t[int(p2)])

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
        if (i < (max1 - 45) or i > (max1 + 45)) and (i < (max1 + 180 - 45) or i > (max1 - 180 + 45)) and tab[i,1] > max:
            max = tab[i,1]
            indice_max = tab[i,0]
    return indice_max