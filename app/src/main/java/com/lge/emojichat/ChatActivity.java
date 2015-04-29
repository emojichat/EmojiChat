package com.lge.emojichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class ChatActivity extends Activity {

    private static final int REQUST_CODE = 1;
    private FrameLayout mResultLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        mResultLayout = (FrameLayout)findViewById(R.id.result);
        mResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ChatActivity.this, EmojiActivity.class), REQUST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUST_CODE && resultCode == RESULT_OK) {
            int resId = intent.getIntExtra("resId", -1);
            Log.d("yys", "Received result from EmojiActivity!! : " + resId);
            mResultLayout.setBackgroundResource(resId);
        }
    }
}
