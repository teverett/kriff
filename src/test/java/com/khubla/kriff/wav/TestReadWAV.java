/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.wav;

import com.khubla.kriff.riff.RIFFFileReader;
import com.khubla.kriff.riff.api.Chunk;
import com.khubla.kriff.riff.callback.ReportingChunkCallback;
import com.khubla.kriff.riff.impl.DataChunkImpl;
import com.khubla.kriff.riff.impl.LISTChunkImpl;
import com.khubla.kriff.riff.impl.wav.CuesChunkImpl;
import com.khubla.kriff.riff.impl.wav.FmtChunkImpl;
import com.khubla.kriff.riff.impl.wav.NoteChunkImpl;
import com.khubla.kriff.riff.impl.wav.PEAKChunkImpl;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestReadWAV {
   @Test
   public void test1() {
      try {
         final int filesize = 1323044;
         InputStream is = TestReadWAV.class.getResourceAsStream("/PinkPanther30.wav");
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         RIFFFileReader riffFileReader = new RIFFFileReader();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         FmtChunkImpl fmtChunk = (FmtChunkImpl) rootChunk.getChunks().get(0);
         assertNotNull(fmtChunk);
         assertEquals(44100, fmtChunk.dwAvgBytesPerSec);
         assertEquals(FmtChunkImpl.Format.PCM, fmtChunk.wFormatTag);
         assertEquals(22050, fmtChunk.dwSamplesPerSec);
         assertEquals(2, fmtChunk.wBlockAlign);
         assertEquals(1, fmtChunk.wChannels);
         assertEquals(16, fmtChunk.wBitsPerSample);
         DataChunkImpl dataChunk = (DataChunkImpl) rootChunk.getChunks().get(1);
         assertNotNull(dataChunk);
         assertEquals(dataChunk.getData().length, dataChunk.getChunkHeader().getLength());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test2() {
      try {
         final int filesize = 118200;
         InputStream is = TestReadWAV.class.getResourceAsStream("/stereol.wav");
         RIFFFileReader riffFileReader = new RIFFFileReader();
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         FmtChunkImpl fmtChunk = (FmtChunkImpl) rootChunk.getChunks().get(0);
         assertNotNull(fmtChunk);
         assertEquals(88200, fmtChunk.dwAvgBytesPerSec);
         assertEquals(FmtChunkImpl.Format.PCM, fmtChunk.wFormatTag);
         assertEquals(22050, fmtChunk.dwSamplesPerSec);
         assertEquals(4, fmtChunk.wBlockAlign);
         assertEquals(2, fmtChunk.wChannels);
         assertEquals(16, fmtChunk.wBitsPerSample);
         // peak
         PEAKChunkImpl peakChunk = (PEAKChunkImpl) rootChunk.getChunks().get(1);
         assertNotNull(peakChunk);
         assertEquals(1, peakChunk.version);
         assertEquals(2, peakChunk.peaks.size());
         // cue
         CuesChunkImpl cuesChunk = (CuesChunkImpl) rootChunk.getChunks().get(2);
         assertNotNull(cuesChunk);
         assertEquals(1, cuesChunk.dwCuePoints);
         // list
         LISTChunkImpl listChunkImpl = (LISTChunkImpl) rootChunk.getChunks().get(3);
         assertNotNull(listChunkImpl);
         assertEquals(1, listChunkImpl.getChunks().size());
         // note
         NoteChunkImpl notesChunk = (NoteChunkImpl) listChunkImpl.getChunks().get(0);
         assertNotNull(notesChunk);
         assertEquals("sfif", notesChunk.dwName);
         assertEquals("DATE\nE24F3437", notesChunk.note);
         // data
         DataChunkImpl dataChunk = (DataChunkImpl) rootChunk.getChunks().get(4);
         assertNotNull(dataChunk);
         assertEquals(dataChunk.getData().length, dataChunk.getChunkHeader().getLength());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test3() {
      try {
         final int filesize = 2706;
         InputStream is = TestReadWAV.class.getResourceAsStream("/voxware.wav");
         RIFFFileReader riffFileReader = new RIFFFileReader();
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         assertEquals(3, rootChunk.getChunks().size());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test4() {
      try {
         final int filesize = 9370;
         InputStream is = TestReadWAV.class.getResourceAsStream("/truspech.wav");
         RIFFFileReader riffFileReader = new RIFFFileReader();
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         assertEquals(3, rootChunk.getChunks().size());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test5() {
      try {
         final int filesize = 234278;
         InputStream is = TestReadWAV.class.getResourceAsStream("/stereofl.wav");
         RIFFFileReader riffFileReader = new RIFFFileReader();
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         assertEquals(6, rootChunk.getChunks().size());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test6() {
      try {
         final int filesize = 47252;
         InputStream is = TestReadWAV.class.getResourceAsStream("/M1F1-mulawWE-AFsp.wav");
         RIFFFileReader riffFileReader = new RIFFFileReader();
         ReportingChunkCallback reportingChunkCallback = new ReportingChunkCallback();
         Chunk rootChunk = riffFileReader.read(is, reportingChunkCallback);
         int lastByte = rootChunk.getChunkHeader().getLastByteIndex();
         assertEquals(filesize, lastByte);
         assertNotNull(rootChunk);
         assertEquals(5, rootChunk.getChunks().size());
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }
}
