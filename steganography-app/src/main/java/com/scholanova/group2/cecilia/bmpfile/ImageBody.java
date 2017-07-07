package com.scholanova.group2.cecilia.bmpfile;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;

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
		
		byte [] imageInByte = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		ImageIO.write(bi, "bmp", baos);
		imageInByte = baos.toByteArray();
		baos.close();
		
		return imageInByte;

	}

	/**
	 * convert a byte array into a buffered image
	 * @param b
	 * @return
	 * @throws IOException 
	 */
//	public BufferedImage imageFromByteArray(byte[] b) throws IOException {
//		BufferedImage image = createRGBImage(bytes, width, height);
//
//		try {
//		    ImageIO.write(image, "BMP", stream);
//		}
//		finally {
//		    stream.close();
//		}
//		
//		return ImageIO.read(is);
//	}

	public BufferedImage imageFromByteArray(byte[] bytes, int width, int height) {
	    DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
	    ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
	    return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
	}

	
	
	

	public void read(RandomAccessFile raf, int offset, int size) throws IOException {
		this.rawBytes = new byte[size];
		raf.seek(offset);
		raf.read(this.rawBytes);
	}


	public void write(RandomAccessFile raf, int offset) throws IOException {
		if (this.rawBytes == null) 
			throw new IllegalStateException("no puedes hacer write si antes no has inicializado rawBytes");
		raf.seek(offset);
		raf.write(this.rawBytes);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
