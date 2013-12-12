package com.akqa.kiev.android.conferer.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceData;
import com.androidquery.AQuery;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ConferenceListArrayAdapter extends ArrayAdapter<ConferenceData> {

	List<ConferenceData> conferences;
	SimpleDateFormat dayFormat;
	SimpleDateFormat monthFormat;
	SimpleDateFormat separatorDateFormat;
	private int lastMonth = 0;
	
	public ConferenceListArrayAdapter(Context context, int textViewResourceId,
			List<ConferenceData> conferences) {
		super(context, textViewResourceId, conferences);
		this.conferences = conferences;
		dayFormat = new SimpleDateFormat("dd", Locale.US);
		monthFormat = new SimpleDateFormat("MMM", Locale.US);
		separatorDateFormat = new SimpleDateFormat("MMMM yyyy", context.getResources().getConfiguration().locale);
	}
	
	@Override
	public int getCount() {
		return conferences.size();
	}
	
	@Override
	public ConferenceData getItem(int position) {
		return conferences.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.conference_listitem, parent, false);
		}
		
		ConferenceData item = getItem(position);
		
		//Determine if separator needed
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(item.getStartDate().getTime());
		int month = calendar.get(Calendar.MONTH);
		View separatorContainerView = row.findViewById(R.id.conference_listitem_separator);
		if(month != lastMonth) {
			separatorContainerView.setVisibility(View.VISIBLE);
			TextView separatorTextView = (TextView) row.findViewById(R.id.conference_listitem_separator_text);
			separatorTextView.setText(separatorDateFormat.format(item.getStartDate()));
			lastMonth = month;
		} else {
			separatorContainerView.setVisibility(View.GONE);
		}
		
		TextView titleTV = (TextView) row.findViewById(R.id.conference_listitem_title);
		titleTV.setText(getItem(position).getTitle());
		TextView dateTV = (TextView) row.findViewById(R.id.conference_listitem_dates);
		StringBuilder builder = new StringBuilder();
		builder.append("<b>").append(dayFormat.format(item.getStartDate())).append("</b>");
		builder.append(" ");
		builder.append(monthFormat.format(item.getStartDate()).toUpperCase()).append(" - ");
		builder.append("<b>").append(dayFormat.format(item.getEndDate())).append("</b>");
		builder.append(" ").append(monthFormat.format(item.getEndDate()).toUpperCase());
		dateTV.setText(Html.fromHtml(builder.toString()));
		TextView locationTV = (TextView) row.findViewById(R.id.conference_listitem_location);
		locationTV.setText(item.getCity() + ", " + item.getCountry());
		AQuery aq = new AQuery(row);
		aq.id(R.id.conference_listitem_logo).image(item.getLogoUrl());
		return row;
	}

}
