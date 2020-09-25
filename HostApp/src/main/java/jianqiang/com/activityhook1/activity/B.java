package jianqiang.com.activityhook1.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class B extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView t = new TextView(this);
        t.setText("B页面");
        setContentView(t);
        Log.e("sanbo", "----activity B onCreate-----");
    }
}