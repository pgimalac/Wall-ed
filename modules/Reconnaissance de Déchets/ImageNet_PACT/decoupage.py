from PIL import Image
import numpy as np
from os import listdir
from os.path import isfile, join



def decoupage(chemin): # Découpe l'image en plein de portion plus petites pour les analyser séparement

    imgPil = Image.open(chemin) # Ouverture de l'image
    img = np.array(imgPil) # Transformation de l'image en tableau numpy
    (h,l,d) = img.shape # On récupère la taille de l'image
    c = min(h,l) # les images découpées seront de forme carrée ; on récupère le côté maximum d'un tel carré
    L = [i/5 for i in range(1, 5+1)] # Liste des tailles relatives des sous-images

    for k in range(len(L)):
        print("Etape " + str(k+1) + "/" + str(len(L))) # Affiche l'avancement
        rapport = L[k] # Rapport de taille entre la nouvelle image et l'ancienne
        taille = int(c*rapport) # Taille de la nouvelle image
        step = int(taille/4) # Nombre de pixels dont on se décale à chaque itération
        for dx in range(0, l-taille, step):
            for dy in range(0, h-taille, step):
                nvNpArray = np.array([[[img[y][x][i] for i in range(d)] for x in range(dx, dx+taille)] for y in range(dy, dy+taille)])
                nvImage = Image.fromarray(nvNpArray)
                nvImage.save("/tmp/images/rapport" + str(rapport) + "_x" + str(dx) + "_y" + str(dy) + ".jpg")



def analyseDansDossier(chemin, graphPath, input_layer, output_layer, label_file):
    files = [f for f in listdir(chemin) if (isfile(join(chemin, f)) and f.endswith(".jpg"))]
    L = []
    graph = chargeReseau(graphPath)
    for i in range(len(files)):
        print("Etape " + str(i) + "/" + str(len(files)))
        file = files[i]
        (label, result) = analyseImage(graph, join(chemin, file), input_layer, output_layer, label_file)
        if (result>=0.8):
            L.append([label, result, file])
    return L



#def analyseSousImages(chemin):
