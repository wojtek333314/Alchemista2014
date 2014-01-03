package com.alchemy;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;

public class Skladnik 
{
	MainActivity	act;
	int				w,h;
	
	String			tytul,
					opis,
					file_path;
	Sprite			ikona;
	TiledSprite		tiled;			//podzielony na 3 czesci obiekt. 
	
	boolean			mozdzierz;		//czy moze byc rozdrabniany.
	short			kolor;			//0-nigredo,1 rubedo,2 neutral,3 nitricostam,4 albedo,jesli nie posiada to -1
	int				e_lek,			//efekt leczniczy
					e_tru,			//efekt trujacy
					e_wzm,			//jezeli > 0 to wzmacnia ostatni efekt( efekt trujacy,leczniczy,oslbiajacy nie sa brane pod uwage w tworzeniu elementu!)
					e_osl,			//jezeli > 0 to oslabia ostatni efekt ( efekt trujacy,leczniczy,oslbiajacy nie sa brane pod uwage w tworzeniu elementu!)
					e_mag,			//efekt magiczny (0-lykan,1-wampiryzm,2-szczescie,3-milosc, -1 brak efektu)
					rozdrobnienie;  //aktualny stan rozdrobnienia(jednoczesnie wczytana tekstura dla "tiled")
	
	private FileRead	filereader;	//zawiera tablice z odczytanymi liniami pliku
	Skladnik(String file_path)
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		this.file_path = file_path;
		filereader = new FileRead(file_path,9);
		stringi();
		grafika();
		wlasciwosci();
	}
	
	void stringi()
	{
		
	}
	
	void grafika()
	{
		
	}
	
	void wlasciwosci()
	{
		
	}
}
