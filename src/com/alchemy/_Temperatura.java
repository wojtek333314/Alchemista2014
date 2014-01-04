package com.alchemy;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.input.touch.detector.HoldDetector;
import org.andengine.input.touch.detector.HoldDetector.IHoldDetectorListener;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class _Temperatura implements ITimerCallback
{
	MainActivity	act;
	int				w,h;
	
	float			temp=0.3f;//aktualna temperatura
	Podgrzej		podgrzej;//przycisk od podgrzewania
	Sprite			ramka,
					temp_tex;
	TimerHandler 	timer;
	
	
	_Temperatura(float x,float y,Scene s)
	{
		act = MainActivity.getSharedInstance();
		w   = act.w;
		h   = act.h;
		timer = new TimerHandler(0.2f,true,this);
		
		grafika(x,y);
		AddToScene(s);
	}
	
	void AddToScene(Scene s)
	{
		s.attachChild(podgrzej);
		s.attachChild(temp_tex);
		s.attachChild(ramka);
		s.registerUpdateHandler(podgrzej.continuousHoldDetector);
		s.registerTouchArea(podgrzej);
		s.registerUpdateHandler(timer);
	}
	
	void grafika(float x, float y)
	{
		float skala = 16f;
		
		podgrzej = new Podgrzej(0,0,new stb("Alchemy/temp_btn",128,128).T,act.getVertexBufferObjectManager());
		podgrzej.setScaleCenter(0, 0);
		podgrzej.setScale(w/skala/podgrzej.getWidth());
		
		ramka = new Sprite(0,0,new stb("Alchemy/temp_ramka",128,512).T,act.getVertexBufferObjectManager());
		ramka.setScaleCenter(0, 0);
		ramka.setScale(w/ramka.getWidth()/skala);
		
		temp_tex = new Sprite(0,0,new stb("Alchemy/temp_fill",64,64).T,act.getVertexBufferObjectManager());
		temp_tex.setSize(ramka.getWidthScaled(),1);
		
		ramka.setPosition(x, y);
		podgrzej.setPosition(x, y+ramka.getHeightScaled());
		temp_tex.setPosition(x,y+ramka.getHeightScaled());
		
		temp_tex.setSize(temp_tex.getWidthScaled(), ((ramka.getHeightScaled() * temp)/1.0f));
		temp_tex.setPosition(ramka.getX(), this.ramka.getY()+this.ramka.getHeightScaled() - this.temp_tex.getHeightScaled());
	}
	
	void temp_update()
	{
		temp_tex.setSize(temp_tex.getWidthScaled(), ((ramka.getHeightScaled() * temp)/1.0f));
		temp_tex.setPosition(ramka.getX(), this.ramka.getY()+this.ramka.getHeightScaled() - this.temp_tex.getHeightScaled());
	}
	
	
	
	class Podgrzej extends ButtonSprite implements IHoldDetectorListener
	{
		public ContinuousHoldDetector continuousHoldDetector;
		Podgrzej(float x,float y,ITextureRegion tex,VertexBufferObjectManager ver)
		{
			super(x,y,tex,ver);
			continuousHoldDetector = new ContinuousHoldDetector(this);
			continuousHoldDetector.setTriggerHoldMinimumMilliseconds(0);
			
		}

		@Override 
		public boolean onAreaTouched(TouchEvent ev,float x,float y)
		{
			continuousHoldDetector.onTouchEvent(ev);
			return false;
		}
		@Override
		public void onHoldStarted(HoldDetector pHoldDetector, int pPointerID,
				float pHoldX, float pHoldY) {
			pHoldDetector.setEnabled(true);
		}

		@Override
		public void onHold(HoldDetector pHoldDetector,
				long pHoldTimeMilliseconds, int pPointerID, float pHoldX,
				float pHoldY) {
			
			temp+=0.006f;
			temp_update();
		}

		@Override
		public void onHoldFinished(HoldDetector pHoldDetector,
				long pHoldTimeMilliseconds, int pPointerID, float pHoldX,
				float pHoldY) {
			
		}
	}



	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
		temp-=0.001f;
		if(temp<0) temp=0;
		temp_update();
	}
	
}
