package com.lge.emojichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.lge.emojichat.constants.Constants;
import com.lge.emojichat.util.EmojiUtils;

public class EmojiActivity extends Activity implements View.OnTouchListener,OnClickListener {

    private static final int ANGLE_MIN = 0;
    private static final int ANGLE_MAX = 180;
    private static final int ANGLE_INTERVAL = 20;
    private int images[] = new int[] { R.drawable.emoji_good_4,
                                        R.drawable.emoji_good_3,
                                        R.drawable.emoji_good_2,
                                        R.drawable.emoji_good_1,
                                        R.drawable.emoji_default,
                                        R.drawable.emoji_bad_1,
                                        R.drawable.emoji_bad_2,
                                        R.drawable.emoji_bad_3,
                                        R.drawable.emoji_bad_4 };
    private int mIndex = 4;
    private boolean mIsTouchable = false;
    
    private FrameLayout mBGImage;
    private FrameLayout mTouchLayout;
    private CatEmojiImageView mOkButton;
    private CatEmojiImageView mEmoji_0;
    private CatEmojiImageView mEmoji_1;
    private CatEmojiImageView mEmoji_2;
    private CatEmojiImageView mEmoji_3;
    private CatEmojiImageView mEmoji_4;
    private CatEmojiImageView mEmoji_5;

    private float mPosX;
    private float mPosY;
    private float mDgree;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emoji_layout);

        mBGImage = (FrameLayout)findViewById(R.id.image);
        mTouchLayout = (FrameLayout)findViewById(R.id.scroll);
        mTouchLayout.setOnTouchListener(this);

        init();
    }

    private void init() {
        mCenterX = getResources().getDimension(R.dimen.scroll_width_height) / 2f;
        mCenterY = mCenterX;
        mRadius = getResources().getDimension(R.dimen.touch_limit_radius);

        mOkButton = new CatEmojiImageView(this, R.drawable.sendicon, Constants.EMOJI_OK);
        mEmoji_0 = new CatEmojiImageView(this, R.drawable.emoji_good_2, Constants.EMOJI_0);
        mEmoji_1 = new CatEmojiImageView(this, R.drawable.emoji_love, Constants.EMOJI_1);
        mEmoji_2 = new CatEmojiImageView(this, R.drawable.emoji_good_4, Constants.EMOJI_2);
        mEmoji_3 = new CatEmojiImageView(this, R.drawable.emoji_mask, Constants.EMOJI_3);
        mEmoji_4 = new CatEmojiImageView(this, R.drawable.emoji0_cry, Constants.EMOJI_4);
        mEmoji_5 = new CatEmojiImageView(this, R.drawable.emoji_bad_3, Constants.EMOJI_5);

        setCategoryImage(mEmoji_0, 0);
        setCategoryImage(mEmoji_1, 330);
        setCategoryImage(mEmoji_2, 300);
        setCategoryImage(mOkButton, 270);
        setCategoryImage(mEmoji_3, 240);
        setCategoryImage(mEmoji_4, 210);
        setCategoryImage(mEmoji_5, 180);
    }

    private void setCategoryImage(CatEmojiImageView view, float degree) {
        view.setOnClickListener(this);
        int miniEmojiWidth = (int)getResources().getDimension(R.dimen.mini_emoji_width_height);
        int correctRadius = (int)getResources().getDimension(R.dimen.category_image_correct_radius);
        mTouchLayout.addView(view, new FrameLayout.LayoutParams(
                miniEmojiWidth, miniEmojiWidth));
        float location[] = new float[2];
        EmojiUtils.angleToPoint(degree, mRadius + correctRadius, mCenterX, mCenterY, location);
        view.setX(location[0] - (miniEmojiWidth / 2));
        view.setY(location[1] - (miniEmojiWidth / 2));
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                mPosX = event.getX();
                mPosY = event.getY();
                float touchDistance = (float)EmojiUtils.getDistance(mCenterX, mCenterY, mPosX, mPosY);
                float touchDgree = EmojiUtils.pointToAngle(mPosX, mPosY, mCenterX, mCenterY);
                mDgree = touchDgree;

                mIsTouchable = true;
                if (touchDistance < mRadius
                        || touchDgree < ANGLE_MIN
                        || touchDgree > ANGLE_MAX) {
                    Log.e("yys", "Unavailable area!!!");
                    mIsTouchable = false;
                }
                Log.i("yys", "touch degree : " + touchDgree);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsTouchable) {
                    mPosX = event.getX();
                    mPosY = event.getY();
                    float moveDgree = EmojiUtils.pointToAngle(mPosX, mPosY, mCenterX, mCenterY);
                    float degreeInterval = moveDgree - mDgree;
                    Log.i("yys", "move degree : " + moveDgree);
                    if (Math.abs(degreeInterval) > ANGLE_INTERVAL) {
                        if (degreeInterval >= 0) {
                            mIndex = increasingIndex(++mIndex);
                        } else {
                            mIndex = increasingIndex(--mIndex);
                        }
                        Log.d("yys", "index : " + mIndex);
                        mBGImage.setBackgroundResource(images[mIndex]);
                        mDgree = moveDgree;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private int increasingIndex(int index) {
        int result = index;
        if (index < 0){
            result =  0;
        } else if (index >= images.length) {
            result = images.length - 1;
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        Log.d("yys", "" + view.getTag());
        if (Constants.EMOJI_OK.equals(view.getTag())) {
            Intent intent = new Intent();
            intent.putExtra("resId", images[mIndex]);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            mBGImage.setBackground(view.getBackground());
        }
    }
}
