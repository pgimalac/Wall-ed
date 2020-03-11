import socket
import json

hote = "192.168.2.6"
port = 2346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
    socket.connect((hote, port))
    socket.send(bytes("init", 'utf-8'))
    liste_eleves = socket.recv(255)
    return json.loads(liste_eleves)

def sendFile(filepath):
    with open(filepath, 'rb') as file:
        socket.sendfile(file)
    return json.loads(socket.recv(255))

def stopConnexion():
    socket.close()
