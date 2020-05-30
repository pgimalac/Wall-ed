#!/usr/bin/env python

import socket
import os
import subprocess
import struct
from PIL import Image

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
socket.bind(('137.194.177.197', 55556))

def start_analysis():
    #subprocess.run(["conda", "init"])
    #subprocess.run(["conda", "activate", "py36"])

    imgcounter = 1
    basename = "images_robot/image%s.jpg"

    while True:
        socket.listen(5)
        client, address = socket.accept()
        print("connexion reçue")

        #myfile = open(basename % imgcounter, 'wb')
        #data = client.recv(200000)
        #myfile.write(data)
        #myfile.close()



#        buf = b''            # this caused an error, I added a b so it would be read as bytes and not string
#        while len(buf)<4:
#            buf += client.recv(4-len(buf))
#        size = struct.unpack('!i', buf)
#        print("receiving %s bytes" % size)


        store_image(client,basename, imgcounter)


        print("image reçue")
        ret = subprocess.run(["python3", "CODE.py", basename % imgcounter], stdout=subprocess.PIPE)
        client.send(ret.stdout)
        print("résultat envoyé")
        imgcounter += 1

def store_image(client, basename, imgcounter):
    with open(basename % imgcounter, 'wb') as img:
        size = int(client.recv(8).decode())
#        for i in range(size):
        print(size)
        taille = 0
        while taille <= size:
            data = client.recv(4096)
            img.write(data)
            taille += len(data)



start_analysis()









