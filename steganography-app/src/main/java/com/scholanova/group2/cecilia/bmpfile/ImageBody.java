package com.scholanova.group2.cecilia.bmpfile;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ImageBody {

	private byte[] rawBytes;


	/**
	 * getter
	 * @return rawBytes
	 */
	public byte[] getRawBytes() {
		return rawBytes;
	}

	/**
	 * setter
	 * @param rawBytes
	 */
	public void setRawBytes(byte[] rawBytes) {
		this.rawBytes = rawBytes;
	}


	/**
	 * convert a buffered image into a byte array 
	 * @param bi
	 * @return
	 * @throws IOException 
	 */
	public byte[] byteArrayFromImage(BufferedImage bi) throws IOException {
		return ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
	}


	/**
	 * convert a byte array into a buffered image
	 * @param b
	 * @return
	 * @throws IOException 
	 */
	public BufferedImage imageFromByteArray(byte[] bytes, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		DataBufferByte dbf = new DataBufferByte(bytes, bytes.length);
		Point point = new Point();
		image.setData(Raster.createRaster(image.getSampleModel(), dbf, point ));
		return image;
	}


	/**
	 * read the raw bitmap data from a RandomAccesFile object created from a bmp file
	 * @param raf
	 * @param offset
	 * @param size
	 * @throws IOException
	 */
	public void read(RandomAccessFile raf, int offset, int size) throws IOException {
		this.rawBytes = new byte[size];
		raf.seek(offset); 
		raf.read(this.rawBytes);
	}


	/**
	 * write a raw bitmp data of a bmp image on a RandomAccesFile object
	 * @param raf
	 * @param offset
	 * @throws IOException
	 */
	public void write(RandomAccessFile raf, int offset) throws IOException {
		raf.seek(offset);
		raf.write(this.rawBytes);
	}

}
