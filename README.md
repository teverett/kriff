[![CI](https://github.com/teverett/kriff/actions/workflows/main.yml/badge.svg)](https://github.com/teverett/kriff/actions/workflows/main.yml)

# KRIFF

A Java library for reading [RIFF](https://en.wikipedia.org/wiki/Resource_Interchange_File_Format) files

## Usage

Use the type [RIFFFile](https://github.com/teverett/kriff/blob/main/src/main/java/com/khubla/kriff/domain/RIFFFile.java) to read the RIFF file from an `InputStream`. 
KRIFF will call the callback [ChunkCallback](https://github.com/teverett/kriff/blob/main/src/main/java/com/khubla/kriff/api/ChunkCallback.java) for each chunk in the file.
