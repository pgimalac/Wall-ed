from random import randint
import time
from rpi_ws281x import PixelStrip, Color
from .led import colorWipe, theaterChase


def read():
    try:
        while True:
            i = randint(0, 4)
            if i == 0:
                colorWipe(Color(255, 0, 0))  #blue wipe
                colorWipe(Color(255, 0, 0))
            elif i == 1:
                theaterChase(Color(255, 0, 0))  #blue theaterchase
            # elif i == 2:
            #     rainbowCycle()
            # else:
            #     theaterChaseRainbow()
    finally:
        colorWipe(Color(0, 0, 0), 10)
