package com.zebra.android; 
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;  
import android.view.MotionEvent;
public class ZebraActivity extends Activity {
	
	private Thread splashThread;
	/** Called when the activity is first created. */
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);      
		setContentView(R.layout.splash);       
		splashThread = new Thread() {       
			@Override         
			public void run() {          
				try {                
					synchronized(this){
						//wait a period of time or exit on touch
						wait(5000);
					}             
				} catch (InterruptedException e) {                
					e.printStackTrace();
				} finally {                
					finish();              
					Intent i = new Intent();  
					i.setClassName("com.zebra.android",                  
							"com.zebra.android.mainmenu");         
					startActivity(i);             
				}          
			}       
		};   
		splashThread.start();    
	}
	public boolean onTouchEvent(MotionEvent evt){
		if(evt.getAction() == MotionEvent.ACTION_DOWN){
			synchronized(splashThread){
				splashThread.notifyAll();
			}
		}
		return true;
	}
} 


