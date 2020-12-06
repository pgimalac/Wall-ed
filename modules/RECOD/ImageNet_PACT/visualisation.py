from PIL import Image, ImageDraw, ImageFont

size = 20

img = Image.open(
    "/home/victor/pact32/modules/Reconnaissance de Déchets/ImageNet_PACT/test.jpg"
)
d = ImageDraw.Draw(img)

(x, y) = (2413, 609)
d.rectangle([x - size, y - size, x + size, y + size], (0, 0, 0))

(x, y) = (970, 1138)
d.rectangle([x - size, y - size, x + size, y + size], (0, 0, 0))

(x, y) = (2785, 1451)
d.rectangle([x - size, y - size, x + size, y + size], (0, 0, 0))

img.show()
