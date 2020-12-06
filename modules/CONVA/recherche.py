from .conva import Conva
from . import lectreg1
from . import ledsearch


def read(c=None):
    if c is None:
        return Conva(lectreg1.read, ledsearch.read)
    c.set(lectreg1.read, ledsearch.read)
    return c


if __name__ == "__main__":
    read()
