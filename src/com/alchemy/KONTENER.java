package com.alchemy;

import org.andengine.util.color.Color;

public class KONTENER 
{
	MainActivity	act;
	int				w,h;
	
	Color[] esencja_color;
	Color[] mikstura_color;
	Color[] wywar_color;
	Color[] olej_color;
	Color	zielony,pomaranczowy,czerwony,bialy,zolty,czarny,niebieski,blekitny,bordowy,fioletowy;

	KONTENER()
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		kolory();
		
	}
	
	
	void kolory()
	{
		zielony = new Color(73/255f,243/255f,24/255f);
		pomaranczowy = new Color(250/255f,134/255f,18/255f);
		czerwony = new Color(1,0,0);
		bialy = new Color(1,1,1);
		zolty = new Color(1,1,0);
		czarny = new Color(0,0,0);
		niebieski = new Color(0,0,1);
		blekitny = new Color(77/255f,242/255f,251/255f);
		bordowy = new Color(162/255f,17/255f,17/255f);
		fioletowy = new Color(200/255f,26/255f,215/255f);
		
		esencja_color = new Color[]{pomaranczowy,zielony	,niebieski		,czerwony	,bordowy};
		mikstura_color= new Color[]{fioletowy	,niebieski	,bialy		,zolty		,czarny};
		wywar_color= new Color[]   {niebieski	,blekitny	,czerwony	,zolty		,pomaranczowy};
		olej_color= new Color[]    {czarny   	,fioletowy	,zolty	,zielony	,zolty};
	}
}
