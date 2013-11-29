package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SessionDetailsActivity extends FragmentActivity {
	private boolean isTwoPane = false;
	private ConfererWebService confererService;
	private ConfererDbService cs;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_session_details);
		cs = new ConfererDbService(getApplicationContext());
	}
}
