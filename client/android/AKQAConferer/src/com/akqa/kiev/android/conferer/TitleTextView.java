package com.akqa.kiev.android.conferer;

import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class TitleTextView extends TextView {

	public TitleTextView(Context context) {
		super(context);
		initTypefaceAndText(context);
	}

	public TitleTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initTypefaceAndText(context);
	}

	public TitleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTypefaceAndText(context);
	}

	private void initTypefaceAndText(Context context) {
		setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));

		SpannableString title = new SpannableString(context.getString(
				R.string.app_name).toUpperCase(Locale.getDefault()));

		title.setSpan(new RelativeSizeSpan(1.2f), 0, 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		title.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 8, 0);
		setText(title, BufferType.SPANNABLE);
	}
}
