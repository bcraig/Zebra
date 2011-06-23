package com.zebra.android;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;


public class WebServices {
	private String createUrl; 
	private int timeout;
	//private HttpParams httpParams;
	//private HttpClient client;
	
	
	public WebServices(){
		createUrl = "https://testzebra.service-now.com/cmdb_ci_computer.do?sysparm_action=insert&JSON";
		timeout = 10000;
	}
	public void createAsset(String serialNumber){
		final String sn = serialNumber;
        Thread t = new Thread(){         
        	public void run() {                 
        		Looper.prepare(); //For Preparing Message Pool for the child Thread                 
        		HttpClient client = new DefaultHttpClient();                 
        		HttpConnectionParams.setConnectionTimeout(client.getParams(), timeout); //Timeout Limit                 
        		HttpResponse response;                 
        		JSONObject json = new JSONObject();                 
        		try{                     
        			HttpPost post = new HttpPost(createUrl);                     
        			json.put("serial_number", sn);                     
        			json.put("hardware_status", "Pending Install");
        			json.put("name", "TEST PC FROM ANDROID APP");
        			StringEntity se = new StringEntity( "JSON: " + json.toString());                       
        			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));                     
        			post.setEntity(se);                     
        			response = client.execute(post);                     
        			/*Checking response */                     
        			if(response!=null){  
        				InputStream in = response.getEntity().getContent(); //Get the data in the entity   
        			}                 
        		}
        		catch(Exception e){                     
        			e.printStackTrace();   
        			CreateLabel.connectionAlertDialog.show();
              
        		}                 
        		Looper.loop(); //Loop in the message queue             
        		}         
        	};         
       	t.start();           
	}
    
	public String retrieveDateCreated(){
		return null;
	}
	


	
	
}
