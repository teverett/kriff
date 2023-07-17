/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff.impl.wav;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.RIFFUtil;
import com.khubla.kriff.riff.api.ChunkCallback;
import com.khubla.kriff.riff.api.ChunkHeader;
import com.khubla.kriff.riff.impl.AbstractChunkImpl;

import java.io.IOException;

public class CuesChunkImpl extends AbstractChunkImpl {
   public int dwCuePoints;
   public CuesChunkImpl.WAVCuePoint[] cuePoints;

   public CuesChunkImpl(ChunkHeader chunkHeader) {
      super(chunkHeader);
   }

   @Override
   public void readBody(LittleEndianDataInputStream dis, ChunkCallback chunkCallback) throws IOException {
      this.dwCuePoints = dis.readInt();
      this.cuePoints = new CuesChunkImpl.WAVCuePoint[this.dwCuePoints];
      for (int i = 0; i < this.dwCuePoints; i++) {
         CuesChunkImpl.WAVCuePoint wavCuePoint = new CuesChunkImpl.WAVCuePoint();
         wavCuePoint.dwName = RIFFUtil.readString(dis, 4);
         wavCuePoint.dwPosition = dis.readInt();
         wavCuePoint.fccChunk = RIFFUtil.readString(dis, 4);
         wavCuePoint.dwChunkStart = dis.readInt();
         wavCuePoint.dwBlockStart = dis.readInt();
         wavCuePoint.dwSampleOffset = dis.readInt();
         this.cuePoints[i] = wavCuePoint;
      }
   }

   public static class WAVCuePoint {
      public String dwName;
      public int dwPosition;
      // 4 bytes
      public String fccChunk;
      public int dwChunkStart;
      public int dwBlockStart;
      public int dwSampleOffset;
   }
}
