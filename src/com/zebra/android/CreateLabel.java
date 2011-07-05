package com.zebra.android;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

public class CreateLabel extends Activity {
	private EditText serial; 		//field where user can enter the serial number of the new asset 
	private String currentdate;     //automatically generated current date
	private WebServices webServices;
	private AlertDialog alertDialog;
	public static AlertDialog connectionAlertDialog;
	public String LOG_TAG = "SN LOG";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_label);
		
		webServices = new WebServices();
		
		
		Calendar ci = Calendar.getInstance();
		currentdate= (ci.get(Calendar.MONTH)+1) + "/" + ci.get(Calendar.DAY_OF_MONTH) + "/" + ci.get(Calendar.YEAR);
		
		TextView date =(TextView) findViewById(R.id.create_date);
		date.setText(currentdate);
		
		
		Button CtoMain = (Button) findViewById(R.id.c_to_main);
		CtoMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
		
		serial = (EditText) findViewById(R.id.manual_serial);
		
		
		
		connectionAlertDialog = new AlertDialog.Builder(this).create();
		connectionAlertDialog.setTitle("Connection Problem");
		connectionAlertDialog.setMessage("Connection to Service Now timed out");
		connectionAlertDialog.setButton("Okay", new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which){return;}});
		
		int mInt=0;
		Log.d(LOG_TAG , "mInt value: "+mInt);
	}	

	private SimpleDateFormat SimpleDateFormat(String string, Locale us) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createAsset(View v){
		
		
		
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Verify Asset Information");
		alertDialog.setMessage("Serial Number: "+ serial.getText()+"\nDate: " + currentdate);
		alertDialog.setButton("Correct", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				//create record with service now
				webServices.createAsset(serial.getText().toString());
				
				//print new asset tag
				
				
				return;
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		
		alertDialog.show();

	}
	
		

	public void startScanner(View v){		
		IntentIntegrator.initiateScan(CreateLabel.this); 
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
	
	
	
	private void toggleEditField(EditText editText, boolean set) {
		/*
		 * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
		 *       See http://code.google.com/p/android/issues/detail?id=2771
		 **/
		editText.setEnabled(set);
		editText.setFocusable(set);
		editText.setFocusableInTouchMode(set);
	}
    private String getSerialFieldText() {
        return serial.getText().toString();
    }
}
