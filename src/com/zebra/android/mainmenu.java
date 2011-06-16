package com.zebra.android;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainmenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        Button createbutton = (Button) findViewById(R.id.create);
        createbutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), create.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button reprintbutton = (Button) findViewById(R.id.reprint);
        reprintbutton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), reprint.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button inventorybutton = (Button) findViewById(R.id.inventory);
        inventorybutton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), inventory.class);  
        		startActivityForResult(i, 0);  
        	}
        });
    }
}


