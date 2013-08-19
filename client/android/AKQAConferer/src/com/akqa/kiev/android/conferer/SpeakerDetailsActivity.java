package com.akqa.kiev.android.conferer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.task.DownloadImageTask;
import com.akqa.kiev.android.conferer.view.social.SocialPanel;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class SpeakerDetailsActivity extends Activity {

	private ConfererService confererService;

	public static String SPEAKER_ID_ARG = "speakerId";

	private List<AsyncTask<?, ?, ?>> asyncTasks = new ArrayList<AsyncTask<?, ?, ?>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_speaker_details);
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}
		View searchView = findViewById(R.id.search_view);
		searchView.setVisibility(View.INVISIBLE);
		confererService = new ConfererService();

		initView();
	}

	private void initView() {
		Long speakerId = (Long) getIntent()
				.getSerializableExtra(SPEAKER_ID_ARG);

		SpeakerData speakerDetails = confererService
				.loadSpeakereDetails(speakerId);
		Context context = getApplicationContext();

		TextView titleView = (TextView) findViewById(R.id.speaker_title);
		titleView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		titleView.setText(speakerDetails.getFirstName() + " "
				+ speakerDetails.getLastName());

		TextView competenceView = (TextView) findViewById(R.id.speaker_competence);
		competenceView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		competenceView.setText(speakerDetails.getCompetence());

		ImageView photoView = (ImageView) findViewById(R.id.speaker_photo);
		asyncTasks.add(new DownloadImageTask(photoView).execute(speakerDetails
				.getPhotoUrl()));

		TextView aboutView = (TextView) findViewById(R.id.speaker_about);
		aboutView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.COND));
		aboutView.setText(speakerDetails.getAbout());
		
		SocialPanel socialPanel = (SocialPanel)findViewById(R.id.speaker_social);
		socialPanel.init(speakerDetails.getSocialLinks());
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (AsyncTask<?, ?, ?> asyncTask : asyncTasks) {
			if (!asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		asyncTasks.clear();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
