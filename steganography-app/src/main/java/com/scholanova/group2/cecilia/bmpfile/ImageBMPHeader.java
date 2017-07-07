package com.scholanova.group2.cecilia.bmpfile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageBMPHeader {
	
	private byte[] headerSize = new byte[4]; //the size of this header, i.e. 40 bytes
	private byte[] imageWidth = new byte[4]; // the bitmap width in pixels (signed integer)
	private byte[] imageHeight = new byte[4]; // the bitmap height in pixels (signed integer)
	private byte[] colorPlanes = new byte[2]; // the number of color planes (must be 1)
	private byte[] colorDepth = new byte[2]; // the number of bits per pixel, which is the color depth of the image. Typical values are 1, 4, 8, 16, 24 and 32
	private byte[] compressionMethod = new byte[4]; // the compression method being used (0 means non compression) 
	private byte[] imageSize = new byte[4]; // the image size. This is the size of the raw bitmap data
	private byte[] horizontalResolution = new byte[4]; // pixel per meter, signed integer 
	private byte[] verticalResolution = new byte[4]; // pixel per meter, signed integer
	private byte[] paletteColors = new byte[4]; // the number of colors in the color palette, or 0 to default to 2n 
 	private byte[] importantColors = new byte[4]; // the number of important colors used, or 0 when every color is important; generally ignored
	
 	/**
 	 * getter
 	 * @return headerSize
 	 */
 	public byte[] getHeaderSize() {
		return this.headerSize;
	}
	
	
 	/**
 	 * setter
 	 * @param headerSize
 	 */
	public void setHeaderSize(byte[] headerSize) {
		this.headerSize = headerSize;
	}

	
	/**
	 * getter
	 * @return imageWidth
	 */
	public byte[] getImageWidth() {
		return this.imageWidth;
	}
	
	
	/**
	 * setter
	 * @param imageWidth
	 */
	public void setImageWidth(byte[] imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * getter
	 * @return imageHeight
	 */
	public byte[] getImageHeight() {
		return this.imageHeight;
	}


	
	/**
	 * setter
	 * @param imageHeight
	 */
	public void setImageHeight(byte[] imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * getter
	 * @return colorPlanes
	 */
	public byte[] getColorPlanes() {
		return this.colorPlanes;
	}
	
	
	
	/**
	 * setter
	 * @param colorPlanes
	 */
	public void setColorPlanes(byte[] colorPlanes) {
		this.colorPlanes = colorPlanes;
	}

	/**
	 * getter
	 * @return colorDepth
	 */
	public byte[] getColorDepth() {
		return this.colorDepth;
	}
	
	
	/**
	 * setter
	 * @param colorDepth
	 */
	public void setColorDepth(byte[] colorDepth) {
		this.colorDepth = colorDepth;
	}

	/**
	 * getter
	 * @return compressionMethod
	 */
	public byte[] getCompressionMethod() {
		return this.compressionMethod;
	}

	
	/**
	 * setter
	 * @param compressionMethod
	 */
	public void setCompressionMethod(byte[] compressionMethod) {
		this.compressionMethod = compressionMethod;
	}

	
	/**
	 * getter
	 * @return imageSize
	 */
	public byte[] getImageSize() {
		return this.imageSize;
	}
	
	
	/**
	 * setter
	 * @param imageSize
	 */
	public void setImageSize(byte[] imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * getter
	 * @return horizontalResolution
	 */
	public byte[] getHorizontalResolution() {
		return this.horizontalResolution;
	}
	
	
	/**
	 * setter
	 * @param horizontalResolution
	 */
	public void setHorizontalResolution(byte[] horizontalResolution) {
		this.horizontalResolution = horizontalResolution;
	}

	
	/**
	 * getter
	 * @return verticalResolution
	 */
	public byte[] getVerticalResolution() {
		return this.verticalResolution;
	}
	
	
	/**
	 * setter
	 * @param verticalResolution
	 */
	public void setVerticalResolution(byte[] verticalResolution) {
		this.verticalResolution = verticalResolution;
	}

	/**
	 * getter
	 * @return paletteColors
	 */
	public byte[] getPaletteColors() {
		return this.paletteColors;
	}
	
	
	/**
	 * setter
	 * @param paletteColors
	 */
	public void setPaletteColors(byte[] paletteColors) {
		this.paletteColors = paletteColors;
	}

	/**
	 * getter
	 * @return importantColors
	 */
	public byte[] getImportantColors() {
		return this.importantColors;
	}

	
	/**
	 * setter
	 * @param importantColors
	 */
	public void setImportantColors(byte[] importantColors) {
		this.importantColors = importantColors;
	}
	
	/**
	 * get the size of the bmp header from the byte array headerSize
	 * @param headerSize
	 * @return
	 */
	public int decodeSizeOfImageHeader () {
		return ByteBuffer.wrap(this.headerSize).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	
	/**
	 * get the width of the image in pixels from the byte array imageWidth
	 * @param imageWidth
	 * @return
	 */
	public int decodeImageWidth () {
		return ByteBuffer.wrap(this.imageWidth).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * get the height of the image in pixels from the byte array imageHeight
	 * @param imageHeight
	 * @return
	 */
	public int decodeImageHeight () {
		return ByteBuffer.wrap(this.imageHeight).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * get the number of color planes from the byte array colorPlanes
	 * @param colorPlanes
	 * @return
	 */
	public int decodeColorPlanes () {
		return ByteBuffer.wrap(this.colorPlanes).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}
	
	/**
	 * get the number of bits per pixel from the byte array colorDepth
	 * @param colorDepth
	 * @return
	 */
	public int decodeColorDepth () {
		return ByteBuffer.wrap(this.colorDepth).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}
	
	/**
	 * get the compression method used (0 means non compression) from the byte array compressionMethod  
	 * @param compressionMethod
	 * @return
	 */
	public int decodeCompressionMethod () {
		return ByteBuffer.wrap(this.compressionMethod).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * 
	 * @return
	 */
	public int decodeImageSize() {
		return ByteBuffer.wrap(this.imageSize).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	
	/**
	 * get the horizontal resolution in pixel per meter from the byte array horizontalResolution  
	 * @param horizontalResolution
	 * @return
	 */
	public int decodeHorizontalResolution () {
		return ByteBuffer.wrap(this.horizontalResolution).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	
	/**
	 * get the vertical resolution in pixel per meter from the byte array verticalResolution  
	 * @param verticalResolution
	 * @return
	 */
	public int decodeVerticalResolution () {
		return ByteBuffer.wrap(this.verticalResolution).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * get the number of colors in the color palette from the byte array paletteColors  
	 * @param paletteColors
	 * @return
	 */
	public int decodePaletteColors () {
		return ByteBuffer.wrap(this.paletteColors).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * get the number of important colors used from the byte array importantColors  
	 * @param importantColors
	 * @return
	 */
	public int decodeImportantColors () {
		return ByteBuffer.wrap(this.importantColors).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}

	/**
	 * 
	 * @param raf
	 * @throws IOException
	 */
	public void read(RandomAccessFile raf) throws IOException {
		raf.seek(14);
		raf.read(this.headerSize);
		raf.read(this.imageWidth);
		raf.read(this.imageHeight);
		raf.read(this.colorPlanes);
		raf.read(this.colorDepth);
		raf.read(this.compressionMethod);
		raf.read(this.imageSize);
		raf.read(this.horizontalResolution);
		raf.read(this.verticalResolution);
		raf.read(this.paletteColors);
		raf.read(this.importantColors);
		
		
	}

	/**
	 * 
	 * @param raf
	 * @throws IOException
	 */
	public void write(RandomAccessFile raf) throws IOException {
		raf.seek(14);
		raf.write(this.headerSize);
		raf.write(this.imageWidth);
		raf.write(this.imageHeight);
		raf.write(this.colorPlanes);
		raf.write(this.colorDepth);
		raf.write(this.compressionMethod);
		raf.write(this.imageSize);
		raf.write(this.horizontalResolution);
		raf.write(this.verticalResolution);
		raf.write(this.paletteColors);
		raf.write(this.importantColors);
		
	}
	
}
