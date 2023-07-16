/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.wav;

import com.google.common.io.LittleEndianDataInputStream;
import com.khubla.kriff.riff.RIFFFileReader;
import com.khubla.kriff.riff.RIFFUtil;
import com.khubla.kriff.riff.api.Chunk;
import com.khubla.kriff.riff.api.ChunkCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WAVFileReader implements ChunkCallback {
   /**
    * logger
    */
   private static final Logger logger = LogManager.getLogger(WAVFileReader.class);
   /**
    * RIFF reader
    */
   private final RIFFFileReader riffFileReader = new RIFFFileReader();
   /**
    * result
    */
   private final WAVFile wavFile = new WAVFile();

   public WAVFile read(InputStream inputStream) throws Exception {
      riffFileReader.read(inputStream, this);
      return wavFile;
   }

   public WAVFile read(String fn) throws Exception {
      riffFileReader.read(fn, this);
      return wavFile;
   }

   @Override
   public void chunk(Chunk chunk) throws IOException {
      if (chunk.getChunkHeader().getId().compareTo("fmt") == 0) {
         readFmt(chunk);
      } else if (chunk.getChunkHeader().getId().compareTo("data") == 0) {
         readData(chunk);
      } else if (chunk.getChunkHeader().getId().compareTo("PEAK") == 0) {
         readPeak(chunk);
      } else if (chunk.getChunkHeader().getId().compareTo("cue") == 0) {
         readCue(chunk);
      } else if (chunk.getChunkHeader().getId().compareTo("note") == 0) {
         readNote(chunk);
      }
   }

   private void readFmt(Chunk chunk) throws IOException {
      WAVFormat wavFormat = new WAVFormat();
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(chunk.getData());
      LittleEndianDataInputStream littleEndianDataInputStream = new LittleEndianDataInputStream(byteArrayInputStream);
      wavFormat.wFormatTag = WAVFormat.Format.from(littleEndianDataInputStream.readShort());
      wavFormat.wChannels = littleEndianDataInputStream.readShort();
      wavFormat.dwSamplesPerSec = littleEndianDataInputStream.readInt();
      wavFormat.dwAvgBytesPerSec = littleEndianDataInputStream.readInt();
      wavFormat.wBlockAlign = littleEndianDataInputStream.readShort();
      if (wavFormat.wFormatTag == WAVFormat.Format.PCM) {
         wavFormat.wBitsPerSample = littleEndianDataInputStream.readShort();
      }
      this.wavFile.setWavFormat(wavFormat);
   }

   private void readData(Chunk chunk) {
      this.wavFile.setData(chunk.getData());
   }

   private void readPeak(Chunk chunk) {
   }

   private void readCue(Chunk chunk) throws IOException {
      WAVCues wavCues = new WAVCues();
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(chunk.getData());
      LittleEndianDataInputStream littleEndianDataInputStream = new LittleEndianDataInputStream(byteArrayInputStream);
      wavCues.dwCuePoints = littleEndianDataInputStream.readInt();
      wavCues.cuePoints = new WAVCues.WAVCuePoint[wavCues.dwCuePoints];
      for (int i = 0; i < wavCues.dwCuePoints; i++) {
         WAVCues.WAVCuePoint wavCuePoint = new WAVCues.WAVCuePoint();
         wavCuePoint.dwName = RIFFUtil.readString(littleEndianDataInputStream, 4);
         wavCuePoint.dwPosition = littleEndianDataInputStream.readInt();
         wavCuePoint.fccChunk = RIFFUtil.readString(littleEndianDataInputStream, 4);
         wavCuePoint.dwChunkStart = littleEndianDataInputStream.readInt();
         wavCuePoint.dwBlockStart = littleEndianDataInputStream.readInt();
         wavCuePoint.dwSampleOffset = littleEndianDataInputStream.readInt();
         wavCues.cuePoints[i] = wavCuePoint;
      }
      this.wavFile.setWavCues(wavCues);
   }

   private void readNote(Chunk chunk) {
   }
}
