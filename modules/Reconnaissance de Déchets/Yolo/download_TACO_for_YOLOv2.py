'''
Download the TACO database, while writing annotations usable by YOLOv2
Using annotations.json from the TACO project: https://github.com/pedropro/TACO
Written by Victor Masiak, 2020
'''

## Librairies

import os.path
import argparse
import json
from PIL import Image, ImageDraw
import requests
from io import BytesIO
import sys


## Constants

output_directory = "D:\\Git\\pact32\\modules\\Reconnaissance de Déchets\\Yolo\\Images"
annotations_file = "D:\\Git\\TACO\\data\\annotations.json"


## Main loop

#List used to link each image to its annotation
images_list = []

# Load annotations
with open(annotations_file, 'r') as f:
    annotations = json.loads(f.read())

## Downloads images

    nr_images = len(annotations['images'])
    for i in range(nr_images):

        image = annotations['images'][i]

        file_name = image['file_name'].replace("/","_")
        image_id = image['id']
        image_width = image['width']
        image_height = image['height']
        url_original = image['flickr_url']
        url_resized = image['flickr_640_url']

        file_path = os.path.join(output_directory, file_name)

        # Create subdir if necessary
        subdir = os.path.dirname(file_path)
        if not os.path.isdir(subdir):
            os.mkdir(subdir)

        if not os.path.isfile(file_path):
            # Load and Save Image
            response = requests.get(url_original)
            img = Image.open(BytesIO(response.content))
            if img._getexif():
                img.save(file_path, exif=img.info["exif"])
            else:
                img.save(file_path)
        images_list.append({"img_id": image_id, "img_file_name": file_name, "img_width": image_width, "img_height": image_height, "annotation": ""})

        #Progress
        print("Downloaded n°" + str(i) + "/" + str(nr_images))


## Loads annotations

    n_annotations = len(annotations['annotations'])
    for i in range(n_annotations):

        #Loads annotations in TACO format
        annotation = annotations['annotations'][i]
        annotation_id = annotation['image_id']
        annotation_category_id = annotation['category_id']
        annotation_bbox = annotation['bbox']

        #Load image information, and start conversion
        bwidth = annotation_bbox[2]
        bheight = annotation_bbox[3]
        bx = annotation_bbox[0] + bwidth/2
        by = annotation_bbox[1] + bheight/2

        for image_d in images_list:
            if image_d["img_id"]==annotation_id:

                width = bwidth/image_d["img_width"]
                height = bheight/image_d["img_height"]
                x = bx/image_d["img_width"]
                y = by/image_d["img_height"]
                annotation_path = os.path.join(output_directory, image_d["img_file_name"][:-4]+".txt")

                tmp = ""
                if os.path.isfile(annotation_path):
                    with open(annotation_path, "r") as f:
                        tmp = f.read()

                print("Lecture de " + annotation_path + " : " + tmp)
                if not (str(x) + " " + str(y) + " " + str(width) + " " + str(height) in tmp):

                    im = Image.open(os.path.join(output_directory, image_d["img_file_name"]))
                    draw = ImageDraw.Draw(im)
                    draw.rectangle([annotation_bbox[0], annotation_bbox[1], annotation_bbox[0]+annotation_bbox[2], annotation_bbox[1]+annotation_bbox[3]], fill=None, outline=(255,0,0), width=15)
                    draw.rectangle([annotation_bbox[0], annotation_bbox[1], annotation_bbox[0]+annotation_bbox[2], annotation_bbox[1]+annotation_bbox[3]], fill=None, outline=(0,255,0), width=10)
                    draw.rectangle([annotation_bbox[0], annotation_bbox[1], annotation_bbox[0]+annotation_bbox[2], annotation_bbox[1]+annotation_bbox[3]], fill=None, outline=(0,0,255), width=5)
                    del draw
                    im.show()

                    category =-1
                    print("0 - Divers")
                    print("1 - Verre")
                    print("2 - Carton / Papier")
                    print("3 - Métal")
                    print("4 - Plastique")
                    while not (0 <= category <= 6):
                        category = int(input("Categorie : "))

                    image_d["annotation"] = str(category) + " " + str(x) + " " + str(y) + " " + str(width) + " " + str(height) + chr(13)
                    with open(annotation_path, "a+") as f:
                        f.write(image_d["annotation"])

                    #Progress
                    print("Set annotation n°" + str(i) + "/" + str(n_annotations))


## The end

print("Finished")