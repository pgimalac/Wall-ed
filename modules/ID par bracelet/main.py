from composantes_connexes import *
from filtreImage import *
from determinationPics import *
from HistogramConstructor import *

from PIL import Image

def test(img):
    imageFiltree = seuillageGris(img, 220)
    impageNonFiltree = Image.open(img)
    imageComp, composante = main(imageFiltree)
    histogramme = histo(imageNonFiltree, imageComp, composante)
    pic1, pic2 = determinationPics(histogramme)
    return (pic1,pic2)