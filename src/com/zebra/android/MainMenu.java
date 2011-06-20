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
    
        Button createbutton = (Button) findViewById(R.id.create);
        createbutton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), CreateLabel.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button relabelbutton = (Button) findViewById(R.id.relabel);
        relabelbutton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), ReLabel.class);  
        		startActivityForResult(i, 0);  
        	}
        });
        
        Button inventorybutton = (Button) findViewById(R.id.inventory);
        inventorybutton.setOnClickListener(new View.OnClickListener() {
		  	public void onClick(View view){
        		Intent i = new Intent(view.getContext(), Inventory.class);  
        		startActivityForResult(i, 0);  
        	}
        });
    }
}


