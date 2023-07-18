/**
 * Copyright (c) 2023, Tom Everett All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.khubla.kriff.riff.impl;

import com.khubla.kriff.riff.api.ChunkHeader;

public class ChunkHeaderImpl implements ChunkHeader {
   /**
    * id bytes
    */
   protected final String id;
   /**
    * length of chunk with header, not include 1st two DWORDS
    */
   protected final int length;
   /**
    * offset of header in file
    */
   protected final int headerOffset;
   /**
    * offset of data in file
    */
   protected final int dataOffset;
   /**
    * type (only for RIFF and LIST)
    */
   protected final String type;
   /**
    * size of header
    */
   protected final int headerSize;
   /**
    * index, from the start of the file, of the last byte of this chunk
    */
   protected int lastByteIndex;

   public ChunkHeaderImpl(String id, int length, int headerOffset, int dataOffset, int headerSize, String type) {
      this.id = id;
      this.length = length;
      this.headerOffset = headerOffset;
      this.dataOffset = dataOffset;
      this.type = type;
      this.headerSize = headerSize;
   }

   public ChunkHeaderImpl(String id, int length, int headerOffset, int dataOffset, int headerSize) {
      this.id = id;
      this.length = length;
      this.headerOffset = headerOffset;
      this.dataOffset = dataOffset;
      this.headerSize = headerSize;
      this.type = null;
   }

   public String getId() {
      return id;
   }

   public int getLength() {
      return length;
   }

   @Override
   public String describe() {
      if (null == type) {
         return "Chunk '" + id + "' of length " + length + " header offset " + this.headerOffset + " header size " + this.headerSize + " data offset " + this.dataOffset;
      } else {
         return "Chunk '" + id + "' of length " + length + " header offset " + this.headerOffset + " header size " + this.headerSize + " data offset " + this.dataOffset + " of type '" + this.type + "'";
      }
   }

   public String getType() {
      return type;
   }

   @Override
   public int getLastByteIndex() {
      return lastByteIndex;
   }

   public void setLastByteIndex(int lastByteIndex) {
      this.lastByteIndex = lastByteIndex;
   }

   public int getHeaderOffset() {
      return headerOffset;
   }

   public int getDataOffset() {
      return dataOffset;
   }

   public int getHeaderSize() {
      return headerSize;
   }
}
