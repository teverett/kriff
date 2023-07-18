/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.api.Chunk;
import com.khubla.kriff.riff.api.ChunkCallback;
import com.khubla.kriff.riff.api.ChunkHeader;
import com.khubla.kriff.riff.impl.AbstractChunkImpl;
import com.khubla.kriff.riff.impl.ChunkHeaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ChunkReader {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(ChunkFactory.class);
   /**
    * running byte count
    */
   private int count;

   public ChunkReader(int count) {
      this.count = count;
   }

   public int getCount() {
      return count;
   }

   protected ChunkHeader readHeader(LittleEndianDataInputStream dis) throws IOException {
      ChunkHeader ret = null;
      /*
       * id
       */
      String id = RIFFUtil.readString(dis, 4);

      /*
       * length
       */
      int length = dis.readInt();
      /*
       * is riff or list?
       */
      if (isCompound(id)) {
         String type = RIFFUtil.readString(dis, 4);
         ret = new ChunkHeaderImpl(id, length, count, count + 12, 12, type);
         this.count += 12;
      } else {
         ret = new ChunkHeaderImpl(id, length, count, count + 8, 8);
         this.count += 8;
      }
      return ret;
   }

   private boolean isCompound(String id) {
      return ((id.compareTo("RIFF") == 0) || (id.compareTo("LIST") == 0) || (id.compareTo("LIS") == 0));
   }

   public Chunk read(LittleEndianDataInputStream dis, ChunkCallback chunkCallback) throws Exception {
      try {
         // header
         ChunkHeader chunkHeader = this.readHeader(dis);
         logger.info(chunkHeader.describe());
         if (null != chunkCallback) {
            chunkCallback.chunkStart(chunkHeader);
         }
         // chunk
         AbstractChunkImpl chunk = ChunkFactory.getChunk(chunkHeader);
         // read content
         chunk.readBody(dis, chunkCallback);
         count += chunkHeader.getLength();
         // callback
         if (null != chunkCallback) {
            chunkCallback.chunkEnd(chunk);
         }
         return chunk;
      } catch (Exception e) {
         throw new Exception("Exception in read", e);
      }
   }
}
