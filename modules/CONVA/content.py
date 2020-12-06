from .conva import Conva
from . import lectreg2
from . import ledcontent


def read(c=None):
    if c is None:
        return Conva(lectreg2.read, ledcontent.read)
    c.set(lectreg2.read, ledcontent.read)
    return c


if __name__ == "__main__":
    read()
