/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff;

import com.khubla.kriff.riff.api.ChunkHeader;
import com.khubla.kriff.riff.impl.*;
import com.khubla.kriff.riff.impl.rmid.RMIDChunkImpl;
import com.khubla.kriff.riff.impl.wav.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkFactory {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(ChunkFactory.class);

   public static AbstractChunkImpl getChunk(ChunkHeader chunkHeader) throws Exception {
      switch (chunkHeader.getId()) {
         case "data":
            return new DataChunkImpl(chunkHeader);
         case "fmt":
            return new FmtChunkImpl(chunkHeader);
         case "cue":
            return new CuesChunkImpl(chunkHeader);
         case "label":
            return new LabelChunkImpl(chunkHeader);
         case "note":
            return new NoteChunkImpl(chunkHeader);
         case "PEAK":
            return new PEAKChunkImpl(chunkHeader);
         case "DISP":
            return new DISPChunkImpl(chunkHeader);
         case "RIFF":
            return new RIFFChunkImpl(chunkHeader);
         case "LIST":
            return new LISTChunkImpl(chunkHeader);
         case "LIS":
            return new LISChunkImpl(chunkHeader);
         case "INF":
            return new INFChunkImpl(chunkHeader);
         case "JUNK":
            return new JUNKChunkImpl(chunkHeader);
         case "PAD":
            return new PADChunkImpl(chunkHeader);
         case "LTXT":
            return new LTXTChunkImpl(chunkHeader);
         case "fact":
         case "FACT":
            return new FACTChunkImpl(chunkHeader);
         case "PLST":
            return new PLSTChunkImpl(chunkHeader);
         case "RMID":
            return new RMIDChunkImpl(chunkHeader);
         case "FILE":
            return new FILEChunkImpl(chunkHeader);
         default:
            // some chunk we don't know
            return new NULLChunkImpl(chunkHeader);
      }
   }
}
