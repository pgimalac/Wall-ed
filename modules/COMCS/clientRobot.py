import socket
import pickle
import json
import imageio
import numpy
import time

hote = "192.168.2.4"
port = 22346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def sendMessage(message):
    socket.send(bytes(message, 'utf-8'))

def initConnexion():
    socket.connect((hote, port))
    sendMessage("init")
    liste_eleves = socket.recv(255)
    return json.loads(liste_eleves)

def sendInfoCoque(id_bracelet, type_trash, type_random, answer):
    pass

def sendImage(filepath):
    sendMessage("newImage")
	print("sent command")
	time.sleep(5)
	print(socket.recv(255))
	time.sleep(5)
	file = open(filepath,'rb')
	socket.send(pickle.dumps(file))
	print("sent image")
	time.sleep(5)
	return json.loads(socket.recv(255))

def stopConnexion():
    socket.close()
