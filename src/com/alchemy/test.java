package com.alchemy;

import org.andengine.entity.scene.Scene;

public class test extends Scene 
{
	MainActivity act;
	_Temperatura temp;
	
	test()
	{
		act = MainActivity.getSharedInstance();
		_Kociol k = new _Kociol(this,3,0,0,new stb("Alchemy/kociol",512,512).T, act.getVertexBufferObjectManager());
		attachChild(k);
		registerTouchArea(k);
		k.attachPlyn(this);
		
		
	
		temp = new _Temperatura(0,0,this);
		_Mozdzierz mozdzierz = new _Mozdzierz(1,600,200,new stb("Alchemy/mozdzierz",128,128).T,act.getVertexBufferObjectManager());
		mozdzierz.setPosition(act.w - mozdzierz.getWidthScaled(), act.h-mozdzierz.getHeightScaled()*1.1f);
		mozdzierz.attach(this);
		registerTouchArea(mozdzierz);
		act.mozdzierz = mozdzierz;
		act.kociol    = k;
		
		_SkladnikiListaGra lista = new _SkladnikiListaGra();
		lista.attach(this);
		lista.setPosition(act.w - lista.getWidthScaled(), 0);
		act.lista     = lista;
		
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
	}

	
	
}
