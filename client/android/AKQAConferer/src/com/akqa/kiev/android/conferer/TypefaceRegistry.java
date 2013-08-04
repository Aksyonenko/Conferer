package com.akqa.kiev.android.conferer;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public final class TypefaceRegistry {

	private static Map<String, Typeface> typefaces = new HashMap<String, Typeface>();

	public static String COND = "HelveticaLTStd-Cond.otf";
	public static String BOLD_COND = "HelveticaLTStd-BoldCond.otf";
	public static String BLK_COND = "HelveticaLTStd-BlkCond.otf";

	private TypefaceRegistry() {
	}

	public static Typeface getTypeFace(Context context, String name) {
		Typeface typeface = typefaces.get(name);

		if (typeface == null) {
			typeface = Typeface.createFromAsset(context.getAssets(), "fonts/"
					+ name);
			typefaces.put(name, typeface);
		}

		return typeface;
	}
}
