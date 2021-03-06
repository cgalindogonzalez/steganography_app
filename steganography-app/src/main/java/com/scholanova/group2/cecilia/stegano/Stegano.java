package com.scholanova.group2.cecilia.stegano;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.scholanova.group2.cecilia.bmpfile.BMPFileReader;
import com.scholanova.group2.cecilia.bmpfile.BMPIdentifierEnum;
import com.scholanova.group2.cecilia.filetohide.FileReader;


public class Stegano {

	/**
	 * hide a file in a BMP 24 bits image 
	 * @param bmpFilePath
	 * @param bmpFileName
	 * @param fileToHidePath
	 * @param fileToHideName
	 * @throws IOException
	 */
	public void steganoHide(String bmpFilePath, String bmpFileName, Path bmpFile, String fileToHidePath, String fileToHideName, Path fileToHide) throws IOException {

		//read the bmpFile and divide it into his parts (file header, bmp header and image body)
		BMPFileReader bmpFileReader = new BMPFileReader();
		bmpFileReader.readBMPFile(bmpFile.toFile());

		//check if the image file is already BMP 24 bits
		String bmpIdentifier = bmpFileReader.getFileHeader().decodeType();
		int bitsPerPixel = bmpFileReader.getBmpHeader().decodeColorDepth();

		if (BMPIdentifierEnum.getEnums().contains(bmpIdentifier) && (bitsPerPixel == 24)){
			//reckon the maximun size of the file to hide
			int width = bmpFileReader.getBmpHeader().decodeImageWidth(); 
			int height = bmpFileReader.getBmpHeader().decodeImageHeight();

			int numberOfPixels = width*height;
			int maxSize = 3*numberOfPixels/4; // number of bytes that can be hidden (one byte is divided into four pair of bits). 

			//read the file to hide
			FileReader fileReader = new FileReader();
			File file = fileToHide.toFile();

			byte[] fileInformation = fileReader.getFileInformationToReconstruction(file, fileToHide); //get the byte array with the information of the file to hide 
			byte[] readFileArray = fileReader.readFile(file); //get the byte array with the bytes of the file to hide (by reading the file)
			byte[] fileArray = fileReader.concatenateInformationReadArrays(fileInformation, readFileArray); //concatenate both arrays 
			byte[] pairOfBitsArray = fileReader.getPairOfBitsFromByteArray(fileArray);

			int fileArraySize = fileArray.length;
			//check by his size if the file can be hidden in the image  
			if (fileArraySize > maxSize) 
				System.out.println("The file is too large to be hiden in this image");

			else {
				//hide the file within the pixels of the image
				byte[] rawBytesImage = bmpFileReader.getBody().getRawBytes();
				BufferedImage bImage = bmpFileReader.getBody().imageFromByteArray(rawBytesImage, width, height);
				BufferedImage bImageWithFile = hideFileWithinPixelsOfImage (bImage, pairOfBitsArray);
				byte[] rawBytesImageWithFile = bmpFileReader.getBody().byteArrayFromImage(bImageWithFile);
				bmpFileReader.getBody().setRawBytes(rawBytesImageWithFile);

				//rebuild and save the new image (with the file hidden)
				String newBMPFileName = "new" + bmpFileName;
				Path newImagePath = FileSystems.getDefault().getPath(bmpFilePath, newBMPFileName);

				bmpFileReader.writeBMPFile(newImagePath.toFile()); 

				System.out.println("You can find the image in: " + newImagePath.toString());
			}
		} 

		else {
			System.out.println("The image is not a BMP 24 bits file");
		}
	}


