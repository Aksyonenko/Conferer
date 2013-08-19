package com.akqa.kiev.android.conferer.view.social;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.akqa.kiev.android.conferer.R;
import com.akqa.kiev.android.conferer.model.SocialLinksData;

public class SocialPanel extends LinearLayout {

	public SocialPanel(Context context) {
		super(context);
	}

	public SocialPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SocialPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(SocialLinksData data) {
		if (data != null) {
			String twitterUrl = data.getTwitter();
			if (twitterUrl != null) {
				addView(createSocialImg(twitterUrl, R.drawable.twitter_logo));
			}
			String facebookUrl = data.getFacebook();
			if (facebookUrl != null) {
				addView(createSocialImg(facebookUrl, R.drawable.facebook_logo));
			}
			String linkedinUrl = data.getLinkedin();
			if (linkedinUrl != null) {
				addView(createSocialImg(linkedinUrl, R.drawable.linkedin_logo));
			}
		}
	}

	private ImageView createSocialImg(final String url, int resId) {
		ImageView img = new ImageView(getContext());
		img.setBackgroundResource(resId);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 10, 10, 0);
		img.setLayoutParams(layoutParams);
		img.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.setData(Uri.parse(url));
				getContext().startActivity(intent);
			}
		});
		return img;
	}

}
