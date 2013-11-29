package com.akqa.kiev.android.conferer;

import com.akqa.kiev.android.conferer.fragments.SessionDetailsFragment;
import com.akqa.kiev.android.conferer.service.ConfererDbService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.akqa.kiev.android.conferer.utils.Constants;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SessionDetailsActivity extends FragmentActivity implements OnSessionDetailsFragmentStartedListenter {
	private boolean isTwoPane = false;
	private ConfererWebService confererService;
	private ConfererDbService cs;
	Long sessionId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_session_details);
		cs = new ConfererDbService(getApplicationContext());
		sessionId = getIntent().getLongExtra(Constants.BUNDLE_SESSION_ID, 0L);
	}

	@Override
	public void onSessionDetailsFragmentStarted(SessionDetailsFragment fragment) {
		fragment.setSessionId(sessionId);
	}
}
