package com.akqa.kiev.android.conferer.task;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.akqa.kiev.android.conferer.utils.LogUtils;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView image;

	public DownloadImageTask(ImageView bmImage) {
		this.image = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
		Bitmap icon = null;
		try {
			InputStream in = new java.net.URL(url).openStream();
			icon = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			LogUtils.logE(getClass().getName(), e);
		}
		return icon;
	}

	protected void onPostExecute(Bitmap result) {
		image.setImageBitmap(result);

	}
}