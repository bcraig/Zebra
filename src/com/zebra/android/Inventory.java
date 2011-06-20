package com.zebra.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Inventory extends Activity {
	private EditText serial;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory);
		
		Button ItoMain = (Button) findViewById(R.id.i_to_main);
		ItoMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
		
		serial = (EditText) findViewById(R.id.inventory_manual_serial);
	}
	
	public void startScanner(View v){
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", 
		"QR_CODE_MODE"); 
		startActivityForResult(intent, 0);
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {     
		if (requestCode == 0) {         
			if (resultCode == RESULT_OK) {             
				String contents = intent.getStringExtra("SCAN_RESULT");             
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");   
			
				serial.setText(contents);
				// Handle successful scan 

				/*onCreateDialog(DIALOG_SCAN_COMPLETE, temp);
				Bundle temp = new Bundle();
				temp.putString(SCAN_CONTENTS_KEY, contents);*/


				
			} else if (resultCode == RESULT_CANCELED) {             
				// Handle cancel         
			}   
		}
	} 
}
