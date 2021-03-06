package com.alchemy;


import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.util.DisplayMetrics;


public class MainActivity extends BaseGameActivity 
{
    public int 	w = 2,
               	h = 4,
             	ID = 0,
             	Move = 0; // if 0 wychodzi z aplikacji
    
    public Font 				mFont; //czcionka do pisania na ekranie
    public Camera 				mCamera ;//uchwyt do kamery
    public Scene 				mCurrentScene; //uchwyt do obecnie aktywnej sceny
    public static BaseActivity 	instance; //?
    boolean 					info_statement = false;
    TimerHandler 				timerHandler;
    boolean 					GRun = false;
    boolean 					TimerRun = false;
    KONTENER					kon;
    Back 						back;
	
    _Mozdzierz					mozdzierz;	//referencja do mozdzierza
    _Kociol						kociol;		//referencja do mozdzierza
    _SkladnikiListaGra			lista;      //referencja do listy skladnikow w grze
    @Override
    public void onBackPressed()
    {
    	back.b();
    }
    
	@Override
    public EngineOptions onCreateEngineOptions() {
     
    	final DisplayMetrics displayMetrics = new DisplayMetrics();
    	this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	w = displayMetrics.widthPixels;
    	h = displayMetrics.heightPixels;


        mCamera = new Camera(0, 0, w, h);
        instance = this;
        EngineOptions silnik =  new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,//+down
            new RatioResolutionPolicy(w, h), mCamera); //to jest jedna linia! 
        
        silnik.getAudioOptions().setNeedsMusic(true);
        silnik.getAudioOptions().setNeedsSound(true);

        return silnik;   
    }
    

    protected void onCreateResourcesmoje() { //tu zalaczam wszystkie zasoby
    	System.gc();//garbage collector

    	FontFactory.setAssetBasePath("Fonts/"); //ustawiam sciezke dla czcionek w folderze Assets/Fonts/ 	
    	BitmapTextureAtlas mDroidFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512 , 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	
    	mFont = FontFactory.createFromAsset(this.getFontManager(),mDroidFontTexture,this.getAssets(),"MagicSchoolOne.ttf",
    			fontscale() ,true,Color.WHITE);
    	mFont.load();

    	  BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");//ustawiam sciezke dla plikow grafiki Assets/gfx/
    	  SoundFactory.setAssetBasePath("sfx/");
    	  MusicFactory.setAssetBasePath("sfx/");  
    	  
    	  kon = new KONTENER();
    	  back = new Back();
    }

    protected Scene onCreateScenemoje() 
    {
        return mCurrentScene;
    }
    
    
    public float fontscale()//skalowanie czcionki
    {
    	return (h / 12f);
    }
    
    
    public static MainActivity getSharedInstance() //do pobierania instancji aktywnosci z niej mozesz sie odwolywac do kazdego obiektu
    {
        return (MainActivity) instance;
    }

    
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) 
	{
		onCreateResourcesmoje();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) 
	{

		this.setCurrentScene(new Menu());
		 pOnCreateSceneCallback.onCreateSceneFinished(mCurrentScene);
	}


	
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) 
	{
		 pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	
    public void setCurrentScene(Scene scene) 
    {//zmienia aktywn¹ scenê
  	  //System.gc();
	  this.mCurrentScene = scene;
      getEngine().setScene(mCurrentScene);
  }

	
	
}