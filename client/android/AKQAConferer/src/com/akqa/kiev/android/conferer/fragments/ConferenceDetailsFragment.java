package com.akqa.kiev.android.conferer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class ConferenceDetailsFragment extends Fragment {
	private ConfererService confererService;
	private Long conferenceId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererService();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_conference_details, container, false);
		return view;
	}
}
