/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff.domain.domain;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.api.Chunk;
import com.khubla.kriff.riff.api.ChunkCallback;
import com.khubla.kriff.riff.api.ChunkHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChunkImpl implements Chunk {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(ChunkImpl.class);
   /**
    * subchunks (RIFF and LIST)
    */
   private List<Chunk> chunks;
   /**
    * running byte count
    */
   private int count;
   /**
    * header
    */
   private ChunkHeader chunkHeader;

   public ChunkImpl() {
      this.count = 0;
   }

   public ChunkImpl(int count) {
      this.count = count;
   }

   protected void readHeader(LittleEndianDataInputStream dis) throws IOException {
      /*
       * id
       */
      String id = readString(dis, 4);

      /*
       * length
       */
      int length = dis.readInt();
      /*
       * is riff or list?
       */
      if (isCompound(id)) {
         this.chunks = new ArrayList<Chunk>();
         String type = readString(dis, 4);
         this.chunkHeader = new ChunkHeaderImpl(id, length, count, count + 12, type);
         this.count += 12;
      } else {
         this.chunkHeader = new ChunkHeaderImpl(id, length, count, count + 8);
         this.count += 8;
      }
   }

   public ChunkHeader getChunkHeader() {
      return chunkHeader;
   }

   public List<Chunk> getChunks() {
      return this.chunks;
   }

   public void read(LittleEndianDataInputStream dis, ChunkCallback chunkCallback) throws Exception {
      // header
      this.readHeader(dis);
      // callback
      if (null != chunkCallback) {
         chunkCallback.chunk(this);
      }
      // contents
      if (isCompound(this.chunkHeader.getId())) {
         while (count < this.chunkHeader.getLength()) {
            // get chunk
            ChunkImpl RIFFChunk = new ChunkImpl(this.count);
            RIFFChunk.read(dis, chunkCallback);
            this.chunks.add(RIFFChunk);
            this.count = RIFFChunk.count;
         }
      } else {
         // skip content
         dis.skipBytes(this.chunkHeader.getLength());
         count += this.chunkHeader.getLength();
      }
   }

   private boolean isCompound(String id) {
      return ((id.compareTo("RIFF") == 0) || (id.compareTo("LIST") == 0));
   }

   private String readString(LittleEndianDataInputStream dis, int len) throws IOException {
      byte[] idbytes = new byte[len];
      dis.read(idbytes);
      return new String(idbytes).trim();
   }
}
