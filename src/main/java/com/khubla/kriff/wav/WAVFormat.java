/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.wav;

import java.util.HashMap;
import java.util.Map;

public class WAVFormat {
   public Format wFormatTag;
   public short wChannels;
   public int dwSamplesPerSec;
   public int dwAvgBytesPerSec;
   public short wBlockAlign;
   // PCM only
   public short wBitsPerSample;

   public enum Format {
      PCM(1), MULAW(0x0101), ALAW(0x0102), ADPCM(0x0103);
      // Mapping difficulty to difficulty id
      private static final Map<Integer, Format> _map = new HashMap<Integer, Format>();

      static {
         for (Format format : Format.values())
            _map.put(format.value, format);
      }

      int value;

      Format(int value) {
         this.value = value;
      }

      public static Format from(int format) {
         return _map.get(format);
      }
   }
}
