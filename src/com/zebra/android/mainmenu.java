package com.zebra.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mainmenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }    
    
    public void gotocreate(View create){
    	Intent i = new Intent();  
		i.setClassName("com.zebra.android",                  
				"com.zebra.android.create");         
		startActivity(i);  
    }
    
}