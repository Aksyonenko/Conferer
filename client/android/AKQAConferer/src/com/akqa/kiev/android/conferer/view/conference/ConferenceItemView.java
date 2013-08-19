package com.akqa.kiev.android.conferer.view.conference;

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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.ConferenceDetailsActivity;
import com.akqa.kiev.android.conferer.TypefaceRegistry;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.task.DownloadImageTask;

/**
 * View with conference
 * 
 * @author Yuriy.Belelya
 * 
 */
public class ConferenceItemView extends TableLayout implements OnClickListener {

	private ConferenceData data;

	private SimpleDateFormat dateFormat;

	private List<AsyncTask<?, ?, ?>> asyncTasks = new ArrayList<AsyncTask<?, ?, ?>>();

	private static final int IMG_WIDTH = 150;
	private static final int IMG_HEIGHT = 100;

	public ConferenceItemView(Context context) {
		this(context, null);
	}

	public ConferenceItemView(Context context, ConferenceData conferenceData) {
		super(context);
		dateFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.data = conferenceData;
		init(context);
		setOnClickListener(this);
	}

	private void init(Context context) {
		if (data != null) {
			setPadding(10, 10, 10, 10);
			//setStretchAllColumns(true);
			setColumnShrinkable(1, true);
			setGravity(Gravity.CENTER);

			TableRow titleRow = new TableRow(context);

			titleRow.addView(createTitle(context, data.getTitle()));

			TableRow imgAndDescRow = new TableRow(context);

			imgAndDescRow.addView(createcConferenceLogo(context,
					data.getLogoUrl()));

			imgAndDescRow.addView(createSummary(context, data.getSummary()));

			TableRow datesAndLocationRow = new TableRow(context);

			datesAndLocationRow.addView(createDates(context,
					data.getStartDate(), data.getEndDate()));

			datesAndLocationRow.addView(createLocation(context,
					data.getCountry(), data.getCity()));

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

	private ImageView createcConferenceLogo(Context context, String url) {
		ImageView conferenceLogo = new ImageView(context);
		conferenceLogo.setMaxHeight(IMG_HEIGHT);
		conferenceLogo.setMaxWidth(IMG_WIDTH);
		conferenceLogo.setMinimumHeight(IMG_HEIGHT);
		conferenceLogo.setMinimumWidth(IMG_WIDTH);
		conferenceLogo.setAdjustViewBounds(true);
		asyncTasks.add(new DownloadImageTask(conferenceLogo).execute(url));
		return conferenceLogo;
	}

	private TextView createSummary(Context context, String summary) {
		TextView summaryView = new TextView(context);
		summaryView.setPadding(10, 0, 10, 0);
		summaryView.setText(summary);
		summaryView.setMaxLines(4);
		summaryView.setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.COND));
		return summaryView;
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

	private TextView createLocation(Context context, String country, String city) {
		TextView locationView = new TextView(context);
		locationView.setText(country + ", " + city);
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
		bundle.putSerializable(ConferenceDetailsActivity.CONFERENCE_ID_ARG,
				data.getId());
		Intent intent = new Intent(context, ConferenceDetailsActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
