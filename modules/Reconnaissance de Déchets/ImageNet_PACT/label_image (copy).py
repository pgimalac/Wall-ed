# Copyright 2017 The TensorFlow Authors. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

## Ici c'est nous
import time
from PIL import Image, ImageDraw
##

import argparse

import numpy as np
import tensorflow as tf


def load_graph(model_file="/home/victor/ImageNet_PACT/output_graph.pb"):
  graph = tf.Graph()
  graph_def = tf.GraphDef()

  with open(model_file, "rb") as f:
    graph_def.ParseFromString(f.read())
  with graph.as_default():
    tf.import_graph_def(graph_def)

  return graph



def getImage(file_name="/home/victor/Downloads/test.jpg"):
  input_name = "file_reader"
  output_name = "normalized"
  file_reader = tf.read_file(file_name, input_name)
  if file_name.endswith(".png"):
    image_reader = tf.image.decode_png(
        file_reader, channels=3, name="png_reader")
  elif file_name.endswith(".gif"):
    image_reader = tf.squeeze(
        tf.image.decode_gif(file_reader, name="gif_reader"))
  elif file_name.endswith(".bmp"):
    image_reader = tf.image.decode_bmp(file_reader, name="bmp_reader")
  else:
    image_reader = tf.image.decode_jpeg(
        file_reader, channels=3, name="jpeg_reader")
  return image_reader



def read_tensor_from_image_file(image_reader, dy, dx, h, l, input_height=299, input_width=299, input_mean=0, input_std=255):

  croped = tf.image.crop_to_bounding_box(image_reader, dy, dx, h, l)
  float_caster = tf.cast(croped, tf.float32)
  dims_expander = tf.expand_dims(float_caster, 0)
  resized = tf.image.resize_bilinear(dims_expander, [input_height, input_width])
  normalized = tf.divide(tf.subtract(resized, [input_mean]), [input_std])
  sess = tf.compat.v1.Session()
  result = sess.run(normalized)

  return result


def load_labels(label_file):
  label = []
  proto_as_ascii_lines = tf.gfile.GFile(label_file).readlines()
  for l in proto_as_ascii_lines:
    label.append(l.rstrip())
  return label


def chargeReseau(model_file):
  #tempsDepart = time.time()
  graph = load_graph(model_file)
  #print("Temps : " + str(time.time()-tempsDepart))
  return graph



def analyseImage(graph, file_name, h=2340, l=4160, input_layer="Placeholder", output_layer="final_result", label_file="/home/victor/ImageNet_PACT/output_labels.txt"):

  R=[]

  c = min(h,l) # les images découpées seront de forme carrée ; on récupère le côté maximum d'un tel carré
  L = [i/3 for i in range(1, 3+1)] # Liste des tailles relatives des sous-images

  for k in range(len(L)):
    print("Etape " + str(k+1) + "/" + str(len(L))) # Affiche l'avancement
    rapport = L[k] # Rapport de taille entre la nouvelle image et l'ancienne
    taille = int(c*rapport) # Taille de la nouvelle image
    step = int(taille/4) # Nombre de pixels dont on se décale à chaque itération
    for dx in range(0, l-taille, step):
      for dy in range(0, h-taille, step):
        t = read_tensor_from_image_file(file_name, dy, dx, taille, taille)
        r = analyseSousImage(graph, t, input_layer, output_layer, label_file)
        R.append([r[0], r[1], dx, dy, taille])

  return R


def analyseSousImage(graph, tensor, input_layer, output_layer, label_file):

  input_name = "import/" + input_layer
  output_name = "import/" + output_layer
  input_operation = graph.get_operation_by_name(input_name)
  output_operation = graph.get_operation_by_name(output_name)

  with tf.compat.v1.Session(graph=graph) as sess:
    results = sess.run(output_operation.outputs[0], {
        input_operation.outputs[0]: tensor
    })
  results = np.squeeze(results)

  top_k = results.argsort()[-1:][::-1]
  labels = load_labels(label_file)
  for i in top_k:
    return (labels[i], results[i])


def drawBox(L, img_path="/home/victor/Downloads/test.jpg"):
  source_img = Image.open(img_path)
  for item in L:
    x,y,t = item[2], item[3], item[4]
    draw = ImageDraw.Draw(source_img)
    draw.rectangle([(x,y), (x+t,y+t)], outline="black" )
  source_img.show()