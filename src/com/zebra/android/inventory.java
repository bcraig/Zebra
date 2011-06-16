package com.zebra.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class inventory extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory);
		
		Button main = (Button) findViewById(R.id.imain);
		main.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
	}
}
