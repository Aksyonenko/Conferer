package com.akqa.kiev.android.conferer.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.SpeakerDetailsFragmentListener;
import com.akqa.kiev.android.conferer.model.SocialLinksData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;
import com.androidquery.AQuery;

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
		updateSocialLinks(speakerData.getSocialLinks());
	}
	
	public void updateSocialLinks(SocialLinksData data) {
		ImageView facebookImageView = (ImageView) getView().findViewById(R.id.speaker_details_facebook_logo);
		ImageView linkedInImageView = (ImageView) getView().findViewById(R.id.speaker_details_linkedin_logo);
		ImageView twitterImageView = (ImageView) getView().findViewById(R.id.speaker_details_twitter_logo);
		final String facebookLink = data.getFacebook();
		if(facebookLink != null && !facebookLink.isEmpty()) {
			facebookImageView.setVisibility(View.VISIBLE);
			facebookImageView.setOnClickListener(new OnSocialLinkclickListener(facebookLink));
		} else {
			facebookImageView.setVisibility(View.GONE);
		}
		
		final String linkedInLink = data.getLinkedin();
		if(linkedInLink != null && !linkedInLink.isEmpty()) {
			linkedInImageView.setVisibility(View.VISIBLE);
			linkedInImageView.setOnClickListener(new OnSocialLinkclickListener(linkedInLink));
		} else {
			linkedInImageView.setVisibility(View.GONE);
		}
		
		final String twitterLink = data.getTwitter();
		if(twitterLink != null && !twitterLink.isEmpty()) {
			twitterImageView.setVisibility(View.VISIBLE);
			twitterImageView.setOnClickListener(new OnSocialLinkclickListener(twitterLink));
		} else {
			twitterImageView.setVisibility(View.GONE);
		}
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
	
	private class OnSocialLinkclickListener implements OnClickListener {

		private String url;
		
		public OnSocialLinkclickListener(String url) {
			this.url = url;
		}
		
		@Override
		public void onClick(View v) {
			Intent viewProfileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(viewProfileIntent);
		}
		
	}
	
}
