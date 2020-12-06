#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May  1 11:47:27 2020

@author: romain
"""
from tkinter import *
import sys
from random import randint
import cv2

valeur = 1


def action1(i):

    if valeur == i:
        exec(open("./lectreg2.py").read())

    else:
        exec(open("./lectreg3.py").read())


def action2(i):

    if valeur != i:
        exec(open("./lectreg2.py").read())

    else:
        exec(open("./lectreg3.py").read())


def proposition():

    root = Tk()
    root.attributes('-fullscreen', True)
    i = randint(0, 2)

    if i == 0:

        msg = Label(root, text="C'est un déchet\nrecyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand=True, side=TOP, fill=BOTH)

    elif i == 1:

        msg = Label(root, text="C'est un déchet\nnon-recyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand=True, side=TOP, fill=BOTH)

    else:

        msg = Label(root, text="C'est un déchet\nen verre")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand=True, side=TOP, fill=BOTH)

    redbutton = Button(root, text="FAUX", command=lambda: action2(i))
    redbutton.config(font=('courier', 70, 'bold'))
    redbutton.config(bg='red', fg='black')
    redbutton.pack(expand=True, side=LEFT, fill=BOTH)

    greenbutton = Button(root, text="VRAI", command=lambda: action1(i))
    greenbutton.config(font=('courier', 70, 'bold'))
    greenbutton.config(bg='green', fg='black')
    greenbutton.pack(expand=True, side=RIGHT, fill=BOTH)

    root.mainloop()


#if __name__=='__main__':
#  valeur= sys.argv[1]
# proposition()
