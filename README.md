Gapi
====
GaPi is a system that allows people to unlock their doors over the face recognition, using the Raspberry Pi and a LOGITECH Webcam and electric door strike.

> The application is coded in java using java 7 SE embedded and python .
The project depends on the OpenCV (version 2.4.9) computer vision library to perform the face detection and recognition.     

Version
----

0.1

Tech
-----------

Gapi uses a number of open source projects to work properly:

* [MOTION](http://www.lavrsen.dk/foswiki/bin/view/Motion/WebHome) -  is a program that monitors the video signal from cameras.
* [OPENCV](http://opencv.org/) -  is a computer vision and machine learning software library.
* [TWITTER4J](http://twitter4j.org/en/index.html) - Java library for the Twitter API.
* [PI4J](http://pi4j.com/) -  is intended to provide a bridge between the native libraries and Java for full access to the Raspberry Pi.
* [RASPBIAN](http://www.raspbian.org/)  is a Debian-based free operating system optimized for the Raspberry Pi hardware.

Hardware
-----------

You will need the following parts for this project:

* Raspberry Pi, either model A or model B will work, running the Raspbian operating system.
* Webcam, Logitech B910 HD.
* Electric Door Strike 12V DC.
* Relay 5V.
* Charger 12V.
* SDCARD 8Gb.


Wiring
--------------
See the diagram below for how to wire the door strike/relay/Webcam to the Raspberry Pi.

![schema](/schema/schema.png?raw=true "shema created with fritzing")


[schema](/https://github.com/arotka/gapi/blob/master/schema/schema.png))

Installation
--------------
1. install motion ref [link](http://www.raspberrypi.org/forums/viewtopic.php?t=18314).

2. install opencv (version 2.4.9) and python dependencies ref [link](https://docs.google.com/document/d/1bgVo24hCK0huoxm9zGC9djL6K0yy0z9GldzSy6SZUQY/pub
).

Build
--------------

The Application will be built and packaged using Maven. There are only two dependencies needed for this application (Twitter4J and Pi4J). To make things easy, we will use the maven-dependency-plugin to copy the dependencies into a lib/ folder along side the main application. See [here](./pom.xml) for the complete pom.xml.

> * To build project you should launch : 

> ```sh
mvn clean install
```
> * After the build you should have a jar 'rpiFaceRecognition-0.0.1-SNAPSHOT.jar' and a directory named (script which contains two subdirectories (matcher/training))    


##### Configure Plugins. Instructions in following README.md files :
#### Opencv training database:
> *  This project uses the LBPH algorithm in OpenCV to perform face recognition. To use this algorithm you'll need to create a set of training data with pictures of faces that are allowed to unlock the door and the faces that are not allowed to unlock the door.
  *  Download [The ORL Database of Faces](http://www.cl.cam.ac.uk/research/dtg/attarchive/facedatabase.html) . These faces make up the set of negative images which represent faces that are not allowed to unlock the door. 
  *  copy the faces database in script/training/training/negative.
  *  Use [takePhotos.py](./script/training/takePhotos.py) script to generate images of the person who will be allowed to unlock the door .
  *  This script will take pictures with the  webcam and write them to the script/training/training/positive sub-directory.
    
>  ```sh
sudo python takePhotos.py
```

>  *  Once you've captured a number of positive training images, you can run the [makeTrainingDb.py](./script/training/makeTrainingDb.py) script to perform the training. 

>  ```sh
python makeTrainingDb.py
```

> *  Once the training is complete. The training data is now stored in the file training.xml, copy it in script/matcher  sub-directory.
  
####  Motion:
> * See  [here](./script/conf/motion.conf) for the complete motion configuration .

####  JAVA Application:
> * Configure your environment :
* DIRECTORY_TO_WATCH it's the motion target directory for all snapshots, motion images and normal images.
* MATCHER_SCRIPT is the script to process detection and recognition faces using openCV Face Detection and Face Recognition.
* For sending a tweet to your app you should set APP_SECRET;APP_KEY;token;
  secret, for further information ref [link](https://dev.twitter.com/). 

> ```java
public class GapiConfiguration 
{
  //DIRECTORY_TO_WATCH references the target_dir key in motion.conf
  public final static String DIRECTORY_TO_WATCH = "/media/webcam/motion";
  //opencv script configuration
  public final static String MATCHER_SCRIPT = "script/matcher/Matcher.py";
  //twitter configuration
  public final static boolean TWITTER_CONFIGURED = true;
  public final static String APP_SECRET = "";
  public final static String APP_KEY = "";
  public final static String token = "";
  public final static String secret = "";
}
```

Deploy 
--------------

* Launch motion deamon, if it is not already launched:

```sh
sudo /etc/init.d/motion start
```

* Launch gapi :

```sh
sudo java -jar rpiFaceRecognition-0.0.1-SNAPSHOT.jar
```
But how does it work?
-----------

Webcam takes photos when someone moves in the monitored areas using open source project motion, 
these images are processed to detect the face using opencv face detection.
Once the face is detected ,it is processed and compared to stored faces on database using LBPH (Local Binary Patterns Histogram) algorithm in OpenCV , 
to decide if the the face belongs to a person who is allowed to open the door .
If the face of the person match with stored faces database , a signal will be send via GPIO to relay to let the electric current power the door strike using gpio4j, as the same time a tweet will be sended with the photo of person who try to open the door using twitter4j.

have fun :) .

License
----

MIT