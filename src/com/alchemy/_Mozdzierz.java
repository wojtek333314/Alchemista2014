package com.alchemy;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class _Mozdzierz extends Sprite
{
	MainActivity	act;
	int				w,h;
	
	int				lvl,					//poziom tluczka. im wiekszy tym szybciej rozdrabnia
					rozdrobnienie_stopien;	//zmienna ktora zwieksza sie przy rozdrabnianiu jak >= 100 to item koniec rozdrobnienia
	boolean			mozna_tluc=false;		//czy mozna tluc(czy przechowuje Skladnik)
	Sprite			toend,					//pasek pokazujacy ile trzeba jeszcze stukac
					slot;
	Skladnik		rozdrabniany;
	
	public _Mozdzierz(int lvl,float x, float y,ITextureRegion tex,VertexBufferObjectManager vex) 
	{
		super(x, y, tex, vex);
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		
		this.lvl = lvl;
		this.setScaleCenter(0, 0);
		this.setScale(w/6/getWidth());
		
		grafika();
	}
	
	void grafika()
	{
		toend = new Sprite(0,0,new stb("WSPOLNE/redPIX",4,4).T,act.getVertexBufferObjectManager());
		toend.setSize(0, this.getHeightScaled()*0.1f);
		toend.setPosition(getX(), getY()+getHeightScaled());
		
		slot = new Sprite(0,0,new stb("Alchemy/slot_mozdzierz",64,64).T,act.getVertexBufferObjectManager());
		slot.setScaleCenter(0, 0);
		slot.setScale(this.getWidthScaled()/2/slot.getWidth());
		slot.setPosition(getX() - slot.getWidthScaled(), getY()+getHeightScaled()/2 - slot.getHeightScaled()/2);
	}

	void attach(Scene s)
	{
		s.attachChild(this);
		s.attachChild(slot);
		s.attachChild(toend);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent ev,float x,float y)
	{	
		if(rozdrabniany==null) return false;
		if(rozdrabniany.tiled.getCurrentTileIndex()>1) return false;
		if(mozna_tluc)
		{
		rozdrobnienie_stopien+= 1*lvl;
		if(rozdrobnienie_stopien>=100) {rozdrobniono();return false;}
		update();
		}
		return false;
	}
	
	@Override 
	public void setPosition(float x,float y)
	{
		super.setPosition(x, y);
		toend.setPosition(x, y+this.getHeightScaled());
		slot.setPosition(getX() - slot.getWidthScaled(), getY()+getHeightScaled()/2 - slot.getHeightScaled()/2);
	}
	void rozdrobniono()
	{
		if(mozna_tluc == false)return;
		rozdrobnienie_stopien = 0;
		rozdrabniany.tiled.setCurrentTileIndex(rozdrabniany.tiled.getCurrentTileIndex()+1);
	}
	
	void update()
	{
		toend.setSize((rozdrobnienie_stopien*this.getWidthScaled())/100,this.getHeightScaled()*0.1f);
	}
	
	void wrzuc_do_mozdzierza(Skladnik skladnik)
	{	
		System.out.println("wrzucono");
		mozna_tluc = true;
		rozdrobnienie_stopien = 0 ;
		rozdrabniany = skladnik;
		System.out.println("Rozdrabniam");
	}
	
}
