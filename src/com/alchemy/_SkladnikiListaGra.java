package com.alchemy;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

public class _SkladnikiListaGra
{
	MainActivity	act;
	int				w,h;
	int				aktywny_index;
	Skladnik[] 		LIST;
	Sprite			ramka;
	String[]		Skladnik_File;
	
	_SkladnikiListaGra()
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		lista();
		ramka();
	}
	
	void lista()
	{
		Skladnik_File = new String[]{"bez","bez","bez","bez","bez"};
		LIST = new Skladnik[5];
		for(int i=0;i<LIST.length;i++)
		{
			LIST[i] = new Skladnik(Skladnik_File[i]);
		}
	}
	
	void ramka()
	{
		ramka = new Sprite(0,0,new stb("Alchemy/ramka_list",64,64).T,act.getVertexBufferObjectManager());
		ramka.setSize(w/2, h/6);
	}
	
	void setPosition(float x,float y)
	{
		ramka.setPosition(x, y);
		for(int i=0;i<LIST.length;i++)
		{
			LIST[i].tiled.setPosition(ramka.getX()+ i*LIST[i].tiled.getWidthScaled(), ramka.getY());
			LIST[i].setStartPosition(ramka.getX()+ i*LIST[i].tiled.getWidthScaled(), ramka.getY());
		}
	}
	
	float getWidthScaled()
	{
		return ramka.getWidthScaled();
	}
	
	float getHeightScaled()
	{
		return ramka.getHeightScaled();
	}
	void attach(Scene s)
	{
		s.attachChild(ramka);
		for(int i=0;i<LIST.length;i++)
		{
			LIST[i].tiled.setScaleCenter(0, 0);
			LIST[i].tiled.setScale(ramka.getHeightScaled()/LIST[i].tiled.getHeight());
			LIST[i].tiled.setPosition(ramka.getX()+ i*LIST[i].tiled.getWidthScaled(), ramka.getY());
			LIST[i].setStartPosition(ramka.getX()+ i*LIST[i].tiled.getWidthScaled(), ramka.getY());
			s.attachChild(LIST[i].tiled);
			s.registerTouchArea(LIST[i].tiled);
		}
	}
}
