package com.zebra.android;

/**
 * Copyright (c) 2003,2004, Stefan Haustein, Oberhausen, Rhld., Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 * Contributor(s): John D. Beatty, Dave Dash, F. Hunter, Alexander Krebs,
 * Lars Mehrmann, Sean McDaniel, Thomas Strang, Renaud Tognelli
 * */


import java.util.List;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.ksoap2.*;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.*;

/**
 * A J2SE based HttpTransport layer.
 */
public class HttpTransportSE extends Transport {

	private ServiceConnection connection;

	/**
	 * Creates instance of HttpTransportSE with set url
	 *
	 * @param url
	 * the destination to POST SOAP data
	 */
	public HttpTransportSE(String url) {
		super(null, url);
	}

	/**
	 * Creates instance of HttpTransportSE with set url and defines a
	 * proxy server to use to access it
	 *
	 * @param proxy
	 * Proxy information or <code>null</code> for direct access
	 * @param url
	 * The destination to POST SOAP data
	 */
	public HttpTransportSE(Proxy proxy, String url) {
		super(proxy, url);
	}

	/**
	 * Creates instance of HttpTransportSE with set url
	 *
	 * @param url
	 * the destination to POST SOAP data
	 * @param timeout
	 * timeout for connection and Read Timeouts (milliseconds)
	 */
	public HttpTransportSE(String url, int timeout) {
		super(url, timeout);
	}

	/**
	 * set the desired soapAction header field
	 *
	 * @param soapAction
	 * the desired soapAction
	 * @param envelope
	 * the envelope containing the information for the soap call.
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public void call(String soapAction, SoapEnvelope envelope) throws IOException, XmlPullParserException {

		call(soapAction, envelope, null);
	}

	/**
	 *
	 * set the desired soapAction header field
	 *
	 * @param soapAction
	 * the desired soapAction
	 * @param envelope
	 * the envelope containing the information for the soap call.
	 * @param headers
	 * a list of HeaderProperties to be http header properties when establishing the connection
	 *
	 * @return <code>CookieJar</code> with any cookies sent by the server
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public List call(String soapAction, SoapEnvelope envelope, List headers)
	throws IOException, XmlPullParserException {

		if (soapAction == null)
			soapAction = "\"\"";

		byte[] requestData = createRequestData(envelope);

		requestDump = debug ? new String(requestData) : null;
		responseDump = null;

		connection = getServiceConnection();

		
		connection.setRequestProperty("User-Agent", "kSOAP/2.0");
		connection.setRequestProperty("SOAPAction", soapAction);
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("Connection", "close");
		connection.setRequestProperty("Content-Length", "" + requestData.length);
		
		// Pass the headers provided by the user along with the call
		if (headers != null) {
			for (int i = 0; i < headers.size(); i++) {
				HeaderProperty hp = (HeaderProperty) headers.get(i);
				connection.setRequestProperty(hp.getKey(), hp.getValue());
			}
		}
		

		
		connection.setRequestMethod("POST");
		connection.connect();


		OutputStream os = connection.openOutputStream();

		os.write(requestData, 0, requestData.length);
		os.flush();
		os.close();
		requestData = null;
		InputStream is;
		List retHeaders = null;

		try {
			connection.connect();
			is = connection.openInputStream();
			retHeaders = connection.getResponseProperties();
		} catch (IOException e) {
			is = connection.getErrorStream();

			if (is == null) {
				connection.disconnect();
				throw (e);
			}
		}

		if (debug) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[256];

			while (true) {
				int rd = is.read(buf, 0, 256);
				if (rd == -1)
					break;
				bos.write(buf, 0, rd);
			}

			bos.flush();
			buf = bos.toByteArray();
			responseDump = new String(buf);
			is.close();
			is = new ByteArrayInputStream(buf);
		}

		parseResponse(envelope, is);
		return retHeaders;
	}

	public ServiceConnection getConnection() {
		return (ServiceConnectionSE) connection;
	}

	protected ServiceConnection getServiceConnection() throws IOException {
		return new ServiceConnectionSE(proxy, url);
	}

	public String getHost() {

		String retVal = null;

		try {
			retVal = new URL(url).getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public int getPort() {

		int retVal = -1;

		try {
			retVal = new URL(url).getPort();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public String getPath() {

		String retVal = null;

		try {
			retVal = new URL(url).getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return retVal;
	}
}