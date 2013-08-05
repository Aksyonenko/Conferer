package com.akqa.kiev.android.conferer.view.conference.details;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.akqa.kiev.android.conferer.SessionDetailsActivity;
import com.akqa.kiev.android.conferer.SpeakerDetailsActivity;
import com.akqa.kiev.android.conferer.TypefaceRegistry;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.akqa.kiev.android.conferer.task.DownloadImageTask;

public class SessionItemView extends TableLayout implements OnClickListener {

	private SessionData data;

	private SimpleDateFormat dateFormat;

	private List<AsyncTask<?, ?, ?>> asyncTasks = new ArrayList<AsyncTask<?, ?, ?>>();

	private static final int IMG_WIDTH = 150;
	private static final int IMG_HEIGHT = 100;

	public SessionItemView(Context context) {
		this(context, null);
	}

	public SessionItemView(Context context, SessionData session) {
		super(context);
		dateFormat = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.data = session;

		init(context);
		setOnClickListener(this);
	}

	private void init(Context context) {
		if (data != null) {
			setPadding(10, 10, 10, 10);
			setStretchAllColumns(true);
			setColumnShrinkable(1, true);
			setGravity(Gravity.CENTER);

			TableRow titleRow = new TableRow(context);

			titleRow.addView(createTitle(context, data.getTitle()));

			TableRow imgAndDescRow = new TableRow(context);

			imgAndDescRow.addView(createcSessionLogo(context,
					data.getSessionLogoUrl()));

			imgAndDescRow.addView(createOverview(context, data.getSpeakers()));

			TableRow datesAndLocationRow = new TableRow(context);

			datesAndLocationRow.addView(createDates(context,
					data.getStartTime(), data.getEndTime()));

			datesAndLocationRow.addView(createLocation(context,
					data.getLocation()));

			addView(titleRow);
			addView(imgAndDescRow);
			addView(datesAndLocationRow);
		}
	}

	private TextView createTitle(Context context, String title) {
		TextView titleView = new TextView(context);
		titleView.setText(title);
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 2;
		titleView.setLayoutParams(params);
		titleView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		return titleView;
	}

	private ImageView createcSessionLogo(Context context, String url) {
		ImageView sessionLogo = new ImageView(context);
		sessionLogo.setMaxHeight(IMG_HEIGHT);
		sessionLogo.setMaxWidth(IMG_WIDTH);
		sessionLogo.setMinimumHeight(IMG_HEIGHT);
		sessionLogo.setMinimumWidth(IMG_WIDTH);
		sessionLogo.setAdjustViewBounds(true);
		asyncTasks.add(new DownloadImageTask(sessionLogo).execute(url));
		return sessionLogo;
	}

	private TextView createOverview(Context context, List<SpeakerData> speakers) {
		TextView overviewView = new TextView(context);
		overviewView.setPadding(10, 0, 10, 0);
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();

		for (SpeakerData speakerData : speakers) {
			sb.append(speakerData.getFirstName()).append(" ")
					.append(speakerData.getLastName()).append(lineSeparator)
					.append(speakerData.getCompetence()).append(lineSeparator);
		}
		String text = sb.toString();
		SpannableString spannableString = new SpannableString(text);

		int start = 0;
		for (final SpeakerData speakerData : speakers) {
			int length = speakerData.getFirstName().length()
					+ speakerData.getLastName().length() + 1;
			spannableString.setSpan(new RelativeSizeSpan(1.2f), start, start
					+ length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			spannableString.setSpan(new ClickableSpan() {

				@Override
				public void onClick(View widget) {
					Context context = getContext();
					Bundle bundle = new Bundle();
					bundle.putSerializable(
							SpeakerDetailsActivity.SPEAKER_ID_ARG,
							speakerData.getId());
					Intent intent = new Intent(context,
							SpeakerDetailsActivity.class);
					intent.putExtras(bundle);
					context.startActivity(intent);

				}
			}, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			overviewView.setText(spannableString, BufferType.SPANNABLE);
			start += length + speakerData.getCompetence().length() + 2;
		}
		overviewView.setMovementMethod(LinkMovementMethod.getInstance());
		overviewView.setMaxLines(4);
		overviewView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.COND));
		return overviewView;
	}

	private TextView createDates(Context context, Date startDate, Date endDate) {
		String start = dateFormat.format(startDate);
		String end = dateFormat.format(endDate);
		TextView datesView = new TextView(context);
		datesView.setText(start + " - " + end);
		datesView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		return datesView;
	}

	private TextView createLocation(Context context, String location) {
		TextView locationView = new TextView(context);
		locationView.setText(location);
		locationView.setPadding(10, 10, 10, 10);
		locationView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));
		return locationView;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (AsyncTask<?, ?, ?> asyncTask : asyncTasks) {
			if (!asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		asyncTasks.clear();
	}

	@Override
	public void onClick(View v) {
		Context context = getContext();
		Bundle bundle = new Bundle();
		bundle.putSerializable(SessionDetailsActivity.SESSION_ID_ARG,
				data.getId());
		Intent intent = new Intent(context, SessionDetailsActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

}
