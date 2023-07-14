package com.khubla.kriff;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import com.khubla.kriff.domain.RIFFFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.sound.midi.spi.MidiFileReader;

public class RIFFFileReader {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(MidiFileReader.class);

   public void read(String fn, RIFFFile riffFile) {
      InputStream fis = null;
      BufferedInputStream bis = null;
      DataInputStream dis = null;
      try {
         fis = new FileInputStream(fn);
         if (null != fis) {
            /*
             * need BufferedInputStream for mark/reset
             */
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            riffFile.read(dis);
         }
      } catch (final Exception e) {
         logger.error("Exception in read", e);
      } finally {
         try {
            if (null != dis) {
               dis.close();
            }
            if (null != bis) {
               bis.close();
            }
            if (null != fis) {
               fis.close();
            }
         } catch (final Exception e) {
            logger.error("Exception in read", e);
         }
      }
   }
}
