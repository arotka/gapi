#!/usr/bin/python
"""Raspberry Pi Face Recognition Security gate access 
Face Detection Helper Functions
Copyright 2014 Amine KARROUT 
config file
"""

# Edit the values below to configure the training and usage of the
# face recognition box.
NO_HIT = 'NO_HIT'
HIT = 'HIT'

# Threshold for the confidence of a recognized face before it's considered a
# positive match.  Confidence values below this threshold will be considered
# a positive match because the lower the confidence value, or distance, the
# more confident the algorithm is that the face was correctly detected.
# Start with a value of 3000, but you might need to tweak this value down if 
# you're getting too many false positives (incorrectly recognized faces), or up
# if too many false negatives (undetected faces).
POSITIVE_THRESHOLD = 2000.0

# File to save and load face recognizer model.
TRAINING_FILE = 'script/matcher/training.xml'

# Value for positive and negative labels passed to face recognition model.
# Can be any integer values, but must be unique from each other.
# You shouldn't have to change these values.
POSITIVE_LABEL = 1
NEGATIVE_LABEL = 2

# Size (in pixels) to resize images for training and prediction.
# Don't change this unless you also change the size of the training images.
FACE_WIDTH  = 92
FACE_HEIGHT = 112

# Face detection cascade classifier configuration.
# You don't need to modify this unless you know what you're doing.
# See: http://docs.opencv.org/modules/objdetect/doc/cascade_classification.html
HAAR_FACES         = 'haarcascade_frontalface_alt.xml'
HAAR_SCALE_FACTOR  = 1.2
HAAR_MIN_NEIGHBORS = 2
HAAR_MIN_SIZE      = (20, 20)
