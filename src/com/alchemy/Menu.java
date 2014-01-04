package com.alchemy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

/**
 * SKRÓTY PODSCEN:
 * 		
 * 	1	MEN - pocz¹tkowa
 * 		PAS - górny pasek widoczny w podscenach SHO, MAG, PRG,
 * 	2	ENC - encyklopedia															
 * 	3	PRG - przed gr¹																 
 * 	4	MAG - magazyn																MEN -- ENC
 * 	5	SH0 - sklep																	 |
 * 																	  SHO -- MAG -- PRG
 * 		te numerki to numer podsceny
 */

public class Menu extends Scene
{
	MainActivity act;
	
	int 			w,
					h;
	
	boolean 		przesuwanie = false, // je¿eli true to znaczy, ¿e scena jest aktualnie przemieszczana
					s_music_MEN_clicked = true,	// do zmieniania koloru spirteu music 0 czerwony 1 zielony
					s_sound_MEN_clicked	= true,	// do zmieniania koloru spriteu sound
					shad_click = false;	// od tego czy papier jest na glownej scenie czy nie
	
	boolean[]		shad_PRG_clicked; // od niej zaleza kolory spriteu
	
	TextureRegion[]	t_background,	
					t_tab_MEN;
	
	TextureRegion   t_music_MEN,
					t_sound_MEN,
					
					t_shop_PRG,
					t_free_PRG,
					t_play_PRG,
					t_list_PRG,
					t_sha0_PRG,
					t_sha1_PRG,
					t_pape_PRG;
	
	Sprite[]		s_background,	// t³o , 3 komórki
					s_tab_MEN,		// tabliczki do przechodzenia, 3 komórki
					
					s_shad_PRG		// tlo pod nazwy zlecen
					;
	
	Sprite			s_music_MEN,	// ikonka nuty
					s_sound_MEN,	// ikonka glosnika
					
					s_shop_PRG,		// przycisk przekierowujacy do sklepu
					s_free_PRG,		// przycisk odpalajacy zwykla gre
					s_play_PRG,		// przycisk odpalajacy zlecenie
					s_list_PRG,		// tlo na srodku
					s_pape_PRG		// papier pod tekst misji
					;
	
	Text[]			m_name_PRG;		// nazwa misji

	Text			m_contents_PRG;	// tekst misji 
	
