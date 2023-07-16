/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.wav;

public class WAVFile {
   private WAVFormat wavFormat;
   private WAVCues wavCues;
   private byte[] data;
   private WAVNoteOrLabel wavNote;
   private WAVNoteOrLabel wavLabel;

   public WAVNoteOrLabel getWavNote() {
      return wavNote;
   }

   public void setWavNote(WAVNoteOrLabel wavNote) {
      this.wavNote = wavNote;
   }

   public WAVNoteOrLabel getWavLabel() {
      return wavLabel;
   }

   public void setWavLabel(WAVNoteOrLabel wavLabel) {
      this.wavLabel = wavLabel;
   }

   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public WAVFormat getWavFormat() {
      return wavFormat;
   }

   public void setWavFormat(WAVFormat wavFormat) {
      this.wavFormat = wavFormat;
   }

   public WAVCues getWavCues() {
      return wavCues;
   }

   public void setWavCues(WAVCues wavCues) {
      this.wavCues = wavCues;
   }
}
