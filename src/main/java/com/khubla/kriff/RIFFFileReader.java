package com.khubla.kriff;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.domain.RIFFFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class RIFFFileReader {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(RIFFFileReader.class);

   public RIFFFile read(InputStream inputStream) throws Exception {
      RIFFFile ret = new RIFFFile();
      BufferedInputStream bis = null;
      LittleEndianDataInputStream dis = null;
      try {
         if (null != inputStream) {
            /*
             * need BufferedInputStream for mark/reset
             */
            bis = new BufferedInputStream(inputStream);
            dis = new LittleEndianDataInputStream(bis);
            ret.read(dis);
         }
         return ret;
      } catch (final Exception e) {
         logger.error("Exception in read", e);
         throw new Exception("Exception in read", e);
      } finally {
         try {
            if (null != dis) {
               dis.close();
            }
            if (null != bis) {
               bis.close();
            }
         } catch (final Exception e) {
            logger.error("Exception in read", e);
         }
      }
   }

   public RIFFFile read(String fn) throws Exception {
      InputStream fis = null;
      try {
         fis = new FileInputStream(fn);
         if (null != fis) {
            return this.read(fis);
         } else {
            return null;
         }
      } catch (final Exception e) {
         logger.error("Exception in read", e);
         throw new Exception("Exception in read", e);
      } finally {
         try {
            if (null != fis) {
               fis.close();
            }
         } catch (final Exception e) {
            logger.error("Exception in read", e);
         }
      }
   }
}
