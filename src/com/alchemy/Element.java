package com.alchemy;

import java.util.Random;
import java.util.Vector;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;


public class Element//sluzy do wazenia mikstur,na jego podstawie mozna stworzyc item
{
	MainActivity	act;
	int				w,h;
	/** 0-esencja,1-mikstura,2-wywar,3-olej**/
	int 			typ,
					stan_koloru			= 2,  	// 2 - stan podstawowy, 1 - rubedo,0 nigredo, 3 - citrinitas,4- albedo	. Dla okreslenia ktory kolor brac z tablicy dla danego elementu w Kontenerze
					e_tru   		    = 0,
					e_lek	 	        = 0,
					e_mag  		= 0,
					poprawnosc_dzialan 	= 0,
					efkt_ostatni 		= -1; 	//0 leczniczy,1 trujacy
	
	float 	wartosc_koloru			= 0;    	//wartosc przedzialowa. <-1,1> stan podstawowy
	float	szybkosc_reakcji 		= 3,    	//podstawowo zajmuje 3 sekund przejscie na dany kolor mikstury
			temperatura_optymalna 	= 0.3f, 	//dolna "belka". <0,1>, mnozone razy h slupka i odjete od wysokosci+pozycjiY daje wlasnie pozycje dolnej belki
			temperatura_zakres		= 0.3f; 	//zakres temperatury w jakiej ma utrzymywac, mnozony razy h slupka 
	
	boolean							moze_dodac_skladnik     = true;
	Vector <Skladnik> 				ingredient;				//wrzucone juz skladniki 
	Color							color;
	ITextureRegion					fiolka;					//ksztalt jakis fiolki okreslony przez typ
	Random							r;
	

	
	Element(int typ)
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		this.typ 	= typ;
		r       	= new Random();
		ingredient	= new Vector<Skladnik>();
		
		if(typ==0) {color = act.kon.esencja_color[this.stan_koloru];	temperatura_optymalna=0.1f;}
		if(typ==1) {color = act.kon.mikstura_color[this.stan_koloru];	temperatura_optymalna=0.3f;}
		if(typ==2) {color = act.kon.wywar_color[this.stan_koloru];		temperatura_optymalna=0.5f;}
		if(typ==3) {color = act.kon.olej_color[this.stan_koloru];		temperatura_optymalna=0.7f;}
	}
	
	
	void dodaj_skladnik(Skladnik skladnik, Sprite plyn)//TODO usun skladnik z eq
	{
		if(moze_dodac_skladnik == false) return;
		
		ingredient.add(skladnik);
		skladnik.tiled.setCurrentTileIndex(0);
		skladnik.tiledSetScaleOnList();
		moze_dodac_skladnik = false;
		//wzmocnienie lub oslabienie:
	  	if(efkt_ostatni != -1 && (skladnik.e_wzm > 0 || skladnik.e_osl > 0)) 
		{
	  		wzmocnij_lub_oslab_ostatni_efekt(skladnik.e_wzm,skladnik.e_osl);
		}
		else
		//dodanie efektu:
		{
	  	this.e_lek += skladnik.e_lek;
	  	this.e_mag  =  skladnik.e_mag;
	  	this.e_tru   += skladnik.e_tru;
	  
	  	if(skladnik.e_lek != 0)   efkt_ostatni = 0;
	  	if(skladnik.e_tru != 0)   efkt_ostatni = 1;
		}
	  	//zmiana temperatury:
	  		if(skladnik.rozdrobnienie==0)szybkosc_reakcji*=1.2f;
	  		if(skladnik.rozdrobnienie==1)szybkosc_reakcji*=1.1f;
	  		if(skladnik.rozdrobnienie==2)szybkosc_reakcji*=0.85f;
	  		
	  	//zmiana koloru:
	  	wartosc_koloru+=skladnik.kolor_light;				//dodawanie stanow nigredo,albedo itd.
	  	if(wartosc_koloru <= -3) 							stan_koloru = 0;
	  	if(wartosc_koloru >  -3    && wartosc_koloru < -1) 	stan_koloru = 1;
	  	if(wartosc_koloru >=  -1   && wartosc_koloru < 1 ) 	stan_koloru = 2;
	  	if(wartosc_koloru >=   1   && wartosc_koloru < 3 ) 	stan_koloru = 3;
	  	if(wartosc_koloru >=  3)							stan_koloru = 4;
	  	
		if(typ==0) color = act.kon.esencja_color[this.stan_koloru];
		if(typ==1) color = act.kon.mikstura_color[this.stan_koloru];
		if(typ==2) color = act.kon.wywar_color[this.stan_koloru];
		if(typ==3) color = act.kon.olej_color[this.stan_koloru];
		
		System.out.println("dodano skladnik");
		plyn.registerEntityModifier(new ColorModifier(szybkosc_reakcji,plyn.getColor(),color)
		{
			   @Override
			    protected void onModifierFinished(IEntity pItem)
			    {
			            super.onModifierFinished(pItem);
			            odblokuj_dodanie_skladnikow();
			    }
		});
	}
	
	private void odblokuj_dodanie_skladnikow()
	{
		moze_dodac_skladnik = true;
	}
	
	void wzmocnij_lub_oslab_ostatni_efekt(int wzmocnienie,int oslabienie)
	{
		if(this.efkt_ostatni == 0 && wzmocnienie > 0)  		e_lek+=wzmocnienie;
		if(this.efkt_ostatni == 0 && oslabienie  > 0) 		e_lek-=oslabienie;
		if(this.efkt_ostatni == 1 && wzmocnienie > 0) 		e_tru+=wzmocnienie;
		if(this.efkt_ostatni == 1 && oslabienie  > 1)		e_tru-=oslabienie;
	}
	
	
	
}
