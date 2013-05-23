package com.akqa.kiev.android.conferer.view.conference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.TypefaceRegistry;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.akqa.kiev.android.conferer.task.DownloadImageTask;

/**
 * View with conference
 * 
 * @author Yuriy.Belelya
 * 
 */
public class ConferenceView extends TableLayout {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM",
			Locale.getDefault());

	List<AsyncTask<?, ?, ?>> asyncTasks = new ArrayList<AsyncTask<?, ?, ?>>();

	public ConferenceView(Context context) {
		this(context, null);
	}

	public ConferenceView(Context context, ConferenceData conferenceData) {
		super(context);
		init(context, conferenceData);
	}

	private void init(Context context, ConferenceData data) {
		// if (data != null) {
		setPadding(10, 10, 10, 10);
		setStretchAllColumns(true);
		setColumnShrinkable(1, true);
		setGravity(Gravity.CENTER);

		TableRow titleRow = new TableRow(context);

		// TODO stub, will be removed later
		titleRow.addView(createTitle(context, "Dota 2 International 2013"));

		TableRow imgAndDescRow = new TableRow(context);

		// TODO stub, will be removed later
		imgAndDescRow
				.addView(createcConferenceLogo(context,
						"http://prodota.ru/forum/uploads/profile/photo-59266.png?_r=1354107901"));

		// TODO stub, will be removed later
		imgAndDescRow
				.addView(createSummary(
						context,
						"The International is an annual electronic sports Dota 2 championship tournament hosted by the American video game developer Valve Corporation, in which sixteen teams are personally invited to compete."));

		TableRow datesAndLocationRow = new TableRow(context);
		// TODO stub, will be removed later

		datesAndLocationRow
				.addView(createDates(context, new Date(), new Date()));

		// TODO stub, will be removed later
		datesAndLocationRow.addView(createLocation(context, "Ukraine", "Kiev"));

		addView(titleRow);
		addView(imgAndDescRow);
		addView(datesAndLocationRow);
		// }
	}

	private TextView createTitle(Context context, String title) {
		TextView titleView = new TextView(context);
		titleView.setText(title);
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 2;
		titleView.setLayoutParams(params);
		titleView.setTypeface(TypefaceRegistry.getTypeFace(context,
				"HelveticaLTStd-BoldCond.otf"));
		return titleView;
	}

	private ImageView createcConferenceLogo(Context context, String url) {
		ImageView conferenceLogo = new ImageView(context);
		conferenceLogo.setMaxHeight(100);
		conferenceLogo.setMaxWidth(150);
		conferenceLogo.setMinimumHeight(100);
		conferenceLogo.setMinimumWidth(150);
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
				"HelveticaLTStd-Cond.otf"));
		return summaryView;
	}

	private TextView createDates(Context context, Date startDate, Date endDate) {
		String start = dateFormat.format(startDate);
		String end = dateFormat.format(endDate);
		TextView datesView = new TextView(context);
		datesView.setText(start + " - " + end);
		datesView.setTypeface(TypefaceRegistry.getTypeFace(context,
				"HelveticaLTStd-BoldCond.otf"));
		return datesView;
	}

	private TextView createLocation(Context context, String country, String city) {
		TextView locationView = new TextView(context);
		locationView.setText(country + ", " + city);
		locationView.setPadding(10, 10, 10, 10);
		locationView.setTypeface(TypefaceRegistry.getTypeFace(context,
				"HelveticaLTStd-BoldCond.otf"));
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

}
