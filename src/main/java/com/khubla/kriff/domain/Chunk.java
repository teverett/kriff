/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.domain;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.api.ChunkCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chunk {
   /**
    * standard chunk header
    */
   private static final int STANDARD_CHUNK_HEADER_SIZE = 8;
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(Chunk.class);
   /**
    * id bytes
    */
   protected String id;
   /**
    * length
    */
   protected int length;
   /**
    * type (only for RIFF and LIST)
    */
   protected String type;
   /**
    * subchunks (RIFF and LIST)
    */
   private List<Chunk> chunks;

   protected void readHeader(LittleEndianDataInputStream dis) throws IOException {
      /*
       * id
       */
      byte[] idbytes = new byte[4];
      dis.read(idbytes);
      id = new String(idbytes);
      /*
       * length
       */
      this.length = dis.readInt();
      /*
       * is riff?
       */
      if (isRIFF()) {
         this.chunks = new ArrayList<Chunk>();
         byte[] typebytes = new byte[4];
         dis.read(typebytes);
         type = new String(typebytes);
      } else {
         this.type = null;
      }
   }

   public void read(LittleEndianDataInputStream dis, ChunkCallback chunkCallback) throws Exception {
      // header
      this.readHeader(dis);
      chunkCallback.chunk(this.id, this.length,0);
      if (isRIFF()) {
         int readbytes = 0;
         while (readbytes < this.length) {
            // get chunk
            Chunk chunk = new Chunk();
            this.chunks.add(chunk);
            chunk.read(dis, chunkCallback);
            readbytes += chunk.length + STANDARD_CHUNK_HEADER_SIZE;
         }
      } else {
         // skip content
         dis.skipBytes(this.length);
      }
   }

   private boolean isRIFF() {
      return (this.id.compareTo("RIFF") == 0);
   }
}
