package com.zebra.android; 
import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;  
public class ZebraActivity extends Activity {    
	/** Called when the activity is first created. */
	@Override   
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);      
		setContentView(R.layout.splash);       
		Thread splashThread = new Thread() {       
			@Override         
			public void run() {          
				try {                
					int waited = 0;             
					while (waited < 5000) {   
						sleep(100);             
						waited += 100;                
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
} 


