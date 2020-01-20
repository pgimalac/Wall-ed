from composantes_connexes.py import main
from filtreImage import seuillageGris
#from determinationPics import main
from HistogramConstructor import histo
from determinationPics import determinationPics
from PIL import Image
import numpy as np

def test(img):
    imageFiltree = seuillageGris(img, 220)
    imageComp, composante = main(imageFiltree)
    histogramme = histo(img, imageComp, composante)
    pic1, pic2 = determinationPics(histogramme)
    return (pic1,pic2)