import socket
import pickle
import json

hote = "localhost"
port = 2346

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
	socket.connect((hote,port))
	sendSomething("init")
	liste_eleves = socket.recv(255)
	return json.loads(liste_eleves)

def sendSomething(something):
	socket.send(pickle.dumps(something))
	return json.loads(socket.recv(255))

def stopConnexion():
	socket.close()
