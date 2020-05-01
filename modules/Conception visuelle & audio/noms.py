#!/usr/bin/env python3
# -*- coding: utf-8 -*-
from tkinter import *
import sys

def affiche_noms(Nom1, Nom2):
    L=max(len(Nom1),len(Nom2))
    L1=L-len(Nom1)
    L2=L-len(Nom2)
    root = Tk()
    root.attributes('-fullscreen', True)
    msg = Label(root, text=((L1+1)//2)*" "+Nom1+(L1//2)*" ")
    msg.config(font=('courier', 70, 'bold'))
    msg.config(bg='red2', fg='black')
    #msg.config(height=20, width=30)
    msg.pack(expand= True, side = "right", fill=BOTH)
    
    
    champ_label = Label(root, text=((L2+1)//2)*" "+Nom2+(L2//2)*" ")
    champ_label.pack(expand= True,side="left", fill= BOTH)
    champ_label.config(font=('courier', 70, 'bold'))
    champ_label.config(bg='dodger blue', fg='black')
    #champ_label.config(height=20, width=30)
    # On affiche le label dans la fenêtre
    champ_label.pack()


# On démarre la boucle Tkinter qui s'interompt quand on ferme la fenêtre
    root.mainloop()