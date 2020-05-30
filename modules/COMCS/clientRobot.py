import socket
import json

hote = "192.168.1.15"
port = 22346

socket1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def initConnexion():
    socket1.connect((hote, port))
    socket1.send(bytes("init", 'utf-8'))
    liste_eleves = socket1.recv(255)
    print(liste_eleves)
    return json.loads(liste_eleves)

def sendImage(filepath):
    socket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket2.connect(("137.194.177.197", 55556))
    print("connected")
    file = open(filepath, 'rb')
    data = file.read()
    size = len(data)
    size.to_bytes(8, byteorder='big')
    socket2.sendall(str(size).encode())
    socket2.sendall(data)
    socket2.send(bytes("False", 'utf-8'))
    print("image sent")
    res = socket2.recv(255)
    socket2.close()
    l = json.loads(res.decode())
    d = {}
    for tr in l:
        d[tr[1]] = tr[0]

    return d

    #attention le serveur attend une réponse, si un déchet à été instancié
    # ---> envoyer un JSON sous la forme : {"trashFound" : boolean, "braceletID" : ID, "type" : type_dechet, "typePropose" : type_propose_par_robot(coque), "reponseEleve" : boolean}

def interactionAnswer(trashFound, braceletID, type, typePropose, reponseEleve):
    socket1.send(bytes("newImage", 'utf-8'))
    socket1.send(bytes(json.dumps({'trashFound' : trashFound, 'braceletID' : braceletID, 'type' : type, 'typePropose' : typePropose, 'reponseEleve' : reponseEleve}), 'utf-8'))

def stopConnexion():
    socket1.send(bytes("close", 'utf-8'))
    socket1.close()
