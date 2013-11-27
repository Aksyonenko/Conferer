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
import com.akqa.kiev.android.conferer.utils.LogUtils;

/**
 * Web client for Conferer App.
 * 
 * @author Yuriy.Belelya
 * 
 */
public class ConfererWebClient {

	private static final String CONFERENCES_URL = "http://10.11.100.254/conferences";
	private static final String ALL_CONFERENCES_URL = "http://10.11.100.254/conferences/all";

	private static final String CONFERENCES_YEAR_PARAM = "year";
	private static final String CONFERENCES_MONTH_PARAM = "month";
	private static final String CONFERENCES_MONTHS_URL = "http://10.11.100.254/conferences/months";
	private static final String SESSIONS_URL = "http://10.11.100.254/sessions";
	private static final String SPEAKERS_URL = "http://10.11.100.254/speakers";

	private DefaultHttpClient httpClient;

	public ConfererWebClient() {
		httpClient = new DefaultHttpClient();
	}

	public String getCurrentMonthConferences() {
		return getRequest(CONFERENCES_URL);
	}

	public String getAllconferencesMonths() {
		return getRequest(CONFERENCES_MONTHS_URL);
	}

	public String getConferenceDetails(long id) {
		return getRequest(CONFERENCES_URL + "/" + id);
	}

	public String getSessionDetails(long id) {
		return getRequest(SESSIONS_URL + "/" + id);
	}

	public String getSpeakerDetails(long id) {
		return getRequest(SPEAKERS_URL + "/" + id);
	}

	public String getConferences(int year, int month) {
		StringBuilder url = new StringBuilder(CONFERENCES_URL);
		url.append("?").append(CONFERENCES_YEAR_PARAM).append("=").append(year);
		url.append("&").append(CONFERENCES_MONTH_PARAM).append("=")
				.append(month);
		return getRequest(url.toString());
	}
	
	public String getAllConferences() {
		return getRequest(ALL_CONFERENCES_URL);
	}

	private String getRequest(String url) {
		HttpGet request = new HttpGet(url);
		try {
			Log.i(getClass().getName(), "Executing request: " + url);
			HttpResponse response = httpClient.execute(request);
			return getHtmlAnswer(response);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		} finally {
			releaseConnection(request);
		}
		return null;
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