	Menu()
	{
		act = MainActivity.getSharedInstance();
		w 	= act.w;
		h	= act.h;
		
		resources();
		background();
		
		MEN();
		ENC();
		PRG();
		MAG();
		SHO();
		
		act.Move = 0;
	}
	
	
	
	
	// ZASOBY
	void resources()
	{
		t_background = new TextureRegion[3];
		t_tab_MEN 	 = new TextureRegion[3];
		
		s_background = new Sprite[3];
		s_tab_MEN    = new Sprite[3];
		s_shad_PRG   = new Sprite[4];
		
		m_name_PRG	 = new Text[4];
		
		shad_PRG_clicked= new boolean[4];
		
		t_background[0] = new stb("Menu/Background/Background0", 256, 256).T;
		t_background[1] = new stb("Menu/Background/Background1", 128, 256).T;
		t_background[2] = new stb("Menu/Background/Background2", 128, 256).T;
		t_music_MEN 	= new stb("Menu/MEN/music", 32, 32).T;
		t_sound_MEN		= new stb("Menu/MEN/sound", 32, 32).T;
		
		t_shop_PRG  	= new stb("Menu/PRG/shop", 128, 128).T;
		t_free_PRG  	= new stb("Menu/PRG/free", 128, 128).T;
		t_play_PRG		= new stb("Menu/PRG/play", 256,  64).T;
		t_list_PRG		= new stb("Menu/PRG/list", 512, 256).T;
		t_sha0_PRG		= new stb("Menu/PRG/sha0", 128,  32).T;
		t_sha1_PRG		= new stb("Menu/PRG/sha1", 128,  32).T;
		t_pape_PRG		= new stb("Menu/PRG/pape", 512, 512).T;
		
		for(int i = 0; i < t_tab_MEN.length; i++)
			t_tab_MEN[i] = new stb("Menu/MEN/tab" + i, 256, 512).T;
		
		for(int i = 0; i < shad_PRG_clicked.length; i++)
			shad_PRG_clicked[i] = false;
		
		
		
		
	}
	// KONIEC ZASOBÓW
	
	
	
	
	// T£O (0, 0)
	void background()
	{	
		s_background[0] = new Sprite(w, h, t_background[0],act.getVertexBufferObjectManager());
		s_background[0].setScaleCenter(0, 0);
		s_background[0].setScale(h/s_background[0].getHeightScaled());
		s_background[0].setPosition(w / 2 - s_background[0].getWidthScaled() / 2, h / 2 - s_background[0].getHeightScaled() / 2);
		
		s_background[1] = new Sprite(w, h, t_background[1],act.getVertexBufferObjectManager());
		s_background[1].setScaleCenter(0, 0);
		s_background[1].setScale(w/s_background[1].getWidthScaled()/4);
		s_background[1].setPosition(0, h / 2 - s_background[1].getHeightScaled() / 2);
		
		s_background[2] = new Sprite(w, h, t_background[2],act.getVertexBufferObjectManager());
		s_background[2].setScaleCenter(0, 0);
		s_background[2].setScale(w/s_background[2].getWidthScaled()/4);
		s_background[2].setPosition(w - s_background[2].getWidthScaled(), h / 2 - s_background[2].getHeightScaled() / 2);
		
		for(int i = 0; i < s_background.length; i++)
			attachChild(s_background[i]);
	}
	// KONIEC T£A
	
	
	
	
	// MEN (0, 0)
	void MEN()
	{
		for(int i = 0; i < s_tab_MEN.length; i++)
		{
			s_tab_MEN[i] = new Sprite(w, h, t_tab_MEN[i],act.getVertexBufferObjectManager())
			{
				@Override
				public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
				{
					if(pEvent.isActionDown())
					{			
						switch(this.getTag())
						{
						case 0:
							//TODO PRZEJAZD W AMULETY						
							System.out.println("s_tab_MEN[0] przechodzê do amuletów");
							break;
							
						case 1:
							Move(0, -h);
							act.Move = 2;
							System.out.println("s_tab_MEN[1] przechodzê do PRG");
							break;
							
						case 2:
							Move(-w, 0);
							act.Move = 1;
							System.out.println("s_tab_MEN[2] przechodzê do ENC");
							break;
						}
						return true;
					}
					return false;
				}
			};
			s_tab_MEN[i].setWidth(0.24f * w);
			s_tab_MEN[i].setHeight(s_tab_MEN[i].getWidth() * 2);
			s_tab_MEN[i].setTag(i);
			s_tab_MEN[i].setPosition(0.1f * w + (s_tab_MEN[i].getWidth() + 0.04f * w) * i, h/2 - s_tab_MEN[i].getHeight()/2);
			
			registerTouchArea(s_tab_MEN[i]);
			attachChild(s_tab_MEN[i]);
		}
		
		s_sound_MEN = new Sprite(w, h, t_sound_MEN, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					s_sound_MEN_clicked = !s_sound_MEN_clicked;
					odswiez_sound();
					
					return true;
				}
				return false;
			}
		};
		s_sound_MEN.setWidth(0.1f * h);
		s_sound_MEN.setHeight(s_sound_MEN.getWidth());
		s_sound_MEN.setPosition(0.01f * h, 0.01f * h);
		odswiez_sound();
		
		registerTouchArea(s_sound_MEN);
		attachChild(s_sound_MEN);
		
		s_music_MEN = new Sprite(w, h, t_music_MEN, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					s_music_MEN_clicked = !s_music_MEN_clicked;
					odswiez_music();
					
					return true;
				}
				return false;
			}
		};
		s_music_MEN.setWidth(0.1f * h);
		s_music_MEN.setHeight(0.1f * h);
		s_music_MEN.setPosition(s_sound_MEN.getX() + s_sound_MEN.getWidth() + 0.01f * w, 0.01f * h);
		odswiez_music();
		
		registerTouchArea(s_music_MEN);
		attachChild(s_music_MEN);
	}
	
	void odswiez_sound()
	{
		if(s_sound_MEN_clicked)
			s_sound_MEN.setColor(0, 0.9f, 0);
		else
			s_sound_MEN.setColor(0.9f, 0, 0);
	}
	
	void odswiez_music()
	{
		if(s_music_MEN_clicked)
			s_music_MEN.setColor(0, 0.9f, 0);
		else
			s_music_MEN.setColor(0.9f, 0, 0);
	}
	
	
	// KONIEC MEN

	
	
	
	// ENC (w, 0)
	void ENC()
	{
		
	}
	// KONIEC ENC
	
	
	
	
	// PRG (0, h)
	void PRG()
	{
		s_shop_PRG = new Sprite(w, h, t_shop_PRG, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{	
					//TODO przejscie do sklepu
					return true;
				}
				return false;
			}
		};
		s_shop_PRG.setWidth(0.15f * h);
		s_shop_PRG.setHeight(0.15f * h);
		s_shop_PRG.setPosition(0, h/2 - s_shop_PRG.getHeight() - 0.06f * h +h);
		
		registerTouchArea(s_shop_PRG);
		attachChild(s_shop_PRG);
		
		s_free_PRG = new Sprite(w, h, t_free_PRG, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO odpalenie wolnej rozgrywki
					return true;
				}
				return false;
			}
		};
		s_free_PRG.setWidth(0.15f * h);
		s_free_PRG.setHeight(0.15f * h);
		s_free_PRG.setPosition(0, h/2 + 0.06f * h +h);
		
		registerTouchArea(s_free_PRG);
		attachChild(s_free_PRG);
		
		s_list_PRG = new Sprite(w, h, t_list_PRG, act.getVertexBufferObjectManager());
		s_list_PRG.setWidth(w - 0.34f * h);
		s_list_PRG.setHeight(0.7f * h);
		s_list_PRG.setPosition(s_shop_PRG.getX() + 0.02f * h + s_shop_PRG.getHeight(), 0.15f * h +h);
		attachChild(s_list_PRG);
		
		s_play_PRG = new Sprite(w, h, t_play_PRG, act.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pEvent, float pX, float pY)
			{
				if(pEvent.isActionDown())
				{
					//TODO odpalanie gry
					act.setCurrentScene(new test());
					return true;
				}
				return false;
			}
		};
		s_play_PRG.setWidth(0.4f * h);
		s_play_PRG.setHeight(0.1f * h);
		s_play_PRG.setPosition(w - 0.01f * h - s_play_PRG.getWidth() , h - 0.01f * h - s_play_PRG.getHeight() +h);
		
		registerTouchArea(s_play_PRG);
		attachChild(s_play_PRG);
		
		for(int i = 0; i < s_shad_PRG.length; i++)
		{
			s_shad_PRG[i] = new Sprite(w, h, t_sha0_PRG, act.getVertexBufferObjectManager())
			 {
				 @Override
		         public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			     {
					 if(shad_click == false)
					 if(przesuwanie == false)
		             if(pSceneTouchEvent.isActionDown())        	   
		             {
		            	 shad_click = true;
		            	 m_contents(this.getTag());
		            	 m_contents_PRG.setPosition(s_pape_PRG);
		            	 reset_shad();
		            	 shad_PRG_clicked[this.getTag()] = true;
		            	 odswiez_shad(this.getTag());
		            	  
		            	 s_pape_PRG.registerEntityModifier(new MoveModifier(0.5f,  s_pape_PRG.getX(),  s_pape_PRG.getX() -w, s_pape_PRG.getY(),  s_pape_PRG.getY())
		            	 {
		            		 @Override
		            		 protected void onModifierStarted(IEntity i)
		            		 {
		            			 przesuwanie = true;
		            			 m_contents_PRG.registerEntityModifier(new MoveModifier(0.5f, m_contents_PRG.getX(), m_contents_PRG.getX() -w, 
		            		 																  m_contents_PRG.getY(), m_contents_PRG.getY()));
		            		 }
		            		 
		            		 @Override
		            		 protected void onModifierFinished(IEntity i)
		            		 {
		            			 przesuwanie = false;
		            		 }
		            	 });   
		            }           	
		         return false;
			    }
			};
			s_shad_PRG[i].setWidth(s_list_PRG.getWidth() *0.9f ); 
			s_shad_PRG[i].setHeight(s_list_PRG.getHeight()/4 - 0.02f * h);
			s_shad_PRG[i].setTag(i);
			odswiez_shad(i);
	
			float odstep = s_shad_PRG[i].getHeightScaled()*0.2f;// odstep miedzy kazdym zleceniem o jedn¹ pi¹t¹ wysokosci tabelki
			
			if(i == 0)
				s_shad_PRG[i].setPosition(s_list_PRG.getX()+s_list_PRG.getWidthScaled()/2 - s_shad_PRG[i].getWidthScaled()/2,
									   	   (s_list_PRG.getY()* 1.05f * i+1)+ s_shad_PRG[i].getHeightScaled()+odstep +h);
			else
				s_shad_PRG[i].setPosition(s_list_PRG.getX()+s_list_PRG.getWidthScaled()/2 - s_shad_PRG[i].getWidthScaled()/2, 
											s_shad_PRG[i -1].getY() + s_shad_PRG[i -1].getHeight()*1.02f);		// nie pytaj siê jak to dzia³a, skopiowa³em ze starego;p
			
			attachChild(s_shad_PRG[i]);
			registerTouchArea(s_shad_PRG[i]);
			
			m_name(i);
				
		}
		
		s_pape_PRG = new Sprite(w, h, t_pape_PRG, act.getVertexBufferObjectManager())
		{
			@Override
	        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
		    {
				if(przesuwanie == false)
	            if(pSceneTouchEvent.isActionDown())        	   
	            {   	            	
	            	s_pape_PRG.registerEntityModifier(new MoveModifier(0.5f,  s_pape_PRG.getX(),  s_pape_PRG.getX() +w, s_pape_PRG.getY(),  s_pape_PRG.getY())
	            	{
	            		@Override
	            		protected void onModifierStarted(IEntity i)
	            		{
	            			przesuwanie = true;
	            			m_contents_PRG.registerEntityModifier(new MoveModifier(0.5f, m_contents_PRG.getX(), m_contents_PRG.getX() +w, 
	            		 																 m_contents_PRG.getY(), m_contents_PRG.getY()));
	            		}
	            		 
	            		@Override
	            		protected void onModifierFinished(IEntity i)
	            		{
	            			przesuwanie = false;
	            			shad_click = false;
	            		}
	            	});	  
	            }
	        return false;
		   }
		};
		s_pape_PRG.setWidth(h);
		s_pape_PRG.setHeight(s_pape_PRG.getWidth());
		s_pape_PRG.setPosition(w/2 - s_pape_PRG.getWidth()/2 +w, h);
		registerTouchArea(s_pape_PRG);
		attachChild(s_pape_PRG);
	}
	
	void m_name(int i)
	{
		m_name_PRG[i] = new Text(w, h, act.mFont, "Nazwa misji", 1024, act.getVertexBufferObjectManager());
		m_name_PRG[i].setColor(1.0f, 1.0f, 1.0f);
		m_name_PRG[i].setPosition(s_shad_PRG[i].getX() + 0.01f * w, s_shad_PRG[i].getY() + s_shad_PRG[i].getHeight() / 2 - m_name_PRG[i].getHeight() / 2);
		
		if(m_name_PRG[i].getWidth() > s_shad_PRG[i].getWidth() - 0.01f * w)
		{
			m_name_PRG[i].setScaleCenter(0, 0);
			m_name_PRG[i].setScale(s_shad_PRG[i].getWidth() - 0.01f * w);
		}
		attachChild(m_name_PRG[i]);	
	}
	
	void m_contents(int mission_number)
	{
		m_contents_PRG = new Text(w, h, act.mFont,"Jakiœ tekst misji", 1024, act.getVertexBufferObjectManager());
		m_contents_PRG.setColor(0.0f, 0.0f, 0.0f);
		attachChild(m_contents_PRG);
	}
	
	void odswiez_shad(int i)
	{
		if(shad_PRG_clicked[i] == true)
			s_shad_PRG[i].setSprite(t_sha1_PRG);
		else
			s_shad_PRG[i].setSprite(t_sha0_PRG);
	}
	
	void reset_shad()
	{
		for(int i = 0; i < shad_PRG_clicked.length; i++)
		{
			shad_PRG_clicked[i] = false;
			odswiez_shad(i);
		}
	}
	// KONIEC PRG
	
	
	
	
	// MAG (-w, h)
	void MAG()
	{
		
	}
	// KONIEC MAG
	
	
	
	
	// SHO (-2w, h)
	void SHO()
	{
			
	}
	// KONIEC SHO
	
	
	
	
	// PRZESUWANIE MIÊDZY PODSCENAMI
	void Move(float wektorX, float wektorY)
	{
		if(przesuwanie == false)
		{
			this.registerEntityModifier(new MoveModifier(0.5f, this.getX(), this.getX() + wektorX, this.getY(), this.getY() + wektorY)
			{
				@Override
				protected void onModifierStarted(IEntity i)
				{
					przesuwanie = true;
				}
				
				@Override
				protected void onModifierFinished(IEntity i)
				{
					przesuwanie = false;
				}
			});
			
			// grafiki o sta³ej pozycji
			for(int i = 0; i < s_background.length; i++)
				s_background[i].registerEntityModifier(new MoveModifier(0.5f, s_background[i].getX(), s_background[i].getX() - wektorX, 
																		  	  s_background[i].getY(), s_background[i].getY() - wektorY));
			
		}
	}
	
	void FastMove(int podscena)
	{
		switch(podscena)
		{
		case 1: // DO MEN
			this.setPosition(0, 0);
			act.Move = 0;
			break;
			
		case 2: // DO ENC
			this.setPosition(w, 0);
			act.Move = 1;
			break;
			
		case 3: // DO PRG
			this.setPosition(0, h);
			act.Move = 2;
			break;
			
		case 4: // DO MAG
			this.setPosition(-w, h);
			act.Move = 3;
			break;
			
		case 5: // DO SHO
			this.setPosition(-2*w, h);
			act.Move = 4;
			break;
		}
	}
	
	@Override
	public void back()
	{
		if(przesuwanie == false)								
		switch(act.Move)										
		{														
		case 0: // wyjœcie z aplikacji							
			act.finish();											
			break;												
																	
		case 1: // ENC --> MEN
			Move(w, 0);
			act.Move = 0;
			break;
			
		case 2: // PRG --> MEN
			Move(0, h);
			
			if(shad_click = true)
				s_pape_PRG.registerEntityModifier(new MoveModifier(0.5f,  s_pape_PRG.getX(),  s_pape_PRG.getX() +w, s_pape_PRG.getY(),  s_pape_PRG.getY()));
				m_contents_PRG.registerEntityModifier(new MoveModifier(0.5f,  m_contents_PRG.getX(),  m_contents_PRG.getX() +w, m_contents_PRG.getY(),  m_contents_PRG.getY()));
				shad_click = false;
				
			act.Move = 0;
			break;
			
		case 3: // MAG --> PRG
			Move(-w, 0);
			act.Move = 2;
			break;
			
		case 4: // SHO --> MAG
			Move(-w, 0);
			act.Move = 3;
			break;
		}
	}
}	
