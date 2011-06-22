package com.zebra.android;
/********************************************** 
 * CONFIDENTIAL AND PROPRIETARY 
 *
 * The source code and other information contained herein is the confidential and the exclusive property of
 * ZIH Corp. and is subject to the terms and conditions in your end user license agreement.
 * This source code, and any other information contained herein, shall not be copied, reproduced, published, 
 * displayed or distributed, in whole or in part, in any medium, by any means, for any purpose except as
 * expressly permitted under such license agreement.
 * 
 * Copyright ZIH Corp. 2010
 *
 * ALL RIGHTS RESERVED 
 ***********************************************/


import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Looper;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;


import com.zebra.android.printer.ZebraPrinter;
import com.zebra.android.printer.PrinterLanguage;
import com.zebra.android.printer.ZebraPrinterFactory;
import com.zebra.android.printer.ZebraPrinterLanguageUnknownException;



public class ConnectionScreen extends Activity {

    protected ZebraPrinterConnection zebraPrinterConnection;
    protected ZebraPrinter printer;
    protected Button testButton;
    private EditText macAddress;
    private TextView statusField;

    
    public static final String bluetoothAddressKey = "ZEBRA_DEMO_BLUETOOTH_ADDRESS";
    public static final String PREFS_NAME = "OurSavedAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_screen);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        macAddress = (EditText) this.findViewById(R.id.mac_input);
        String mac = settings.getString(bluetoothAddressKey, "");    
        macAddress.setText(mac);
        
        statusField = (TextView) this.findViewById(R.id.status_field);
        setStatus("Not Connected", Color.RED);
        
        
        
        testButton = (Button) this.findViewById(R.id.test_button);
        testButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        enableTestButton(false);
                        Looper.prepare();
                        doConnectionTest();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });
        
        toggleEditField(macAddress, true);
        
        Button PtoMain = (Button) findViewById(R.id.p_to_main);
		PtoMain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
        		setResult(RESULT_OK, intent);
        		finish();
			}
		});
    }

    private void toggleEditField(EditText editText, boolean set) {
        /*
         * Note: Disabled EditText fields may still get focus by some other means, and allow text input.
         *       See http://code.google.com/p/android/issues/detail?id=2771
         */
        editText.setEnabled(set);
        editText.setFocusable(set);
        editText.setFocusableInTouchMode(set);
    }


    protected String getMacAddressFieldText() {
        return macAddress.getText().toString();
    }
    
    private void enableTestButton(final boolean enabled) {
        runOnUiThread(new Runnable() {
            public void run() {
                testButton.setEnabled(enabled);
            }
        });
    }
    
    private void doConnectionTest() {
        printer = connect();
        if (printer != null) {
            sendTestLabel();
        } else {
            disconnect();
        }
    }   
    private void sendTestLabel() {
        try {
            byte[] configLabel = getConfigLabel();
            zebraPrinterConnection.write(configLabel);
            setStatus("Sending Data", Color.BLUE);
            sleep(1500);
            if (zebraPrinterConnection instanceof BluetoothPrinterConnection) {
                String friendlyName = ((BluetoothPrinterConnection) zebraPrinterConnection).getFriendlyName();
                setStatus(friendlyName, Color.MAGENTA);
                sleep(500);
            }
        } catch (ZebraPrinterConnectionException e) {
            setStatus(e.getMessage(), Color.RED);
        } finally {
            disconnect();
        }
    }
    public ZebraPrinter connect() {
        setStatus("Connecting...", Color.YELLOW);
        zebraPrinterConnection = null;
        zebraPrinterConnection = new BluetoothPrinterConnection(getMacAddressFieldText());
        //SettingsHelper.saveBluetoothAddress(this, getMacAddressFieldText());
        
        try {
            zebraPrinterConnection.open();
            setStatus("Connected", Color.GREEN);
        } catch (ZebraPrinterConnectionException e) {
            setStatus("Comm Error! Disconnecting", Color.RED);
            sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null;

        if (zebraPrinterConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(zebraPrinterConnection);
                setStatus("Determining Printer Language", Color.YELLOW);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
                setStatus("Printer Language " + pl, Color.BLUE);
            } catch (ZebraPrinterConnectionException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                setStatus("Unknown Printer Language", Color.RED);
                printer = null;
                sleep(1000);
                disconnect();
            }
        }

        return printer;
    }
    
    public void disconnect() {
        try {
            setStatus("Disconnecting", Color.RED);
            if (zebraPrinterConnection != null) {
                zebraPrinterConnection.close();
            }
            setStatus("Not Connected", Color.RED);
        } catch (ZebraPrinterConnectionException e) {
            setStatus("COMM Error! Disconnected", Color.RED);
        } finally {
            enableTestButton(true);
        }
    }
    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                statusField.setBackgroundColor(color);
                statusField.setText(statusMessage);
            }
        });
       sleep(1000);
    }
    private byte[] getConfigLabel() {
        PrinterLanguage printerLanguage = printer.getPrinterControlLanguage();

        byte[] configLabel = null;
        if (printerLanguage == PrinterLanguage.ZPL) {
            configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
        } else if (printerLanguage == PrinterLanguage.CPCL) {
            String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
            configLabel = cpclConfigLabel.getBytes();
        }
        return configLabel;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
