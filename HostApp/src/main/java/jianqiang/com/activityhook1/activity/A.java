package jianqiang.com.activityhook1.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class A extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView t = new TextView(this);
        t.setText("A页面");
        setContentView(t);
    }
}