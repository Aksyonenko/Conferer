package com.akqa.kiev.android.conferer;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
				"HelveticaLTStd-BoldCond.otf"));
		SpannableString title = new SpannableString(
				context.getString(R.string.app_name));
		title.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 8, 0);
		setText(title, BufferType.SPANNABLE);
	}

}
