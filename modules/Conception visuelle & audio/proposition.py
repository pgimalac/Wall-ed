#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri May  1 11:47:27 2020

@author: romain
"""
from tkinter import *
import sys
from random import randint

def action1():
    
    if valeur == i: 
    
    else:
        
def action2():
    
    if valeur != i:
    
    else:
        

def proposition():
    
    root=Tk()
    root.attributes('-fullscreen', True)
    i=randint(0,2)
    
    if i==0:
        
        msg = Label(root, text="C'est un déchet recyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
        
    
    
    elif i == 1:
        
        msg = Label(root, text="C'est un déchet non-recyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
        
    
    else:
        
        msg = Label(root, text="C'est un déchet en verre")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
        
    redbutton = Button(root, text="FAUX")
    redbutton.config(font=('courier', 70, 'bold'))
    redbutton.config(bg='red', fg='black')
    redbutton.pack(expand=True, side = LEFT, fill = BOTH)
    
    greenbutton = Button(root, text="VRAI")
    greenbutton.config(font=('courier', 70, 'bold'))
    greenbutton.config(bg='green', fg='black')
    greenbutton.pack(expand= True,side = RIGHT, fill=BOTH)  

    
    root.mainloop()