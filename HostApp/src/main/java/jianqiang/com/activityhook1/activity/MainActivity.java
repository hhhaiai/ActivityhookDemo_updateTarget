package jianqiang.com.activityhook1.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import jianqiang.com.activityhook1.ams_hook.AMSHookHelper;

public class MainActivity extends Activity {
    
    private static final String TAG = "sanbo.MA";
    
    private static final String apkName = "plugin1.apk";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button t = new Button(this);
        t.setText("test button");
        
        setContentView(t);

        Log.d(TAG, "context classloader: " + getApplicationContext().getClassLoader());
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent t = new Intent();
                    t.setComponent(
                            new ComponentName(getPackageName(),
                                    A.class.getName()));

                    startActivity(t);
                } catch (Throwable e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
//            Utils.extractAssets(newBase, apkName);
//            File dexFile = getFileStreamPath(apkName);
//            File optDexFile = getFileStreamPath("plugin1.dex");
//            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
    
//            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();
    
        } catch (Throwable e) {
            Log.e(TAG, Log.getStackTraceString(e));
    
        }
    }
}
