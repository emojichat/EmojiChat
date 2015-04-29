package com.lge.emojichat;

import android.content.Context;
import android.widget.ImageView;

public class CatEmojiImageView extends ImageView {

    public CatEmojiImageView(Context context, int resId, String tag) {
        super(context);
        setBackgroundResource(resId);
        setTag(tag);
    }
}
