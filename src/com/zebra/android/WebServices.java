package com.zebra.android;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/*import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;*/
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.util.Base64;


public class WebServices {
	private String createUrl; 
	private int timeout;
	//private HttpParams httpParams;
	//private HttpClient client;
	private String authorizationString;
	
	public WebServices(){

		//createUrl = "https://testzebra.service-now.com/incident.do?JSON&sysparm_action=insert";
		//timeout = 10000;
		/*String val = (new StringBuffer("username").append(":").append("password")).toString();
		byte[] base = val.getBytes();*/
		//authorizationString = "Basic " + (Base64.encode("bcraig:chicago".getBytes(), 0));


	}
	public void createAsset(String serialNumber){
		final String sn = serialNumber;

		//createUrl = "https://testzebra.service-now.com/incident.do?JSON&sysparm_action=insert";
		createUrl = "https://testzebra.service-now.com/cmdb_ci_computer.do?sysparm_action=insert&JSON";
		timeout = 10000;
		/*String val = (new StringBuffer("username").append(":").append("password")).toString();
		byte[] base = val.getBytes();*/
		//authorizationString = "Basic " + (Base64.encode("bcraig:chicago".getBytes(), 0));
		String encoding = "bcraig:chicago";
		
		
        Thread t = new Thread(){         
        	public void run() {                 
        		Looper.prepare(); //For Preparing Message Pool for the child Thread                 
        		
        		//DefaultHttpClient client = new DefaultHttpClient();        
        		//HttpConnectionParams.setConnectionTimeout(client.getParams(), timeout); //Timeout Limit           
        		//client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials("BCRAIG:chicago"));   		  		
        		
        		//HttpResponse response;   
        		JSONObject json = new JSONObject();                 
        		try{                     
        		
        			URL url = new URL(createUrl);
        			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        			connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
        			//connection.setRequestProperty ("Authorization", authorizationString);
        			
        			//InputStream in = url.openStream();
        			String jsonobj = "{\"records\":[{\"assigned_to\":\"Ryan McCarthy\", \"assignment_group\":\"VH Sysadmin\", \"caller_id\":\"Ryan McCarthy\",\"priority\":\"4\", \"short_description\":\"this is a test\" }]}";
        			json.put("assignment_group","VH Sysadmin");
        			json.put("caller_id","Benjamin Craig");
        			json.put("short_description","TEST TICKET");
        			
        			
                    //OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

        			String authStringEnc = "YmNyYWlnOmNoaWNhZ28=";
        			System.out.println("Base64 encoded auth string: " + authStringEnc);

        			connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());
        			InputStream is = connection.getInputStream();
        			InputStreamReader isr = new InputStreamReader(is);
 

        			int numCharsRead;
        			char[] charArray = new char[1024];
        			StringBuffer sb = new StringBuffer();
        			while ((numCharsRead = isr.read(charArray)) > 0) {
        				sb.append(charArray, 0, numCharsRead);
        			}
        			String result = sb.toString();
        			
        			output.write(json.toString().getBytes());
        			
        			charArray = new char[1024];
        			sb = new StringBuffer();
        			while ((numCharsRead = isr.read(charArray)) > 0) {
        				sb.append(charArray, 0, numCharsRead);
        			}
        			//result = sb.toString();
        			
        			CreateLabel.connectionAlertDialog.setMessage(result);
        			CreateLabel.connectionAlertDialog.show();
        			
///////////////////////////////////////////////////////////////////////
        			
        			
        			
        			
        			
                    //output.write(jsonobj.getBytes());
                    
                    /*if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    	// OK  
                    	CreateLabel.connectionAlertDialog.setMessage("Server responded HTTP_OK");
            			CreateLabel.connectionAlertDialog.show();
                    	
                    } 
                    else {
                    		// Server returned HTTP error code. 
                    	CreateLabel.connectionAlertDialog.setMessage(connection.getResponseCode()+"");
                		CreateLabel.connectionAlertDialog.show();
                   	}   */
                    //output.close();
                	connection.disconnect();

        		} catch (MalformedURLException e) {             
                    	// ...      
            		e.printStackTrace();   
            		CreateLabel.connectionAlertDialog.setMessage(e.toString());
            		CreateLabel.connectionAlertDialog.show();
                } catch (IOException e) {             
            		e.printStackTrace();   
            		CreateLabel.connectionAlertDialog.setMessage(e.toString());
            		CreateLabel.connectionAlertDialog.show();
            	} catch (Exception e){
            		e.printStackTrace();   
            		CreateLabel.connectionAlertDialog.setMessage(e.toString());
            		CreateLabel.connectionAlertDialog.show();
            	}
            	Looper.loop(); //Loop in the message queue             
            }         
        };         
        t.start();          
    }
        			
        			
        			
        			//HttpPost post = new HttpPost(createUrl);
        			/*json.put("hardware_status", "Pending Install");
        			json.put("name", "TEST PC FROM ANDROID APP");
        			json.put("serial_number", sn);   
        			json.put("subcategory", "Computer");
        	
        			StringEntity se = new StringEntity( json.toString());
        			String jsonobj = "{ \"assigned_to\":\"Ryan McCarthy\", \"assignment_group\":\"VH Sysadmin\", \"caller_id\":\"Ryan McCarthy\",\"priority\":\"4\", \"short_description\":\"this is a test\" }";
        			se = new StringEntity(jsonobj);
        			CreateLabel.connectionAlertDialog.setMessage(jsonobj);
        			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));                     
     
        			post.setEntity(se);                     
        			response = client.execute(post);                     
        			/*Checking response */                     
       /* 			if(response!=null){  
        				InputStream in = response.getEntity().getContent(); //Get the data in the entity   
        				//in.close();
        				//CreateLabel.connectionAlertDialog.setMessage(response.getEntity().getContent().toString());
        			}                 
        			else{
        			//	CreateLabel.connectionAlertDialog.setMessage("NULL RETURNED");
        				
        			}
    				CreateLabel.connectionAlertDialog.show();

        		}
        		catch(Exception e){                     
        			e.printStackTrace();   
        			CreateLabel.connectionAlertDialog.setMessage(e.toString());
        			CreateLabel.connectionAlertDialog.show();
              
        		}                 
        		Looper.loop(); //Loop in the message queue             
        		}         
        	};         
       	t.start();          
	}*/
    
	public String retrieveDateCreated(){
		return null;
	}
	
}
