package com.akqa.kiev.android.conferer.fragments;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SessionDetailsFragment extends Fragment {
	private ConfererService confererService;
	private SessionData sessionData;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_session_details, container, false);
		return view;
	}
}
