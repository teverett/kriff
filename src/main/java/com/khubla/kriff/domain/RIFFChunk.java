/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.domain;

import com.google.common.io.LittleEndianDataInputStream;

import java.util.ArrayList;
import java.util.List;

public class RIFFChunk extends AbstractChunk {
   /**
    * expected id bytes
    */
   private final static byte[] ID = { 'R', 'I', 'F', 'F' };
   /*
    * fcc type
    */
   protected final byte[] formType = new byte[4];
   /**
    * chunks
    */
   private final List<Chunk> chunks = new ArrayList<Chunk>();

   public List<Chunk> getChunks() {
      return chunks;
   }

   @Override
   public void read(LittleEndianDataInputStream dis) throws Exception {
      // header
      this.readHeader(dis);
      // check id bytes
      for (int i = 0; i < ID.length; i++) {
         if (id[i] != ID[i]) {
            throw new Exception("Head bytes mismatch");
         }
      }
      //  fcc type
      dis.read(formType);
      // get chunk
      Chunk chunk = ChunkFactory.getInstance().getChunk(new String(formType));
      chunk.read(dis);
   }
}
