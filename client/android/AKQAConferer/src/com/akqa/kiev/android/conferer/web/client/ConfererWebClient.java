package com.akqa.kiev.android.conferer.web.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.akqa.kiev.android.conferer.utils.IoUtils;

/**
 * Web client for Conferer App.
 * @author Yuriy.Belelya
 *
 */
public class ConfererWebClient {

	private static final String CONFERENCES_URL = "http://localhost:8080/conferer/conferences";
	private static final String CONFERENCES_YEAR_PARAM = "year";
	private static final String CONFERENCES_MONTH_PARAM = "month";

	private DefaultHttpClient httpClient;

	public ConfererWebClient() {
		httpClient = new DefaultHttpClient();
	}

	public String getCurrentMonthConferences() {
		return simpleGetRequest(CONFERENCES_URL);
	}

	public String getConferences(int year, int month) {
		StringBuilder url = new StringBuilder(CONFERENCES_URL);
		url.append("?").append(CONFERENCES_YEAR_PARAM).append("=").append(year);
		url.append("&").append(CONFERENCES_MONTH_PARAM).append("=")
				.append(month);
		return simpleGetRequest(url.toString());
	}

	private String simpleGetRequest(String url) {
		String answer = null;
		HttpGet homeRequest = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(homeRequest);
			return getHtmlAnswer(response);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(getClass().getSimpleName(), e.getMessage());
		} finally {
			releaseConnection(homeRequest);
		}
		return answer;
	}

	/**
	 * Release request connection
	 * 
	 * @param request
	 */
	private void releaseConnection(HttpRequestBase request) {
		request.abort();
		httpClient.getConnectionManager().closeExpiredConnections();
	}

	/**
	 * Parse string html response from HttpResoponse
	 * 
	 * @param response
	 * @return - string html response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String getHtmlAnswer(HttpResponse response)
			throws IllegalStateException, IOException {
		InputStream content = response.getEntity().getContent();
		return IoUtils.getStringDataFromStream(content);
	}

	/**
	 * Setter for proxy
	 * 
	 * @param host
	 *            - proxy host
	 * @param port
	 *            - proxy port
	 */
	public void setProxy(String host, int port) {
		HttpHost proxy = new HttpHost(host, port);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}
}
