#!/usr/bin/env python

import socket
import os
import subprocess
import struct
from PIL import Image

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEPORT, 1)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
sock.bind(('137.194.177.197', 55556))

def start_analysis():
    #subprocess.run(["conda", "init"])
    #subprocess.run(["conda", "activate", "py36"])

    imgcounter = 1
    basename = "images_robot/image%s.jpg"

    while True:
        sock.listen(5)
        client, address = sock.accept()
        print("connexion reçue")


        store_image(client,basename, imgcounter)

        print("image reçue")
        ret = subprocess.run(["python3", "CODE.py", basename % imgcounter], stdout=subprocess.PIPE)
        result = ret.stdout
        chaine = result.decode()
        print(chaine)
        print(type(chaine))
        i = 0
        while chaine[i:i+7] != "RETURN:":
            i+=1
        j = i
        while chaine[j:j+3] != "END":
            j+=1
        tosend = chaine[i+7:j]
        client.send(tosend.encode())
        print("résultat envoyé")
        imgcounter += 1



def store_image(client, basename, imgcounter):
    with open(basename % imgcounter, 'wb') as img:
        size = int.from_bytes(client.recv(8), byteorder='big')
#        for i in range(size):
        print(size)
        taille = 0
        while taille <= size:
            data = client.recv(4096)
            img.write(data)
            taille += len(data)



start_analysis()

