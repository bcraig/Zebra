package com.zebra.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateLabel extends Activity {
	private EditText serial;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_label);
		
		
		Calendar ci = Calendar.getInstance();
		String currentdate= (ci.get(Calendar.MONTH)+1) + "/" + ci.get(Calendar.DAY_OF_MONTH) + "/" + ci.get(Calendar.YEAR);
		
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
	}	

	private SimpleDateFormat SimpleDateFormat(String string, Locale us) {
		// TODO Auto-generated method stub
		return null;
	}

	/*	Button LaunchScan = (Button) findViewById(R.id.create_scan);
		LaunchScan.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setClassName("com.xxx.your_package_name",
                        "com.xxx.your_class_name");

                startActivity(i);

			}
		});*/
		/*serial = (EditText) findViewById(R.id.manual_serial);
		serial.setOnFocusChangeListener(new OnFocusChangeListener(){
			
			public void onFocusChange(View v, boolean hasFocus){
				if(hasFocus==true){
					serial.setText("");
				}
			}
		});*/


		
	/*	
		if(serial.getText().toString().length()>=7){
			//have serial
			serialNumber = serial.getText().toString();
			TextView text = (TextView) findViewById(R.id.create_text1);
			text.setText(serialNumber);
		}*/
	
	
	/*private SimpleDateFormat SimpleDateFormat(String string, Locale us) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public void startScanner(View v){
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", 
		"QR_CODE_MODE"); 
		startActivityForResult(intent, 0);
		
	}
	
	/*code below from http://stackoverflow.com/questions/2050263/using-zxing-to-create-an-android-barcode-scanning-app */
	/*public Button.OnClickListener mScan = new Button.OnClickListener() {
		public void onClick(View v) {
			
		}
	} ;  */

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {     
		if (requestCode == 0) {         
			if (resultCode == RESULT_OK) {             
				String contents = intent.getStringExtra("SCAN_RESULT");             
				intent.getStringExtra("SCAN_RESULT_FORMAT");   
			
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
	
	/*protected Dialog onCreateDialog(int Id, Bundle args){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getSerialFieldText()).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//MyActivity.this.finish();           
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {                
				dialog.cancel();           
				}       
			});AlertDialog alert = builder.create();
			return alert;
	}
	protected void onPrepareDialog(int Id, Bundle args, Dialog dialog){
		
	}

		
	
	public boolean onKey(View v, int keyCode, KeyEvent event){
		if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
			//enter key pressed
			String serialNumber = serial.getText().toString();
			
			
			
			return true;
		}
		return false;
	}
	
	private void toggleEditField(EditText editText, boolean set) {
		/*
		 * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
		 *       See http://code.google.com/p/android/issues/detail?id=2771
		 *
		editText.setEnabled(set);
		editText.setFocusable(set);
		editText.setFocusableInTouchMode(set);
	}
    private String getSerialFieldText() {
        return serial.getText().toString();
    }
	*/
}
