package com.alchemy;

public class Back 
{
	MainActivity act;
	
	Back()
	{
		act = MainActivity.getSharedInstance();
	}
	
	void b()
	{
		act.mCurrentScene.back();
	}
}
