package com.akqa.kiev.android.conferer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ConfererButton extends Button {
	public ConfererButton(Context context) {
		super(context);
		initTypeface(context);
	}

	public ConfererButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initTypeface(context);
	}

	public ConfererButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTypeface(context);
	}

	private void initTypeface(Context context) {
		setTypeface(TypefaceRegistry.getTypeFace(context,
				TypefaceRegistry.BOLD_COND));

	}
}
