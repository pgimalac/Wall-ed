'''
Download the TACO database, while writing annotations usable by YOLOv2
Using annotations.json from the TACO project: https://github.com/pedropro/TACO
Written by Victor Masiak, 2020
'''

## Librairies

import os.path
import argparse
import json
from PIL import Image
import requests
from io import BytesIO
import sys


## Constants

matches = ["1-7", "2-3", "3-5", "4-3", "5-1", "6-1", "7-0", "8-1", "9-1", "10-3", "11-0", "12-3", "13-3", "14-2", "15-2", "16-2", "17-3", "18-2", "19-2", "20- 1", "21-2", "22-0", "23-1", "24-5", "25-1", "26-3", "27-4", "28-4", "29-4", "30-4", "31-4", "32-1", "33-3", "34-1", "35-1", "36-1", "37-1", "38-1", "39-1", "40-1", "41-1", "42-1", "43-1", "44-1", "45-1", "46-1", "47-5", "48-1", "49-1", "50-1", "51-3", "52-5", "53-3", "54-5", "55-1", "56-5", "57-1", "58-4", "59-5", "60-2", "61-5", "62-0", "63-1", "64-5"]
output_directory = "/home/pact32/TACO_data_for_YOLOv2"
annotations_file = "/home/pact32/repos/TACO/data/annotations.json"

an_min = 100
an_max = -1


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

        file_path = os.path.join(dataset_dir, file_name)

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
        sys.stdout.write("Downloaded n°" + str(i) + "/" + str(nr_images))
        sys.stdout.flush()


## Loads annotations

    n_annotations = len(annotations['annotations'])
    for i in range(n_annotations):

        #Loads annotations in TACO format
        annotation = annotations['annotations'][i]
        annotation_id = annotation['image_id']
        annotation_category_id = annotation['category_id']
        an_min = min(annotation_category_id, an_min)
        an_max = max(annotation_category_id, an_max)
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

                image_d["annotation"].append(str(annotation_category_id) + " " + str(x) + " " + str(y) + " " + str(width) + " " + str(height) + chr(13))

        #Progress
        sys.stdout.write("Loaded annotation n°" + str(i) + "/" + str(n_annotations))
        sys.stdout.flush()

## Save annotations

    for i in range(len(images_list)):
        image_d = images_list[i]
        annotation_path = os.path.join(output_directory, image_d["img_file_name"][:-4]+".txt")
        with open(annotation_path) as f:
            f.write(image_d["annotation"])
        #Progress
        sys.stdout.write("Saved annotation n°" + str(i) + "/" + str(len(images_list)))
        sys.stdout.flush()


## The end

sys.stdout.write(an_min + " ; " + an_max)
sys.stdout.write('Finished\n')