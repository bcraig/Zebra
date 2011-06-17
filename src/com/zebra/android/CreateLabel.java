package com.zebra.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
		EditText serial = (EditText) findViewById(R.id.manual_serial);
		if(serial.length()==7){
			//have serial
			serialNumber = serial.toString();
			TextView text = new TextView(this);
			text.layout(0, 300, 300, 500);
			text.setText(serialNumber);
		}
	}
}
