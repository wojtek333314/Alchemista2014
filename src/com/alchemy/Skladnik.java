package com.alchemy;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;

public class Skladnik 
{
	MainActivity	act;
	int				w,h;
	
	String			nazwa,
					opis,
					file_path;
	Sprite			ikona;			//ikona przechowywana w folderze(gfx/Items/file_path_0.png) 128x128
	TiledSprite		tiled;			//podzielony na 3 czesci obiekt.(gfx/Items/file_path_1.png) 512x128
	
	boolean			mozdzierz,		//czy moze byc rozdrabniany.
					draging;		//czy mozna przesuwac przy dotknieciu(domyslnie true)
	short			kolor;			//0-nigredo,1 rubedo,2 neutral,3 nitricostam,4 albedo,jesli nie posiada to -1
	int				e_lek,			//efekt leczniczy
					e_tru,			//efekt trujacy
					e_wzm,			//jezeli > 0 to wzmacnia ostatni efekt( efekt trujacy,leczniczy,oslbiajacy nie sa brane pod uwage w tworzeniu elementu!)
					e_osl,			//jezeli > 0 to oslabia ostatni efekt ( efekt trujacy,leczniczy,oslbiajacy nie sa brane pod uwage w tworzeniu elementu!)
					e_mag,			//efekt magiczny (0-lykan,1-wampiryzm,2-szczescie,3-milosc, -1 brak efektu)
					rozdrobnienie,  //aktualny stan rozdrobnienia(jednoczesnie wczytana tekstura dla "tiled")
					kolor_light;	//"œwietlnoœæ" koloru.  przedzial <-1,1> stan podstawowy, <-3 nigredo, >3 albedo wszystko w dokumentacji
	
	private FileRead	filereader;	//zawiera tablice z odczytanymi liniami pliku
	private _Kociol		kociol;
	private _Mozdzierz 	_mozdzierz;
	
	Skladnik(String file_path)
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		this.kociol    = act.kociol;
		this._mozdzierz= act.mozdzierz;
		
		this.file_path = file_path;
		filereader = new FileRead("Items/"+file_path+".itm",9);
		stringi();
		grafika();
		wlasciwosci();
	}
	
	private void stringi()
	{
		nazwa = filereader.F[0];
		opis  = filereader.F[1];
	}
	
	Skladnik getThis()
	{
		return this;
	}
	private void grafika()
	{
		ikona = new Sprite(0,0,new stb("Items/"+file_path+"_0",128,128).T,act.getVertexBufferObjectManager());
		ikona.setScaleCenter(0, 0);
		
		tiled = new TiledSprite(0,0,new stb("Items/"+file_path+"_1",512,128,4,1).tiledT,act.getVertexBufferObjectManager())
		{
			@Override
			public
			boolean onAreaTouched(TouchEvent ev,float x,float y)
			{
	
				if((ev.isActionDown() || ev.isActionMove()) && draging)
				{
					this.setPosition(ev.getX() - this.getWidthScaled()/2, ev.getY() - this.getHeightScaled()/2 );
					if(_mozdzierz.collidesWith(this)) 
						_mozdzierz.mozna_tluc = false;
					else
						_mozdzierz.mozna_tluc = true;
				}
				
				if(ev.isActionUp())
				{
					//opisana elipsa(gora dol) przez kolizje AABB:
					if(ev.getY()+this.getHeightScaled()/2 > ( kociol.getY())+(67/512)*kociol.getHeightScaled() && ev.getY()+this.getHeightScaled()/2 < (kociol.getY()+kociol.getHeightScaled() -(370/512)*kociol.getHeightScaled())
							&& ev.getX()+this.getWidthScaled()/2 > kociol.getX() && ev.getX()+this.getWidthScaled()/2 < kociol.getWidthScaled()+kociol.getX())
					{
						System.out.println("!!");
						//TODO co po dodaniu itema
						kociol.dodaj_skladnik(getThis());
					}
					
					if(this.collidesWith(_mozdzierz))
					{
						_mozdzierz.wrzuc_do_mozdzierza(getThis());
					}
				}
				
				return false;
			}
		};
	}
	
	private void wlasciwosci()
	{
		this.draging = true;
		this.mozdzierz = Boolean.parseBoolean(filereader.F[2]);
		this.kolor = Short.parseShort(filereader.F[3]);
		this.e_lek = Integer.parseInt(filereader.F[4]);
		this.e_tru = Integer.parseInt(filereader.F[5]);
		this.e_wzm = Integer.parseInt(filereader.F[6]);
		this.e_osl = Integer.parseInt(filereader.F[7]);
		this.e_mag = Integer.parseInt(filereader.F[8]);
		
		if(kolor==0) kolor_light = -2;
		if(kolor==1) kolor_light = -1;
		if(kolor==2) kolor_light = 0;
		if(kolor==3) kolor_light = 1;
		if(kolor==4) kolor_light = 2;
	}

	void draging_on()  {draging=true;}
	void draging_off() {draging=false;}
	
	void rozdrobnij(int in)//TODO zmiana wlasciwosci
	{
		rozdrobnienie = in;
		tiled.setCurrentTileIndex(rozdrobnienie);
	}
}
