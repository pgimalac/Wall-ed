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

valeur = 1
i = 0

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
        
        
    
root=Tk()
root.attributes('-fullscreen', True)
global i
i=randint(0,2)
    
if i==0:
        
        msg = Label(root, text="C'est un déchet\nrecyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
    
    
elif i == 1:
        
        msg = Label(root, text="C'est un déchet\nnon-recyclable")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
        
     
else:
        
        msg = Label(root, text="C'est un déchet\nen verre")
        msg.config(font=('courier', 70, 'bold'))
        msg.config(bg='lavender', fg='black')
        msg.pack(expand= True, side = TOP, fill=BOTH)
        
redbutton = Label(root, text="FAUX")
redbutton.config(font=('courier', 70, 'bold'))
redbutton.config(bg='red', fg='black')
redbutton.pack(expand=True, side = LEFT, fill = BOTH)
    
greenbutton = Label(root, text="VRAI") 
greenbutton.config(font=('courier', 70, 'bold'))
greenbutton.config(bg='green', fg='black')
greenbutton.pack(expand=True,side = RIGHT, fill=BOTH)
         

    
root.mainloop()
    

#if __name__=='__main__':
 #  valeur= sys.argv[1]
  # proposition()
    
pin1 = 20  
pin2 = 16                              #broche utilisé en entrée
#temps = 1                              #valeur attente en msec
#temps = 10
temps = 100
#temps = 100
#temps = 1000
 
GPIO.setwarnings(False)                 #désactive le mode warning
GPIO.setmode(GPIO.BCM)                  #utilisation des numéros de ports du
                                        #processeur
GPIO.setup(pin1, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
GPIO.setup(pin2, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)
                                        #mise en entrée du port GPIO 22
                                        #et activation résistance soutirage
                                        #au ground
if __name__ == '__main__':
    
     print("Début du programme")        #IHM
     print("/nSortie par ctrl-c\n")       #IHM
     try:
         while True:                    #boucle infinie
             entree1 = GPIO.input(pin1) #lecture entrée
             entree2 = GPIO.input(pin2)
             if (entree1 == False):       #si touche appuyée
                 print("BP vrai")         #IHM
                 action1(i)
             if (entree2 == False):
                 print("BP faux")
                 action2(i)
             time.sleep(temps / 1000)   #attente en msec 
     except KeyboardInterrupt:          #sortie boucle par ctrl-c
         GPIO.cleanup()                 #libère toutes les ressources
         print("\nFin du programme\n")  #IHM[/code]
    