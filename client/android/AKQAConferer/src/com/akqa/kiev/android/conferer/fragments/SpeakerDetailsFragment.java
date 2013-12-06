package com.akqa.kiev.android.conferer.fragments;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.SpeakerDetailsFragmentListener;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.androidquery.AQuery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerDetailsFragment extends Fragment {
	
	private ConfererService confererService;
	private SpeakerDetailsFragmentListener speakerListener;
	private Long speakerId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
		speakerListener = (SpeakerDetailsFragmentListener)getActivity();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_speaker_details, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		speakerListener.onSpeakerDetailsFragmentStart(this);
		
	}
	
	private void updateViews(SpeakerData speakerData) {
		TextView speakerName = (TextView) getView().findViewById(R.id.speaker_details_name);
		speakerName.setText(speakerData.getFirstName() + " " + speakerData.getLastName());
		TextView speakerSubtitle = (TextView) getView().findViewById(R.id.speaker_details_subtitle);
		speakerSubtitle.setText(speakerData.getCompetence());
		ImageView speakerImage = (ImageView) getView().findViewById(R.id.speaker_details_image);
		if(speakerImage != null) {
			AQuery aq = new AQuery(getView());
			aq.id(R.id.speaker_details_image).image(speakerData.getPhotoUrl());
		}
		TextView speakerBio = (TextView) getView().findViewById(R.id.speaker_details_bio);
		speakerBio.setText(speakerData.getAbout());
	}
	
	public void setSpeakerId(Long speakerId) {
		this.speakerId = speakerId;
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, SpeakerData> {

		@Override
		protected SpeakerData doInBackground(Void... params) {
			return confererService.loadSpeakereDetails(speakerId);
		}
		
		@Override
		protected void onPostExecute(SpeakerData result) {
			updateViews(result);
		}
		
	}
}
