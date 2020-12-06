## FILES LIBS

from os import listdir
from os.path import isfile, join

## NN LIBS

import keras
from keras.applications import InceptionV3
from keras.models import Model, Sequential
from keras.layers import Dense

## PICTURES

PATH = "/home/victor/Downloads/garbage-classification/Garbage classification/Garbage classification"
processed_imgs_array = []

for dir in listdir(PATH):
    local_path = join(PATH, dir)
    if not isfile(local_path):
        for file in listdir(local_path):
            processed_imgs_array.append()

## MODEL

original_model = InceptionV3()
bottleneck_input = original_model.get_layer(index=0).input
bottleneck_output = original_model.get_layer(index=-2).output
bottleneck_model = Model(inputs=bottleneck_input, outputs=bottleneck_output)

for layer in bottleneck_model.layers:
    layer.trainable = False

new_model = Sequential()
new_model.add(bottleneck_model)
new_model.add(Dense(NUM_CLASSES, activation='softmax', input_dim=2048))

## COMPILE AND TRAIN

# For a binary classification problem
new_model.compile(optimizer='rmsprop',
                  loss='binary_crossentropy',
                  metrics=['accuracy'])

one_hot_labels = keras.utils.to_categorical(labels, num_classes=NUM_CLASSES)
new_model.fit(processed_imgs_array, one_hot_labels, epochs=2, batch_size=32)
