import socket
import pickle
import json
import imageio
import numpy
import time

hote = "127.0.0.1"
port = 2346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
	socket.connect((hote,port))
	socket.send(bytes("init",'utf-8'))
	liste_eleves = socket.recv(255)
	return json.loads(liste_eleves)

def sendImage(filepath):
	socket.send(bytes("newImage",'utf-8'))
	print("sent command")
	print(socket.recv(255))
	file = open(filepath,'rb')
	data = file.read()
	socket.sendall(data)
	print("sent image")
	return json.loads(socket.recv(255))

def stopConnexion():
	socket.close()
