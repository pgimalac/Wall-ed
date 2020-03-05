import socket
import pickle

hote = "adresseIP"
port = 8000

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
	socket.connect((hote,port))
	liste_eleves = socket.recv(255)
	return json.loads(liste_eleves)

def receiveSomething():
	return json.loads(socket.recv(255))

def sendSomething(something):
	socket.send(pickle.dumps(something))

def stopConnexion():
	socket.close()
