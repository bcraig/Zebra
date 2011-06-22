package com.zebra.android;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ReLabel extends Activity {
	private EditText serial;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.re_label);
		
		Button RtoMain = (Button) findViewById(R.id.r_to_main);
		RtoMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
		serial = (EditText) findViewById(R.id.reprint_manual_serial);

	}
	
	public void startScanner(View v){
		IntentIntegrator.initiateScan(ReLabel.this); 

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		switch(requestCode) { 
			case IntentIntegrator.REQUEST_CODE: { 
				if (resultCode != RESULT_CANCELED) { 
					IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data); 
					if (scanResult != null) { 
						String number = scanResult.getContents(); 
						// Do whatever you want with the barcode...
						serial.setText(number);
					} 
				} 
				break; 
			} 
		} 
	} 

}
