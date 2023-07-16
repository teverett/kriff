/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.api.ChunkCallback;
import com.khubla.kriff.riff.domain.domain.RIFFFile;
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

   public RIFFFile read(InputStream inputStream, ChunkCallback chunkCallback) throws Exception {
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
            ret.read(dis, chunkCallback);
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

   public RIFFFile read(String fn, ChunkCallback chunkCallback) throws Exception {
      InputStream fis = null;
      try {
         fis = new FileInputStream(fn);
         if (null != fis) {
            return this.read(fis, chunkCallback);
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
