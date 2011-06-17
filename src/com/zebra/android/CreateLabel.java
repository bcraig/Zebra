package com.zebra.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateLabel extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		String serialNumber;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_label);
		
		Button CtoMain = (Button) findViewById(R.id.c_to_main);
		CtoMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
		
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
		
		final EditText serial = (EditText) findViewById(R.id.manual_serial);
		serial.setOnFocusChangeListener(new OnFocusChangeListener(){
			
			public void onFocusChange(View v, boolean hasFocus){
				if(hasFocus==true){
					serial.setText("");
				}
			}
		});
		
		
	/*	
		if(serial.getText().toString().length()>=7){
			//have serial
			serialNumber = serial.getText().toString();
			TextView text = (TextView) findViewById(R.id.create_text1);
			text.setText(serialNumber);
		}*/
		
	}
}
