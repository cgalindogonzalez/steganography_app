package com.scholanova.group2.cecilia.bmpfile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageFileHeader {

	private byte[] headerField = new byte[2]; //to identify BMP file
	private byte[] fileSize = new byte[4]; // size of the BMP file in bytes
	private byte[] reservedField = new byte[4]; // actual value depends on the application that creates the image 
	private byte[] offset = new byte[4]; // starting address of the byte where the bitmap image data (pixel array) can be found.


	/**
	 * getter
	 * @return headerField
	 */
	public byte[] getHeaderField() {
		return this.headerField;
	}

	/**
	 * setter
	 * @param headerField
	 */
	public void setHeaderField(byte[] headerField) {
		this.headerField = headerField;
	}

	/**
	 * getter
	 * @return fileSize
	 */
	public byte[] getFileSize() {
		return this.fileSize;
	}

	/**
	 * setter
	 * @param fileSize
	 */
	public void setFileSize(byte[] fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * getter
	 * @return reservedField
	 */
	public byte[] getReservedField() {
		return this.reservedField;
	}

	/**
	 * setter
	 * @param reservedField
	 */
	public void setReservedField(byte[] reservedField) {
		this.reservedField = reservedField;
	}

	/**
	 * getter
	 * @return offset
	 */
	public byte[] getOffset() {
		return this.offset;
	}

	/**
	 * setter
	 * @param offset
	 */
	public void setOffset(byte[] offset) {
		this.offset = offset;
	}


	/**
	 * get the file type from the byte array headerFile
	 * @param headerFile
	 * @return
	 */
	public String decodeType () {
		String str = new String(this.headerField);
		return str;	
	}


	/**
	 * get the size of the file from the byte array fileSize
	 * @param fileSize
	 * @return
	 */
	public int decodeSize () {
		return ByteBuffer.wrap(this.fileSize).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}


	/**
	 * get the starting address of the byte where the bitmap image data (pixel array) can be found
	 * @param offset
	 * @return
	 */
	public int decodeOffset () {
		return ByteBuffer.wrap(this.offset).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}


	/**
	 * read each field of the file header from a RandomAccesFile object created from a bmp file
	 * @param raf
	 * @throws IOException
	 */
	public void read(RandomAccessFile raf) throws IOException {
		raf.seek(0);
		raf.read(this.headerField);
		raf.read(this.fileSize);
		raf.read(this.reservedField);
		raf.read(this.offset);		
	}


	/**
	 * write the fields of a bmp file header on a RandonAccesFIle object 
	 * @param raf
	 * @throws IOException
	 */
	public void write(RandomAccessFile raf) throws IOException {
		raf.seek(0);
		raf.write(this.headerField);;
		raf.write(this.fileSize);
		raf.write(this.reservedField);
		raf.write(this.offset);	
	}

}
