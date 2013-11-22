package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.web.ConfererService;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ConferenceDetailActivity extends FragmentActivity {
	
	private ConfererService confererService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conference_details);
	}
}
