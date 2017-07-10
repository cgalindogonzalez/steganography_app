package com.scholanova.group2.cecilia;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Scanner;

import com.scholanova.group2.cecilia.stegano.Stegano;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		String choice = null;
		do {
			System.out.println("Please, select one option:");
			System.out.println("1. Hide a file in a BMP image.");
			System.out.println("2. Recover a file from a BMP image");

			choice = sc.nextLine();

			//Selection 1: Hide file into image
			if (choice.equals("1")) {

				System.out.println("******************************");
				System.out.println("1. HIDE A FILE IN A BMP IMAGE");
				System.out.println("******************************");

				System.out.println("Insert the file path of the directory where the image is.");
				String bmpFilePath = sc.nextLine();
				System.out.println("Insert the file name (with the extension) of the image.");
				String bmpFileName = sc.nextLine();

				Path bmpFile = FileSystems.getDefault().getPath(bmpFilePath, bmpFileName);
				if (!Files.exists(bmpFile, LinkOption.NOFOLLOW_LINKS)) 
					System.out.println("This file does not exists on this path or the path is not correct");
				else{
					System.out.println("Insert the file path of the directory where the file to hide is.");
					String fileToHidePath = sc.nextLine();
					System.out.println("Insert the file name (with the extension) of the file to hide.");
					String fileToHideName = sc.nextLine();

					Path fileToHide = FileSystems.getDefault().getPath(fileToHidePath, fileToHideName);
					if (!Files.exists(fileToHide, LinkOption.NOFOLLOW_LINKS)) 
						System.out.println("This file does not exists on this path or the path is not correct");
					else{
						Stegano stegano = new Stegano();
						stegano.steganoHide(bmpFilePath, bmpFileName, bmpFile, fileToHidePath, fileToHideName, fileToHide);
					}
				}
			}

			//Selection 2. recover a file from an image
			else if (choice.equals("2")) {

				System.out.println("***********************************");
				System.out.println("2. RECOVER A FILE FROM A BMP IMAGE");
				System.out.println("***********************************");

				System.out.println("Insert the file path of the directory where the image is.");
				String bmpFilePath = sc.nextLine();
				System.out.println("Insert the file name (with the extension) of the image.");
				String bmpFileName = sc.nextLine();

				Path bmpFile = FileSystems.getDefault().getPath(bmpFilePath, bmpFileName);
				if (!Files.exists(bmpFile, LinkOption.NOFOLLOW_LINKS)) 
					System.out.println("This file does not exists on this path or the path is not correct");
				else{
					Stegano stegano = new Stegano();
					stegano.steganoRecover(bmpFilePath, bmpFile);
				}
			}

			//Selection is not 1 or 2
			else{
				System.out.println("Wrong selection. The options are 1 or 2");
			}

		} while (!choice.equals("1") && !choice.equals("2")); // to restart if the selection is not 1 or 2
		sc.close();
	}

}


