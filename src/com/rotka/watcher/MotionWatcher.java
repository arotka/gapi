package com.rotka.watcher;

import java.io.IOException;
import java.nio.file.*;


import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

public class MotionWatcher 
{
  private String directoryToWatch;
  IMotionEvent me;

  public MotionWatcher(String directoryToWatch, IMotionEvent event) {
    this.directoryToWatch = directoryToWatch;
    this.me = event;
  }

  public void startWatching()
  {

    // get the directory we want to watch, using the Paths singleton class
    Path toWatch = Paths.get(directoryToWatch);
    if(toWatch == null) 
    {
      throw new UnsupportedOperationException("Directory not found");
    }

    // make a new watch service that we can register interest in 
    // directories and files with.
    WatchService motionWatcher;
    try 
    {
      motionWatcher = toWatch.getFileSystem().newWatchService();
      // start the file watcher thread below
      MotionWatch fileWatcher = new MotionWatch(motionWatcher,this.me);
      Thread th = new Thread(fileWatcher, "FileWatcher");
      th.start();

      // register a file
      toWatch.register(motionWatcher, ENTRY_CREATE);
      th.join();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }    
  }

  /**
   * This Runnable is used to constantly attempt to take from the watch 
   * queue, and will receive all events that are registered with the 
   * fileWatcher it is associated. In this sample for simplicity we 
   * just output the kind of event and name of the file affected to 
   * standard out.
   */
  private class MotionWatch implements Runnable {

    /** the watchService that is passed in from above */
    private WatchService motionFolderWatcher;
    IMotionEvent me;

    public MotionWatch(WatchService motionFolderWatcher, IMotionEvent me) {
      this.motionFolderWatcher= motionFolderWatcher;
      this.me = me;
    }

    /**
     * In order to implement a file watcher, we loop forever 
     * ensuring requesting to take the next item from the file 
     * watchers queue.
     */
    @Override
    public void run() {
      try {
        // get the first event before looping
        WatchKey key = motionFolderWatcher.take();
        while(key != null) 
        {
          // we have a polled event, now we traverse it and 
          // receive all the states from it
          for (WatchEvent event : key.pollEvents()) 
          {
            WatchEvent.Kind<?> kind = event.kind();

            if (kind == OVERFLOW) {
              continue;
            }

            Path filename = (Path) event.context();
            //callback launcher
            System.out.printf("Received %s event for file: %s\n",
                event.kind(), event.context() );
            if(filename.toString().contains("jpg"))
            {
              String imgPath = directoryToWatch + "/" + filename.toString();
              this.me.motionDetected(imgPath);
            }
          }
          key.reset();
          key = motionFolderWatcher.take();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Stopping thread");
    }
  }	
}
