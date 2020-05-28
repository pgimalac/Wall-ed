from conva import Conva
import lectreg1
import ledsearch

def read(c=None):
    if c is None:
        return Conva(lectreg1.read, ledsearch.read)
    c.set(lectreg1.read, ledsearch.read)
    return c
