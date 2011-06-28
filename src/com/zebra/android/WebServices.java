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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;


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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

public class WebServices {
	private String url; 
	private int timeout;
	//private HttpParams httpParams;
	//private HttpClient client;
	private String authorizationString;
	private final String NAMESPACE = "http://www.service-now.com/imp_computer/";
	private final String METHOD_NAME = "insert";
	private final String URL = "https://testzebra.service-now.com/imp_computer.do?SOAP";
	private final String SOAP_ACTION = "http://www.service-now.com/imp_computer/insert";
	public WebServices(){

		String createUrl = "https://testzebra.service-now.com/incident.do?JSON&sysparm_action=insert";
		timeout = 10000;
		/*String val = (new StringBuffer("username").append(":").append("password")).toString();
		byte[] base = val.getBytes();*/
		//authorizationString = "Basic " + (Base64.encode("bcraig:chicago".getBytes(), 0));
		

	}

	
	public void createAsset(String serialNumber){
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("serial_number", "1234560");
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet=false;
		envelope.setOutputSoapObject(request);        
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		androidHttpTransport.debug=true;
		//androidHttpTransport.setXmlVersionTag("1.0");

		//Prepare the header with the authentication data.
		//Element header = new Element().createElement(NAMESPACE, "Authorization: Basic YmNyYWlnOmNoaWNhZ28=");

		//add header to envelope
		//envelope.headerOut = new Element[]{header};
		
		
		try{
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object result = (Object)envelope.getResponse();
			String resultData = result.toString();
			
			CreateLabel.connectionAlertDialog.setMessage(resultData);
			CreateLabel.connectionAlertDialog.show();
		}
		catch(Exception e){
			CreateLabel.connectionAlertDialog.setMessage(e.toString());
			CreateLabel.connectionAlertDialog.show();
		}
		
        
		
		
		/*     JSON STuFF
         * 
         *
		
		
		//final String sn = serialNumber;

		/*createUrl = "https://testzebra.service-now.com/imp_computer.do?JSON";
		//createUrl = "https://testzebra.service-now.com/cmdb_ci_computer.do?sysparm_action=insert&JSON";
		timeout = 10000;
		/*String val = (new StringBuffer("username").append(":").append("password")).toString();
		byte[] base = val.getBytes();*/
		//authorizationString = "Basic " + (Base64.encode("bcraig:chicago".getBytes(), 0));
		//String encoding = "bcraig:chicago";
		

         /* Thread t = new Thread(){         
  
        	public void run() {                 
        		Looper.prepare(); //For Preparing Message Pool for the child Thread                 
        		
        		//DefaultHttpClient client = new DefaultHttpClient();        
        		//HttpConnectionParams.setConnectionTimeout(client.getParams(), timeout); //Timeout Limit           
        		//client.getCredentialsProvider().setCredentials(new AuthScope(null, -1), new UsernamePasswordCredentials("BCRAIG:chicago"));   		  		
        		
        		//HttpResponse response;   
        		JSONObject json = new JSONObject();    
        		JSONObject outside = new JSONObject();
        		try{                     
        		
        			URL url = new URL(createUrl);
        			String charset = "UTF-8"; 
        			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        			connection.setDoOutput(true);
        			connection.setRequestMethod("POST");
        			connection.setRequestProperty("Accept-Charset", charset);
                    connection.setRequestProperty("Content-Type", "application/json");
                   	connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        			//connection.setRequestProperty ("Authorization", authorizationString);
        			
        			//InputStream in = url.openStream();
        			//String jsonobj = "{\"records\":[{\"assigned_to\":\"Ryan McCarthy\", \"assignment_group\":\"VH Sysadmin\", \"caller_id\":\"Ryan McCarthy\",\"priority\":\"4\", \"short_description\":\"this is a test\" }]}";
        			
                    String jsonobj = "{\"records\":[{\"asset_tag\":\"\",\"assigned\":\"\",\"assigned_to\":\"\",\"attributes\":\"\",\"can_print\":\"false\",\"category\":\"Hardware\",\"cd_rom\":\"false\",\"cd_speed\":\"0\",\"change_control\":\"\",\"checked_in\":\"\",\"checked_out\":\"\",\"comments\":\"\",\"company\":\"\",\"correlation_id\":\"\",\"cost\":\"0\",\"cost_cc\":\"USD\",\"cost_center\":\"\",\"cpu_core_count\":\"1\",\"cpu_count\":\"1\",\"cpu_manufacturer\":\"\",\"cpu_name\":\"\",\"cpu_speed\":\"0\",\"cpu_type\":\"\",\"delivery_date\":\"\",\"department\":\"\",\"discovery_source\":\"\",\"disk_space\":\"0\",\"dns_domain\":\"\",\"due\":\"\",\"due_in\":\"\",\"fault_count\":\"0\",\"first_discovered\":\"\",\"floppy\":\"\",\"form_factor\":\"\",\"gl_account\":\"\",\"hardware_status\":\"installed\",\"hardware_substatus\":\"\",\"install_date\":\"\",\"install_status\":\"1\",\"invoice_number\":\"\",\"ip_address\":\"\",\"justification\":\"\",\"last_discovered\":\"\",\"lease_id\":\"\",\"location\":\"\",\"mac_address\":\"\",\"maintenance_schedule\":\"\",\"managed_by\":\"\",\"manufacturer\":\"\",\"model_id\":\"\",\"model_number\":\"\",\"monitor\":\"false\",\"name\":\"\",\"operational_status\":\"1\",\"order_date\":\"\",\"os\":\"\",\"os_address_width\":\"0\",\"os_domain\":\"\",\"os_service_pack\":\"\",\"os_version\":\"\",\"owned_by\":\"\",\"po_number\":\"\",\"purchase_date\":\"\",\"ram\":\"0\",\"repair_contract_id\":\"\",\"returned_from_repair\":\"\",\"schedule\":\"\",\"sent_for_repair\":\"\",\"serial_number\":\"1234567\",\"short_description\":\"\",\"start_date\":\"\",\"subcategory\":\"Computer\",\"support_group\":\"\",\"supported_by\":\"\",\"sys_class_name\":\"cmdb_ci_computer\",\"sys_created_by\":\"BCraig\",\"sys_created_on\":\"2011-06-24 \",\"sys_domain\":\"global\",\"sys_id\":\"bf2ac494ff630400bb829f1555c4adaf\",\"sys_mod_count\":\"0\",\"sys_updated_by\":\"BCraig\",\"sys_updated_on\":\"2011-06-24 19:03:51\",\"u_buyout_account_no_\":\"\",\"u_buyout_amount\":\"\",\"u_buyout_invoice_no_\":\"\",\"u_date_picked_up_by_dell\":\"\",\"u_date_police_report\":\"\",\"u_date_removed_dell_lar\":\"\",\"u_date_reported_stolen_lost\":\"\",\"u_date_to_is\":\"\",\"u_dell_rma_no_\":\"\",\"u_located\":\"\",\"u_monitor_i\":\"\",\"u_monitor_ii\":\"\",\"vendor\":\"\",\"virtual\":\"false\",\"warranty_expiration\":\"\"}]}" ;
                    json.put("assignment_group","VH Sysadmin");
        			json.put("caller_id","Benjamin Craig");
        			json.put("short_description","TEST TICKET");
        			
        			outside.put("records", json);
        			
                    //OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

        			String authStringEnc = "YmNyYWlnOmNoaWNhZ28="; //encoded bcraig:chicago
        			System.out.println("Base64 encoded auth string: " + authStringEnc);

        			BufferedOutputStream output = new BufferedOutputStream(connection.getOutputStream());
        			//OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        			InputStream is = connection.getInputStream();
        			InputStreamReader isr = new InputStreamReader(is);
 


        			
        			output.write(outside.toString().getBytes(charset));
        			//writer.write(jsonobj.getBytes());
        			
        			
        			
        			int numCharsRead;
        			char[] charArray = new char[1024];
        			StringBuffer sb = new StringBuffer();
        			while ((numCharsRead = isr.read(charArray)) > 0) {
        				sb.append(charArray, 0, numCharsRead);
        			}
        			String result = sb.toString();
        			/*charArray = new char[1024];
        			sb = new StringBuffer();
        			while ((numCharsRead = isr.read(charArray)) > 0) {
        				sb.append(charArray, 0, numCharsRead);
        			}
        			result = sb.toString();*/
        			
        			/*int status = ((HttpURLConnection) connection).getResponseCode();

        			result = jsonobj;
        			CreateLabel.connectionAlertDialog.setMessage(status+"");
        			CreateLabel.connectionAlertDialog.show();
        			
///////////////////////////////////////////////////se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));                     
	
                    output.write(jsonobj.getBytes());
                    
                    /*if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    	// OK  
                    	CreateLabel.connectionAlertDialog.setMessage("Server responded HTTP_OK");
            			CreateLabel.connectionAlertDialog.show();
                    	
                    } 
                    else {
                    		// Server returned HTTP error code. 
                    	CreateLabel.connectionAlertDialog.setMessage(connection.getResponseCode()+"");
                		CreateLabel.connectionAlertDialog.show();
                   	}  */
                    /*output.close();
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
        t.start();     */     
    }
        			
        		/* *********RANDOM ********************** */	
        			
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
