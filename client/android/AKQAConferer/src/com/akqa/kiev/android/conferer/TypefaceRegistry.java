package com.akqa.kiev.android.conferer;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public final class TypefaceRegistry {

	private static Map<String, Typeface> typefaces = new HashMap<String, Typeface>();

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
