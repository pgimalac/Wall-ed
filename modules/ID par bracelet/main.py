from composantes_connexes import main
from filtreImage import seuillageGris
#from determinationPics import main
from HistogramConstructor import histo
from determinationPics import determinationPics
from PIL import Image
import numpy as np

def test(img):
    imageFiltree = seuillageGris(img, 220)
    imageNonFiltree = np.array(Image.open(img))
    imageComp, composante = main(imageFiltree)
    histogramme = histo(imageNonFiltree, imageComp, composante)
    pic1, pic2 = determinationPics(histogramme)
    return (pic1,pic2)

print(test("test.png"))