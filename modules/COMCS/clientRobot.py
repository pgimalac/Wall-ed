import socket
import pickle
import json
import imageio
import numpy
import time

hote = "192.168.2.4"
port = 22346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
	socket.connect((hote,port))
	socket.send(bytes("init",'utf-8'))
	liste_eleves = socket.recv(255)
	return json.loads(liste_eleves)

def sendImage(filepath):
	socket.send(bytes("newImage",'utf-8'))
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
