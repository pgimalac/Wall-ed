import socket
import pickle
import json
import imageio
import numpy

hote = "192.168.2.6"
port = 2346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def sendMessage(message):
    socket.send(bytes(message, 'utf-8'))

def initConnexion():
    socket.connect((hote, port))
    sendMessage("init")
    liste_eleves = socket.recv(255)
    return json.loads(liste_eleves)

def sendFile(filepath):
    sendMessage("newImage")
    with open(filepath, 'rb') as file:
        socket.sendfile(file)
    return json.loads(socket.recv(255))

def stopConnexion():
    socket.close()