	/**
	 * recover a hidden file from a BMP 24 bits image
	 * @param bmpFilePath
	 * @param bmpFileName
	 * @throws IOException
	 */
	public void steganoRecover (String bmpFilePath, Path bmpFile) throws IOException {

		BMPFileReader bmpFileReader = new BMPFileReader();
		bmpFileReader.readBMPFile(bmpFile.toFile());


		String bmpIdentifier = bmpFileReader.getFileHeader().decodeType();
		int bitsPerPixel = bmpFileReader.getBmpHeader().decodeColorDepth();

		//check if the image file is already BMP 24 bits
		if (BMPIdentifierEnum.getEnums().contains(bmpIdentifier) && (bitsPerPixel == 24)){
			//get the raw bytes, the width and the height of the image 
			byte[] rawImage = bmpFileReader.getBody().getRawBytes();
			int width = bmpFileReader.getBmpHeader().decodeImageWidth(); 
			int height = bmpFileReader.getBmpHeader().decodeImageHeight();

			//recover the array with the LSB of the pixels of the buffered image (built with the raw bytes)
			BufferedImage bImage = bmpFileReader.getBody().imageFromByteArray(rawImage, width, height);
			byte[] pairOfBitsToRecoverTheFile = recoverHiddenBytesFromTheImage(bImage);

			FileReader fileReader = new FileReader();
			byte[] arrayToRecoverTheFile = fileReader.getByteArrayFromPairOfBits(pairOfBitsToRecoverTheFile);//each byte of this array is made up of 4 pairs of bits of the former array

			//get the size of the hidden file
			long fileLehgth = fileReader.getFileSizeFromRecoveredArray(arrayToRecoverTheFile);

			//get the extension of the hidden file
			String fileExtension = fileReader.getFileExtensionFromRecoveredArray(arrayToRecoverTheFile);

			//get a byte array from the recovered array whose length is the file size (after removing the file information located on the first bytes)
			byte[] fileArray = fileReader.getFileArrayFromRecoveredArray(arrayToRecoverTheFile, fileLehgth);

			//save the file
			fileReader.saveFile(fileArray, bmpFilePath, fileExtension);

			String fileName = "recovered_file." + fileExtension;
			Path recoveredFile = FileSystems.getDefault().getPath(bmpFilePath, fileName);

			System.out.println("You can find the file in: " + recoveredFile);

		}

		else {
			System.out.println("The image is not a BMP 24 bits file");
		}
	}

	/**
	 * hide a file in the pixels of a buffered image
	 * @param byteArray
	 * @return
	 */
	public BufferedImage hideFileWithinPixelsOfImage (BufferedImage image, byte[] byteArray) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		int i = 0;
		for (int x = 0; x < imageHeight; x++) {
			for (int y = 0; y < imageWidth; y++) {
				int rgb = image.getRGB(y, x);
				Color color = new Color(rgb, true);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int redRounded = (red-(red & 3));
				int greenRounded = (green-(green & 3));
				int blueRounded = (blue-(blue & 3));

				if ((i+2) < byteArray.length) {
					red = redRounded + byteArray[i];
					green = greenRounded + byteArray[i+1];
					blue = blueRounded + byteArray[i+2];
					i+=3;
				}
				else if ((i+1) < byteArray.length) {
					red = redRounded + byteArray[i];
					green = greenRounded + byteArray[i+1];
				}
				else if (i < byteArray.length) {
					red = redRounded + byteArray[i];
				}

				Color newColor = new Color(red, green, blue);
				image.setRGB(y, x, newColor.getRGB());
			}
		}
		return image;
	}

	/**
	 * recover a byte array with the pair of less significant bits from the three bytes of each pixel of the image 
	 * @param bi
	 * @return
	 */
	public byte[] recoverHiddenBytesFromTheImage (BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		byte[] LSBarray = new byte[3*width*height];
		int i = 0;
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				int rgb = bi.getRGB(y, x);
				Color color = new Color(rgb, true);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int redLSB = red & 3;
				int greenLSB = green & 3;
				int blueLSB = blue & 3;

				LSBarray[i] = (byte) redLSB;
				LSBarray[i+1] = (byte) greenLSB;
				LSBarray[i+2] = (byte) blueLSB;
				i+=3;
			}
		}

		return LSBarray;

	}

	/**
	 * get the extension of a file from his path
	 * @param path
	 * @return
	 */
	public static String getFileExtension (Path path) {
		String name = path.toString();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}
}
