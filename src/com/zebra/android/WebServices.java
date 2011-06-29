package com.zebra.android;



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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


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
		request.addProperty("serial_number", serialNumber);
		
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
	}
		
   
    
	public String retrieveDateCreated(){
		return null;
	}
	
}
