package com.akqa.kiev.android.conferer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.task.DownloadImageTask;
import com.akqa.kiev.android.conferer.web.ConfererService;

public class SessionDetailsActivity extends Activity {

	private ConfererService confererService;

	public static String SESSION_ID_ARG = "sessionId";

	private static final int IMG_WIDTH = 200;
	private static final int IMG_HEIGHT = 150;

	private SimpleDateFormat dateFormat;
	private SimpleDateFormat timeFormat;

	private List<AsyncTask<?, ?, ?>> asyncTasks = new ArrayList<AsyncTask<?, ?, ?>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_session_details);
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.titlebar);
		}
		confererService = new ConfererService();

		dateFormat = new SimpleDateFormat("dd MMM yyyy, EEEE", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
		timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		initView();
	}

	private void initView() {
		Long sessionId = (Long) getIntent()
				.getSerializableExtra(SESSION_ID_ARG);

		SessionData sessionDetails = confererService
				.loadSessionDetails(sessionId);

		Context context = getApplicationContext();
		TextView titleView = (TextView) findViewById(R.id.session_title);
		titleView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		titleView.setText(sessionDetails.getTitle());

		TextView detailsView = (TextView) findViewById(R.id.session_details);
		detailsView.setTypeface(TypefaceRegistry.getTypeFace(
				getApplicationContext(), TypefaceRegistry.COND));
		String details = sessionDetails.getDetails() != null ? sessionDetails
				.getDetails() : sessionDetails.getSummary();
		detailsView.setText(details);

		LinearLayout infoPanel = (LinearLayout) findViewById(R.id.session_info_panel);
		infoPanel.addView(createSessionLogo(context,
				sessionDetails.getSessionLogoUrl()));
		infoPanel.addView(createSessionInfo(context, sessionDetails));
	}

	private TextView createSessionInfo(Context context, SessionData sessionData) {
		StringBuilder sb = new StringBuilder();
		TextView infoTextView = new TextView(context);
		infoTextView.setPadding(10, 0, 0, 0);
		List<SpeakerData> speakers = sessionData.getSpeakers();
		String lineSeparator = System.getProperty("line.separator");
		if (speakers.size() > 1) {
			sb.append("Speakers: ");
		} else {
			sb.append("Speaker: ");
		}
		sb.append(" ");
		for (SpeakerData speakerData : speakers) {
			sb.append(speakerData.getFirstName()).append(" ")
					.append(speakerData.getLastName()).append(lineSeparator);
		}
		sb.append("Day: ")
				.append(dateFormat.format(sessionData.getStartTime()))
				.append(lineSeparator);
		sb.append("Time: ")
				.append(timeFormat.format(sessionData.getStartTime()))
				.append(" - ")
				.append(timeFormat.format(sessionData.getEndTime()))
				.append(lineSeparator);
		sb.append("Location: ").append(sessionData.getLocation());
		infoTextView.setText(sb.toString());
		infoTextView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		infoTextView.setTextColor(Color.BLACK);
		return infoTextView;
	}

	private ImageView createSessionLogo(Context context, String url) {
		ImageView sessionLogo = new ImageView(context);
		sessionLogo.setMaxHeight(IMG_HEIGHT);
		sessionLogo.setMaxWidth(IMG_WIDTH);
		sessionLogo.setMinimumHeight(IMG_HEIGHT);
		sessionLogo.setMinimumWidth(IMG_WIDTH);
		sessionLogo.setAdjustViewBounds(true);
		asyncTasks.add(new DownloadImageTask(sessionLogo).execute(url));
		return sessionLogo;
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
