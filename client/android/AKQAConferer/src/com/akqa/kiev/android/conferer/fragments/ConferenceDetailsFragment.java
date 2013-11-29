package com.akqa.kiev.android.conferer.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.akqa.kiev.android.conferer.OnDetailsFragmentStartedListener;
import com.akqa.kiev.android.conferer.OnSessionSelectedListener;
import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.ConferenceDetailsData;
import com.akqa.kiev.android.conferer.model.SessionData;
import com.akqa.kiev.android.conferer.service.ConfererService;
import com.akqa.kiev.android.conferer.service.ConfererWebService;

public class ConferenceDetailsFragment extends Fragment implements OnItemClickListener {
	private ConfererService confererService;
	private Long conferenceId;
	List<SessionData> sessions;
	ListView conferenceDetailsListView;
	ConferenceDetailsArrayAdapter conferenceDetailsAdapter;
	OnDetailsFragmentStartedListener fragmentStartedListener;
	OnSessionSelectedListener sessionSelectedListener;
	SimpleDateFormat dayFormat;
	SimpleDateFormat monthFormat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		confererService = new ConfererWebService();
		sessions = new ArrayList<SessionData>();
		dayFormat = new SimpleDateFormat("dd", Locale.US);
		monthFormat = new SimpleDateFormat("MMM", Locale.US);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_conference_details, container, false);
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		fragmentStartedListener = (OnDetailsFragmentStartedListener) getActivity();
		sessionSelectedListener = (OnSessionSelectedListener) getActivity();
		conferenceDetailsListView = (ListView) getView().findViewById(R.id.conference_details_list_view);
		LayoutInflater inflater = (LayoutInflater) this.getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conferenceDetailsAdapter = new ConferenceDetailsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, sessions);
		conferenceDetailsListView.setAdapter(conferenceDetailsAdapter);
		conferenceDetailsListView.setOnItemClickListener(this);
		fragmentStartedListener.onDetailsFragmentStarted(this);
		
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
		onConferenceIdChange(conferenceId);
	}
	
	private void onConferenceIdChange(Long conferenceId) {
		LoadDataTask loadDataTask = new LoadDataTask();
		loadDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
	}
	
	private void updateViews(ConferenceDetailsData conferenceDetailsData) {
		TextView headerTitle = (TextView) getView().findViewById(R.id.session_list_header_title);
		headerTitle.setText(conferenceDetailsData.getTitle());
		TextView headerLocation = (TextView) getView().findViewById(R.id.session_list_header_location);
		headerLocation.setText(conferenceDetailsData.getCity() + ", " + conferenceDetailsData.getCountry());
		TextView headerDescription = (TextView) getView().findViewById(R.id.session_list_header_description);
		headerDescription.setText(conferenceDetailsData.getSummary());
		TextView headerDate = (TextView) getView().findViewById(R.id.session_list_header_dates);
		StringBuilder builder = new StringBuilder();
		Date startDate = conferenceDetailsData.getStartDate();
		Date endDate = conferenceDetailsData.getEndDate();
		builder.append("<b>").append(dayFormat.format(startDate)).append("</b>");
		builder.append(" ");
		builder.append(monthFormat.format(startDate).toUpperCase()).append(" - ");
		builder.append("<b>").append(dayFormat.format(endDate)).append("</b>");
		builder.append(" ").append(monthFormat.format(endDate).toUpperCase());
		headerDate.setText(Html.fromHtml(builder.toString()));
		sessions.clear();
		for(SessionData data : conferenceDetailsData.getSessions()) {
			sessions.add(data);
		}
		conferenceDetailsAdapter.notifyDataSetChanged();
	}
	
	private class LoadDataTask extends AsyncTask<Void, Void, ConferenceDetailsData> {

		@Override
		protected ConferenceDetailsData doInBackground(Void... params) {
			return confererService.loadConferenceDetails(conferenceId);
		}
		
		@Override
		protected void onPostExecute(ConferenceDetailsData result) {
			if(result != null) {
				updateViews(result);
			}
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Long sessionId = sessions.get(position).getId();
		sessionSelectedListener.onSessionSelected(sessionId);
	}
}
