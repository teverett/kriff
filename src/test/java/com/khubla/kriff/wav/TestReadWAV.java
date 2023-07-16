/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.wav;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestReadWAV {
   @Test
   public void test1() {
      try {
         InputStream is = TestReadWAV.class.getResourceAsStream("/PinkPanther30.wav");
         WAVFileReader wavFileReader = new WAVFileReader();
         WAVFile wavFile = wavFileReader.read(is);
         assertNotNull(wavFile);
         assertNotNull(wavFile.getData());
         assertNotNull(wavFile.getWavFormat());
         assertEquals(44100, wavFile.getWavFormat().dwAvgBytesPerSec);
         assertEquals(WAVFormat.Format.PCM, wavFile.getWavFormat().wFormatTag);
         assertEquals(22050, wavFile.getWavFormat().dwSamplesPerSec);
         assertEquals(2, wavFile.getWavFormat().wBlockAlign);
         assertEquals(1, wavFile.getWavFormat().wChannels);
         assertEquals(16, wavFile.getWavFormat().wBitsPerSample);
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }

   @Test
   public void test2() {
      try {
         InputStream is = TestReadWAV.class.getResourceAsStream("/stereol.wav");
         WAVFileReader wavFileReader = new WAVFileReader();
         WAVFile wavFile = wavFileReader.read(is);
         // wav
         assertNotNull(wavFile);
         assertNotNull(wavFile.getData());
         assertNotNull(wavFile.getWavFormat());
         assertEquals(88200, wavFile.getWavFormat().dwAvgBytesPerSec);
         assertEquals(WAVFormat.Format.PCM, wavFile.getWavFormat().wFormatTag);
         assertEquals(22050, wavFile.getWavFormat().dwSamplesPerSec);
         assertEquals(4, wavFile.getWavFormat().wBlockAlign);
         assertEquals(2, wavFile.getWavFormat().wChannels);
         assertEquals(16, wavFile.getWavFormat().wBitsPerSample);
         // cue
         assertNotNull(wavFile.getWavCues());
         assertEquals(1, wavFile.getWavCues().dwCuePoints);
         // note
         assertNotNull(wavFile.getWavNote());
         assertEquals("sfif", wavFile.getWavNote().dwName);
         assertEquals("DATE\nE24F3437", wavFile.getWavNote().note);
      } catch (final Exception e) {
         e.printStackTrace();
         fail();
      }
   }
}
