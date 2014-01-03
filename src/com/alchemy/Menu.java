package com.alchemy;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

public class Menu extends Scene
{

	Menu()
	{
		this.setColor(new Color(1,1,1));
		System.out.println("poszlo");
		attachChild(new Sprite(0,0,new stb("Menu/coin",512,512).T,MainActivity.getSharedInstance().getVertexBufferObjectManager()));
	}
}
