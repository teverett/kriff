/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff;

import com.khubla.kriff.riff.api.Chunk;
import com.khubla.kriff.riff.api.ChunkHeader;
import com.khubla.kriff.riff.callback.ReportingChunkCallback;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestReadRIFF {
   @Test
   public void test1() {
      try {
         final int filesize = 1323044;
         InputStream is = TestReadRIFF.class.getResourceAsStream("/PinkPanther30.wav");
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         RIFFFileReader riffFileReader = new RIFFFileReader();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         // root
         ChunkHeader chunkHeader = rootChunk.getChunkHeader();
         assertNotNull(chunkHeader);
         assertEquals(0, chunkHeader.getId().compareTo("RIFF"));
         assertEquals(0, chunkHeader.getType().compareTo("WAVE"));
         assertEquals(1323036, chunkHeader.getLength());
         assertEquals(0, chunkHeader.getHeaderOffset());
         assertEquals(12, chunkHeader.getDataOffset());
         assertNotNull(rootChunk.getChunks());
         assertEquals(2, rootChunk.getChunks().size());
         // 1
         Chunk chunk1 = rootChunk.getChunks().get(0);
         assertNull(chunk1.getChunks());
         ChunkHeader chunkHeader1 = chunk1.getChunkHeader();
         assertNotNull(chunkHeader1);
         assertEquals(0, chunkHeader1.getId().compareTo("fmt"));
         assertEquals(16, chunkHeader1.getLength());
         assertEquals(12, chunkHeader1.getHeaderOffset());
         assertEquals(20, chunkHeader1.getDataOffset());
         // 2
         Chunk chunk2 = rootChunk.getChunks().get(1);
         assertNull(chunk2.getChunks());
         ChunkHeader chunkHeader2 = chunk2.getChunkHeader();
         assertNotNull(chunkHeader2);
         assertEquals(0, chunkHeader2.getId().compareTo("data"));
         assertEquals(1323000, chunkHeader2.getLength());
         assertEquals(36, chunkHeader2.getHeaderOffset());
         assertEquals(44, chunkHeader2.getDataOffset());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }
}
