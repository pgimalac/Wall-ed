from conva import Conva
import lectreg3
import ledtriste

def read(c=None):
    if c is None:
        return Conva(lectreg3.read, ledtriste.read)
    c.set(lectreg3.read, ledtriste.read)
    return c
