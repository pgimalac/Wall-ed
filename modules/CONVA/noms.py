#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import time
from tkinter import *
import sys
from random import randint

def choix_noms(L):
    i, j = randint(0, len(L)-1), randint(0, len(L)-1)
    while i == j:
        i, j = randint(0, len(L)-1), randint(0, len(L)-1)

    return ([L[i], L[j]])

def affiche_noms(L):
    if len(L) != 2:
        print("Le nombre de noms n'est pas le bon")
        return 0

    l = max(len(L[0]), len(L[1]))
    L1 = l-len(L[0])
    L2 = l-len(L[1])
    root = Tk()
    root.attributes('-fullscreen', True)
    msg = Label(root, text=((L1+1)//2)*" "+L[0]+(L1//2)*" ")
    msg.config(font=('courier', 70, 'bold'))
    msg.config(bg='red2', fg='black')
    #msg.config(height=20, width=30)
    msg.pack(expand=True, side="right", fill=BOTH)


    champ_label = Label(root, text=((L2+1)//2)*" "+L[1]+(L2//2)*" ")
    champ_label.pack(expand=True, side="left", fill=BOTH)
    champ_label.config(font=('courier', 70, 'bold'))
    champ_label.config(bg='dodger blue', fg='black')
    #champ_label.config(height=20, width=30)
    # On affiche le label dans la fenêtre
    champ_label.pack()


    # On démarre la boucle Tkinter qui s'interompt quand on ferme la fenêtre
    root.mainloop()
