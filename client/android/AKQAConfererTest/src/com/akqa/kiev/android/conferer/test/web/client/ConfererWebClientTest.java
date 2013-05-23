package com.akqa.kiev.android.conferer.test.web.client;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.akqa.kiev.android.conferer.web.client.ConfererWebClient;

public class ConfererWebClientTest extends TestCase {

	private ConfererWebClient client;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		client = new ConfererWebClient();
	}

	public void testGetConferences() {
		String conferences = client.getConferences(2013, 1);
		Assert.assertNotNull(conferences);
	}
}
