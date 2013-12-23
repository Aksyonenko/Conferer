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
import android.widget.BaseAdapter;
import android.widget.TextView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ConferenceListStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
	private static final int MONTHS_IN_YEAR = 12;
	
	private List<ConferenceData> conferences;
	private LayoutInflater inflater;
	SimpleDateFormat dayFormat;
	SimpleDateFormat monthFormat;
	SimpleDateFormat separatorDateFormat;

	public ConferenceListStickyAdapter(Context context, List<ConferenceData> conferences) {
		inflater = LayoutInflater.from(context);
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
	public Object getItem(int position) {
		return conferences.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = inflater.inflate(R.layout.conference_listitem, parent, false);
		}

		ConferenceData item = (ConferenceData) getItem(position);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(item.getStartDate().getTime());
		int month = calendar.get(Calendar.MONTH);
		
		TextView titleTV = (TextView) row.findViewById(R.id.conference_listitem_title);
		titleTV.setText(((ConferenceData) getItem(position)).getTitle());
		
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

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null) {
			row = inflater.inflate(R.layout.conference_listitem_header, parent, false);
		}
		
		ConferenceData item = (ConferenceData) getItem(position);
		TextView text = (TextView) row.findViewById(R.id.conference_listitem_separator_text);
		text.setText(separatorDateFormat.format(item.getStartDate()));
		
		return row;
	}

	@Override
	public long getHeaderId(int position) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(conferences.get(position).getStartDate());
		long id = calendar.get(Calendar.YEAR) * MONTHS_IN_YEAR + calendar.get(Calendar.MONTH);
		return id;
	}

}
