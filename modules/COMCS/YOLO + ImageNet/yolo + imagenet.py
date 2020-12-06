##

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
import time
from PIL import Image, ImageDraw
import subprocess
import argparse
import numpy as np
import tensorflow.compat.v1 as tf

##


def load_graph(model_file="/home/pact32/ImageNet/output_graph.pb"):
    graph = tf.Graph()
    graph_def = tf.GraphDef()

    with open(model_file, "rb") as f:
        graph_def.ParseFromString(f.read())
    with graph.as_default():
        tf.import_graph_def(graph_def)

    return graph


def getImage(file_name):
    input_name = "file_reader"
    output_name = "normalized"
    file_reader = tf.read_file(file_name, input_name)
    if file_name.endswith(".png"):
        image_reader = tf.image.decode_png(file_reader,
                                           channels=3,
                                           name="png_reader")
    elif file_name.endswith(".gif"):
        image_reader = tf.squeeze(
            tf.image.decode_gif(file_reader, name="gif_reader"))
    elif file_name.endswith(".bmp"):
        image_reader = tf.image.decode_bmp(file_reader, name="bmp_reader")
    else:
        image_reader = tf.image.decode_jpeg(file_reader,
                                            channels=3,
                                            name="jpeg_reader")
    return image_reader


def read_tensor_from_image_file(image_reader,
                                dy,
                                dx,
                                h,
                                l,
                                input_height=299,
                                input_width=299,
                                input_mean=0,
                                input_std=255):
    croped = tf.image.crop_to_bounding_box(image_reader, dy, dx, h, l)
    float_caster = tf.cast(croped, tf.float32)
    dims_expander = tf.expand_dims(float_caster, 0)
    resized = tf.image.resize_bilinear(dims_expander,
                                       [input_height, input_width])
    normalized = tf.divide(tf.subtract(resized, [input_mean]), [input_std])
    sess = tf.Session()
    result = sess.run(normalized)

    return result


def load_labels(label_file):
    label = []
    proto_as_ascii_lines = tf.gfile.GFile(label_file).readlines()
    for l in proto_as_ascii_lines:
        label.append(l.rstrip())
    return label


def analyseSousImage(graph,
                     tensor,
                     input_layer="Placeholder",
                     output_layer="final_result",
                     label_file="/home/pact32/ImageNet/output_labels.txt"):

    input_name = "import/" + input_layer
    output_name = "import/" + output_layer
    input_operation = graph.get_operation_by_name(input_name)
    output_operation = graph.get_operation_by_name(output_name)

    with tf.Session(graph=graph) as sess:
        results = sess.run(output_operation.outputs[0],
                           {input_operation.outputs[0]: tensor})
    results = np.squeeze(results)

    top_k = results.argsort()[-1:][::-1]
    labels = load_labels(label_file)
    for i in top_k:
        return (labels[i], results[i])


##


def analyseImage(image_path='../../test.JPG'):
    result_to_return = []
    result = subprocess.run([
        './darknet_modified', 'detector', 'test', 'cfg/obj.data',
        'cfg/tiny-yolo.cfg', 'backup/tiny-yolo_last.weights', image_path
    ],
                            stdout=subprocess.PIPE).stdout.decode('utf-8')
    result = [[int(y.split(",")[0]) for y in x.split("=")[1:]]
              for x in result.split("\n") if "Bounding" in x]
    image = getImage(image_path)
    for (left, top, right, bottom) in result:
        width = right - left
        height = bottom - top
        dim = max(width, height)
        tensor = read_tensor_from_image_file(image, top, left, dim, dim)
        result_to_return.append([(left, top, right, bottom),
                                 analyseSousImage(load_graph(), tensor)[0]])
    return result_to_return


##
tf.disable_eager_execution()

print("=" * 50)
print(analyseImage())
print("=" * 50)
