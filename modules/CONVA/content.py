from conva import Conva
import lectreg2
import ledcontent

def read(c=None):
    if c is None:
        return Conva(lectreg2.read, ledcontent.read)
    c.set(lectreg2.read, ledcontent.read)
    return c
