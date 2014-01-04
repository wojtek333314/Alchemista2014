package com.alchemy;
import java.util.Random;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class _Kociol extends ButtonSprite
{
	private int[]		moves;//tablica ktora na podstawie  ktorych okreslany jest ruch move_ID
	/**
	 * przechowuje ruch ktory wykryla metoda move_detector()
	 * @ 0 - ruch bledny, niekompletny,zwykle kliknicie. ogolnie: BLAD
	 * @ 1 - ruch w lewo
	 * @ 2 - ruch w prawo
	 */
	public  int			move_ID;
	private int 		last_move;				//TODO mozna wyjebac ,tylko do testow zeby mi konsoli nie zasmiecalo
	boolean				moze_dodac_skladnik;	//czy mozna dodac skladnik
	Sprite				plyn;
	Element				baza_alchemiczna;
	Random 				r;
	private Scene		parent;
	
	public _Kociol(Scene s,int typ_bazy_alchemicznej,float pX, float pY, ITextureRegion tex,VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, tex, pVertexBufferObjectManager);
		
		r 					= new Random();
		moves 				= new int[4];
		baza_alchemiczna 	= new Element(typ_bazy_alchemicznej);
		
		parent = s;
		setScaleCenter(0, 0);
		setScale(MainActivity.getSharedInstance().h/2f/getHeight());
		setPosition(MainActivity.getSharedInstance().w/2 - this.getWidthScaled()/2,MainActivity.getSharedInstance().h/2 - this.getHeightScaled()/2);
		plyn();
	}
	
	void plyn()
	{
		plyn = new Sprite(0,0,new stb("Alchemy/plyn",512,512).T,MainActivity.getSharedInstance().getVertexBufferObjectManager());
		plyn.setColor(baza_alchemiczna.color);
		plyn.setScaleCenter(0, 0);
		plyn.setScale(this.getScaleX());
		plyn.setPosition(this.getX(), this.getY());
	}
	
	void touchDisable()
	{
		parent.unregisterTouchArea(this);
	}
	
	void touchEnable()
	{
		parent.registerTouchArea(this);
	}
	
	void attachPlyn(Scene s)
	{
		s.attachChild(plyn);
	}
	
	void dodaj_skladnik(Skladnik skladnik)
	{
		baza_alchemiczna.dodaj_skladnik(skladnik,plyn);
	}
	
	public boolean onAreaTouched(TouchEvent ev,float x,float y)
	{
		if(ev.isActionUp()) reset_array();
		if(ev.isActionDown() || ev.isActionMove())
		{
		 //  dodaj_skladnik(new Skladnik("bez",this));
		   if(x <= this.getWidthScaled()/2 && y  <= this.getHeightScaled()/2)  save_array(1);
		   if(x > this.getWidthScaled() /2 && y  <= this.getHeightScaled()/2)  save_array(2);
		   if(x <= this.getWidthScaled()/2 && y  > this.getHeightScaled()/2)   save_array(3);
		   if(x > this.getWidthScaled() /2 && y  > this.getHeightScaled()/2)   save_array(4);
		   
		   gesture_detector();
		}
		return false;
	}
	
	void save_array(int tosave)
	{
		if(tosave == moves[0]) return;
		for(int i=moves.length-1;i>0;i--)
			moves[i] = moves[i-1];
		moves[0] = tosave;	
	}
	
	void gesture_detector()
	{
		/* w prawo */
		last_move = move_ID;
		move_ID = 0;
		
		if(moves[0]==1 && moves[1]==2 && moves[2]==4 && moves[3]==3) move_ID = 1;
		if(moves[0]==2 && moves[1]==4 && moves[2]==3 && moves[3]==1) move_ID = 1;
		if(moves[0]==4 && moves[1]==3 && moves[2]==1 && moves[3]==2) move_ID = 1;
		if(moves[0]==3 && moves[1]==1 && moves[2]==2 && moves[3]==4) move_ID = 1;
		
		/* w lewo */
		if(moves[0]==1 && moves[1]==3 && moves[2]==4 && moves[3]==2) move_ID = 2;
		if(moves[0]==3 && moves[1]==4 && moves[2]==2 && moves[3]==1) move_ID = 2;
		if(moves[0]==4 && moves[1]==2 && moves[2]==1 && moves[3]==3) move_ID = 2;
		if(moves[0]==2 && moves[1]==1 && moves[2]==3 && moves[3]==4) move_ID = 2;
		
		if(move_ID!=last_move)
			System.out.println(move_ID);
	}
	
	void reset_array()
	{
		for(int i=0;i<moves.length;i++) 
			moves[i] = -1;
	}
	
	
	
}
