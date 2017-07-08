package com.scholanova.group2.cecilia;

import java.io.IOException;
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
				
				System.out.println("Insert the file path of the image.");
				String bmpFilePath = sc.nextLine();
				System.out.println("Insert the file name (with the extension) of the image.");
				String bmpFileName = sc.nextLine();
				System.out.println("Insert the file path of the file to hide");
				String fileToHidePath = sc.nextLine();
				System.out.println("Insert the file name (with the extension) of the file to hide");
				String fileToHideName = sc.nextLine();
//				String bmpFilePath = "/Users/Cecilia/Documents/AgilityFactory/CursoInformatica";
//				String bmpFileName = "24bpp.bmp";
//				String fileToHidePath = "/Users/Cecilia/Documents/AgilityFactory/CursoInformatica";
//				String fileToHideName = "operation.txt";
						
				Stegano stegano = new Stegano();
				stegano.steganoHide(bmpFilePath, bmpFileName, fileToHidePath, fileToHideName);
			}

			//Selection 2. recover a file from an image
			else if (choice.equals("2")) {
				
//				System.out.println("***********************************");
//				System.out.println("2. RECOVER A FILE FROM A BMP IMAGE");
//				System.out.println("***********************************");
//				
//				System.out.println("Insert the file path of the image");
//				String bmpFilePath = sc.nextLine();
//				System.out.println("Insert the file name of the image");
//				String bmpFileName = sc.nextLine();
				
				String bmpFilePath = "/Users/Cecilia/Documents/AgilityFactory/CursoInformatica";
				String bmpFileName = "new24bpp.bmp";
				Stegano stegano = new Stegano();
				stegano.steganoRecover(bmpFilePath, bmpFileName);
				
			}
			
			
			//Selection is not 1 or 2
			else{
				System.out.println("Wrong selection. The options are 1 or 2");
			}


		} while (!choice.equals("1") && !choice.equals("2")); // to restart if the selection is not 1 or 2
		sc.close();
	}

	}


