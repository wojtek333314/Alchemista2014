package com.alchemy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileRead {
	private MainActivity 	act;
	private InputStream 	in;
	private BufferedReader 	reader;
	public  String 			F[];
	public  int 			rozmiar_tablicy;



/**
 * Odczytuje dane z pliku o podanej nazwie do tablicy o podanej rozmairze. Odczyt wierszami	
 * @param v	Nazwa pliku+rozszerzenie np Cos.txt
 * @param rozmiar	Rozmiar liczba calkowita. dla tabluicy zadanie[rozmiar];
 */
FileRead(String v,int rozmiar)
{
	act = MainActivity.getSharedInstance();
	rozmiar_tablicy = rozmiar;
	F = new String[rozmiar_tablicy];
	
	try {
		in = act.getAssets().open(v);
	} catch (IOException e) {
		e.printStackTrace();
	}
   reader = new BufferedReader(new InputStreamReader(in));
   
  	 for (int i=0;i < rozmiar_tablicy ;i++)
	 {
		 try {
			F[i] = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		
	 }
}
}
