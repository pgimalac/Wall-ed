#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May 22 14:02:09 2020

@author: romain
"""
import RPi.GPIO as GPIO                 #bibliothèque RPi.GPIO
import time
from tkinter import *
import sys
from random import randint
import cv2

from . import content
from . import triste
from .conva import Conva

def askForWaste(waste, conva):
    t = waste
    if waste == "cardboard":
        waste = "carton"
    elif waste == "metal":
        waste = "metallique"
    elif waste == "plastic":
        waste = "plastique"
    elif waste == "glass":
        waste = "verre"
    elif waste == "paper":
        waste = "papier"
    else:
        # on ne sait pas quel est le type du déchet, on affiche un type quelconque
        waste = "carton"

    conva.stop()

    root = Tk()
    root.attributes('-fullscreen', True)

    msg = Label(root, text="C'est un déchet\n{}".format(waste))
    msg.config(font=('courier', 70, 'bold'))
    msg.config(bg='lavender', fg='black')
    msg.pack(expand=True, side=TOP, fill=BOTH)

    redbutton = Label(root, text="FAUX")
    redbutton.config(font=('courier', 70, 'bold'))
    redbutton.config(bg='red', fg='black')
    redbutton.pack(expand=True, side=LEFT, fill=BOTH)

    greenbutton = Label(root, text="VRAI")
    greenbutton.config(font=('courier', 70, 'bold'))
    greenbutton.config(bg='green', fg='black')
    greenbutton.pack(expand=True, side=RIGHT, fill=BOTH)

    pin1 = 16
    pin2 = 20                              #broche utilisé en entrée
    temps = 100

    # GPIO.setwarnings(False)                 #désactive le mode warning
    # GPIO.setmode(GPIO.BCM)                  #utilisation des numéros de ports du
                                            #processeur
    GPIO.setup(pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
    GPIO.setup(pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
                                        #mise en entrée du port GPIO 22
                                        #et activation résistance soutirage
                                        #au ground

    # print("Début du programme")        #IHM
    # print("\nSortie par ctrl-c\n")     #IHM
    # try:
    while True:                    #boucle infinie
        entree1 = GPIO.input(pin1) #lecture entrée
        entree2 = GPIO.input(pin2)
        if not entree1:       #si touche appuyée
            print("BP vrai")
            root.destroy()
            content.read(c=conva)
            return True, t
        if not entree2:
            print("BP faux")
            root.destroy()
            triste.read(c=conva)
            return False, t
        time.sleep(temps / 1000)   #attente en msec
        root.update()
    # finally:
        # GPIO.cleanup()                 #libère toutes les ressources
        # print("\nFin du programme\n")  #IHM[/code]
    root.destroy()
    return None, None

if __name__ == "__main__":
    askForWaste("metal", Conva())
