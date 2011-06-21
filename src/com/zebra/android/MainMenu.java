package com.zebra.android;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    /** Called when the activity is first created. */
    @Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    
        Button createButton = (Button) findViewById(R.id.create);
        createButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), CreateLabel.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button relabelButton = (Button) findViewById(R.id.relabel);
        relabelButton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), ReLabel.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button inventoryButton = (Button) findViewById(R.id.inventory);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), Inventory.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button printerButton = (Button) findViewById(R.id.test_printer);
        printerButton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), ConnectionScreen.class);  
        		startActivityForResult(i, 0);  
        	}
        });
    }
}


