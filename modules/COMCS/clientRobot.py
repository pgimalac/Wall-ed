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
	print("command sent")
	print(socket.recv(255))
	file = open(filepath,'rb')
	data = file.read()
	socket.sendall(data)
	print("image sent")
	return json.loads(socket.recv(255))
	//attention le serveur attend une réponse, si un déchet à été instancié
	// ---> envoyer un JSON sous la forme : {"trashFound" : boolean, "braceletID" : ID, "type" : type_dechet, "typePropose" : type_propose_par_robot(coque), "reponseEleve" : boolean}

def stopConnexion():
	socket.close()
