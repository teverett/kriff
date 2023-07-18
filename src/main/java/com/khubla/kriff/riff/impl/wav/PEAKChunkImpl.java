/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff.impl.wav;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.api.ChunkCallback;
import com.khubla.kriff.riff.api.ChunkHeader;
import com.khubla.kriff.riff.impl.AbstractChunkImpl;

import java.util.ArrayList;
import java.util.List;

public class PEAKChunkImpl extends AbstractChunkImpl {
   public int version;
   public int timestamp;  // unix timestamp
   public List<PPEAK> peaks = new ArrayList<PPEAK>();

   public PEAKChunkImpl(ChunkHeader chunkHeader) {
      super(chunkHeader);
   }

   @Override
   public void readBody(LittleEndianDataInputStream dis, ChunkCallback chunkCallback) throws Exception {
      this.version = dis.readInt();
      this.timestamp = dis.readInt();
      int c = (this.chunkHeader.getLength() - 8) / 8;
      for (int i = 0; i < c; i++) {
         PPEAK ppeak = new PPEAK();
         ppeak.peak = dis.readInt();
         ppeak.position = dis.readInt();
         peaks.add(ppeak);
      }
   }

   public static class PPEAK {
      int peak;
      int position;
   }
}
