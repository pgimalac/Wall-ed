import multiprocessing
import time
from random import randint
import cv2

def worker(file):
    exec(open(file).read())


if __name__ == '__main__':
    files = ["./lectreg2.py","./ledcontent.py"]
    for i in files:
        p = multiprocessing.Process(target=worker, args=(i,))
        p.start()
