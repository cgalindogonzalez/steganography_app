package com.scholanova.group2.cecilia.bmpfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BMPFileReader {
	
	private ImageFileHeader fileHeader; 
	private ImageBMPHeader bmpHeader; 
	private ImageBody body; 
	
	

	/**
	 * getter
	 * @return fileHeader
	 */
	public ImageFileHeader getFileHeader() {
		return this.fileHeader;
	}
	

	/**
	 * setter
	 * @param fileHeader
	 */
	public void setFileHeader(ImageFileHeader fileHeader) {
		this.fileHeader = fileHeader;
	}

	/**
	 * getter
	 * @return bmpHeader
	 */
	public ImageBMPHeader getBmpHeader() {
		return this.bmpHeader;
	}


	/**
	 * setter
	 * @param bmpHeader
	 */
	public void setBmpHeader(ImageBMPHeader bmpHeader) {
		this.bmpHeader = bmpHeader;
	}
	
	
	/**
	 * getter
	 * @return body
	 */
	public ImageBody getBody() {
		return this.body;
	}


	/**
	 * setter
	 * @param body
	 */
	public void setBody(ImageBody body) {
		this.body = body;
	}
	
	
	/**
	 * read a bmp image file and divide it into his parts (file header, bmp header and body)
	 * @param file
	 * @throws IOException 
	 */
	public void readBMPFile(File file) throws IOException {

			RandomAccessFile raf = new RandomAccessFile(file,"r");
			
			this.fileHeader = new ImageFileHeader();
			this.fileHeader.read(raf);

			this.bmpHeader = new ImageBMPHeader();
			this.bmpHeader.read(raf);
			
			this.body = new ImageBody();
			int offset = this.fileHeader.decodeOffset();
			int size = this.bmpHeader.decodeImageSize();
			this.body.read(raf, offset, size);
			
	}
	

	public void writeBMPFile(File file) throws IOException {
		
		RandomAccessFile raf = new RandomAccessFile(file,"rw");
		
		this.fileHeader.write(raf);
		this.bmpHeader.write(raf);
		int offset = this.fileHeader.decodeOffset();
		this.body.write(raf, offset);
			
	}

	
	public static void main (String[] args) {

		
	}



	

}
