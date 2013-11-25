package com.akqa.kiev.android.conferer.fragments;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.model.SpeakerData;
import com.androidquery.AQuery;

public class ConferenceDetailsArrayAdapter extends ArrayAdapter<SessionData> {

	private List<SessionData> sessions;
	private SimpleDateFormat startDateFormat;
	private SimpleDateFormat endDateFormat;

	public ConferenceDetailsArrayAdapter(Context context, int textViewResourceId, List<SessionData> sessions) {
		super(context, textViewResourceId, sessions);
		this.sessions = sessions;
		startDateFormat = new SimpleDateFormat("MMM dd HH:mm", Locale.US);
		endDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
	}

	@Override
	public int getCount() {
		return sessions.size();
	}

	@Override
	public SessionData getItem(int position) {
		return sessions.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.conference_details_list_item, parent, false);
		}
		SessionData item = getItem(position);
		TextView titleTextView = (TextView) row.findViewById(R.id.conference_details_list_item_title);
		TextView speakerTextView = (TextView) row.findViewById(R.id.conference_details_list_item_speaker);
		TextView timeTextView = (TextView) row.findViewById(R.id.conference_details_list_item_time);
		ImageView logoImageView = (ImageView) row.findViewById(R.id.conference_details_list_item_logo);
		if (logoImageView != null) {
			AQuery aq = new AQuery(row);
			aq.id(R.id.conference_details_list_item_logo).image(item.getSessionLogoUrl());
		}
		StringBuilder builder = new StringBuilder();
		Iterator<SpeakerData> speakers = item.getSpeakers().iterator();
		while (speakers.hasNext()) {
			SpeakerData speaker = speakers.next();
			builder.append(speaker.getFirstName()).append(" ").append(speaker.getLastName());
			if (speakers.hasNext()) {
				builder.append(", ");
			}
		}

		String sessionTime = startDateFormat.format(item.getStartTime()) + " - "
				+ endDateFormat.format(item.getEndTime());
		speakerTextView.setText(builder.toString());
		titleTextView.setText(item.getTitle());
		timeTextView.setText(sessionTime);
		return row;
	}
}
