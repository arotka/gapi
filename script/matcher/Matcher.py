#!/usr/bin/python

"""
Raspberry Pi Face Recognition for opening gateway access 
Copyright 2014 Amine KARROUT
Script to perform face recognition 
"""
import cv2
import numpy as np
import sys
import config
import face

def matchFace(image, coordinate):
  x, y, w, h = coordinate
  # Crop and resize image to face.
  crop = face.resize(face.crop(image, x, y, w, h))
  cv2.imwrite('crop.jpeg', crop)
  # Test face against model.
  label, confidence = model.predict(crop)
  if label == config.POSITIVE_LABEL and confidence < config.POSITIVE_THRESHOLD:
    return config.HIT
  else:
    return config.NO_HIT


if __name__ == '__main__':
  result = config.NO_HIT
  imgPath = sys.argv[1]
  if not imgPath:
    sys.exit(0)
  image = cv2.imread(imgPath)
  if image is None:
    sys.exit(0)
  # Load training data into model
  #model = cv2.createEigenFaceRecognizer()
  #model = cv2.createFisherFaceRecognizer()
  model = cv2.createLBPHFaceRecognizer()
  model.load(config.TRAINING_FILE)
  # Convert image to grayscale.
  image = cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)
  cv2.imwrite('gray.jpeg', image)
  faces = face.detect_single(image)
  if faces is not config.NO_HIT : 
    result = matchFace(image, faces)
  print result 