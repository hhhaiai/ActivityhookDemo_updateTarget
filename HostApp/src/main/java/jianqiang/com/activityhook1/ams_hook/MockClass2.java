package jianqiang.com.activityhook1.ams_hook;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

import jianqiang.com.activityhook1.RefInvoke;
import jianqiang.com.activityhook1.activity.B;

/**
 * @author weishu
 * @date 16/1/7
 */
/* package */ class MockClass2 implements Handler.Callback {

    Handler mBase;

    public MockClass2(Handler base) {
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
    
        try {
            Log.i("sanbo", "MockClass2 handleMessage msg: " + msg.what);
        
            switch (msg.what) {
                // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
                // 本来使用反射的方式获取最好, 这里为了简便直接使用硬编码
                case 100:   //for API 28以下
                    handleLaunchActivity(msg);
                    break;
                case 159:   //for API 28
                    handleActivity(msg);
                    break;
            }
        
            mBase.handleMessage(msg);
        } catch (Throwable e) {
           Log.e("sanbo",Log.getStackTraceString(e));
        }
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        // 这里简单起见,直接取出TargetActivity;

        Object obj = msg.obj;

        // 把替身恢复成真身
        Intent raw = (Intent) RefInvoke.getFieldObject(obj, "intent");

        Intent target = raw.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
        raw.setComponent(target.getComponent());
    }

    private void handleActivity(Message msg) {
        // 这里简单起见,直接取出TargetActivity;
        Object obj = msg.obj;
    
        List<Object> mActivityCallbacks = (List<Object>) RefInvoke.getFieldObject(obj, "mActivityCallbacks");
    
        for (int i = 0; i < mActivityCallbacks.size(); i++) {
            Log.i("sanbo", "-->" + mActivityCallbacks.get(0).getClass().getCanonicalName());
        }
        if (mActivityCallbacks.size() > 0) {
            String className = "android.app.servertransaction.LaunchActivityItem";
            if (mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)) {
                Object object = mActivityCallbacks.get(0);
                Intent intent = (Intent) RefInvoke.getFieldObject(object, "mIntent");
                ComponentName cm= intent.getComponent();
                if (cm.getClassName().contains(".B")){
                    Log.i("sanbo","含B名字");
                }else{
                    Log.i("sanbo"," 不含B名字");
                }
                Log.i("sanbo","general intent :" + intent);
//                Intent target = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
                Intent newIntent = new Intent();
                // 替身Activity的包名, 也就是我们自己的包名
                String stubPackage = "jianqiang.com.activityhook1";
    
                // 这里我们把启动的Activity临时替换为 StubActivity
                ComponentName componentName = new ComponentName(stubPackage, B.class.getName());
                newIntent.setComponent(componentName);
                intent.setComponent(newIntent.getComponent());
               
            }
        }
    }
}
