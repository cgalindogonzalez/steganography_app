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
	 * 
	 * @param bmpFilePath
	 * @param bmpFileName
	 * @param fileToHidePath
	 * @param fileToHideName
	 * @throws IOException
	 */
	public void steganoHide(String bmpFilePath, String bmpFileName, String fileToHidePath, String fileToHideName) throws IOException {
		
		Path bmpFile = FileSystems.getDefault().getPath(bmpFilePath, bmpFileName);
		Path fileToHide = FileSystems.getDefault().getPath(fileToHidePath, fileToHideName);
		
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

			FileReader fileReader = new FileReader();
			File file = fileToHide.toFile();
			
			byte[] fileInformation = fileReader.getFileInformationToReconstruction(file, fileToHide); //get the byte array with the information of the file to hide 
			byte[] readFileArray = fileReader.readFile(file); //get the byte array with the bytes of the file to hide
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
	 * 
	 * @param bmpFilePath
	 * @param bmpFileName
	 * @throws IOException
	 */
	public void steganoRecover (String bmpFilePath, String bmpFileName) throws IOException {
		
		Path bmpFile = FileSystems.getDefault().getPath(bmpFilePath, bmpFileName);
	
		BMPFileReader bmpFileReader = new BMPFileReader();
		bmpFileReader.readBMPFile(bmpFile.toFile());
		
		//check if the image file is already BMP 24 bits
		String bmpIdentifier = bmpFileReader.getFileHeader().decodeType();

		int bitsPerPixel = bmpFileReader.getBmpHeader().decodeColorDepth();

		if (BMPIdentifierEnum.getEnums().contains(bmpIdentifier) && (bitsPerPixel == 24)){
			
			byte[] rawImage = bmpFileReader.getBody().getRawBytes();
			int width = bmpFileReader.getBmpHeader().decodeImageWidth(); 
			int height = bmpFileReader.getBmpHeader().decodeImageHeight();
			
			BufferedImage bImage = bmpFileReader.getBody().imageFromByteArray(rawImage, width, height);
			byte[] pairOfBitsToRecoverTheFile = recoverHiddenBytesFromTheImage(bImage);
			
			FileReader fileReader = new FileReader();
			byte[] arrayToRecoverTheFile = fileReader.getByteArrayFromPairOfBits(pairOfBitsToRecoverTheFile);
			
			//get the size of the hidden file
			long fileLehgth = fileReader.getFileSizeFromRecoveredArray(arrayToRecoverTheFile);
			
			//get a byte array from the recovered array whose length is the file size (after removing the file information located on the first bytes)
			byte[] fileArray = fileReader.getFileArrayFromRecoveredArray(arrayToRecoverTheFile, fileLehgth);
			
			//get the extension of the hidden file
			String fileExtension = fileReader.getFileExtensionFromRecoveredArray(arrayToRecoverTheFile);
			
			//save the file
			fileReader.saveFile(fileArray, bmpFilePath, fileExtension);
			
			String fileName = "recovered_file." + fileExtension;
			Path recoveredFile = FileSystems.getDefault().getPath(bmpFilePath, fileName);
		
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
		
		BufferedImage newImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);

		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				int rgb = image.getRGB(x, y);
				Color color = new Color(rgb, true);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int redRounded = (red-red%4);
				int greenRounded = (green-green%4);
				int blueRounded = (blue-blue%4);

				int i = (y + x*imageWidth);
				if (byteArray.length < i) {
					red = redRounded + byteArray[i];
					green = greenRounded + byteArray[i+1];
					blue = blueRounded + byteArray[i+2];
					i+=3;
				}
				
				Color newColor = new Color(red, green, blue);
				newImage.setRGB(x, y, newColor.getRGB());
			}
		}
		return newImage;
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
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bi.getRGB(x, y);
				Color color = new Color(rgb, true);
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();

				int redLSB = red%4;
				int greenLSB = green%4;
				int blueLSB = blue%4;

				int i = 3*(y + x*width);
				LSBarray[i] = (byte) redLSB;
				LSBarray[i+1] = (byte) greenLSB;
				LSBarray[i+2] = (byte) blueLSB;
				
			}
		}
		
		return LSBarray;
		
	}
}
