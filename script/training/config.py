#!/usr/bin/python
"""Raspberry Pi Face Recognition Security gate access 
Face Detection Helper Functions
Copyright 2014 Amine KARROUT 
config file
"""

# File to save and load face recognizer model.
TRAINING_FILE = 'training.xml'

# Directories which contain the positive and negative training image data.
POSITIVE_DIR = './training/positive'
NEGATIVE_DIR = './training/negative'

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

# Filename to use when saving the most recently captured image for debugging.
DEBUG_IMAGE = 'capture.pgm'

def get_camera():
	import webcam
	return webcam.OpenCVCapture(device_id=0)