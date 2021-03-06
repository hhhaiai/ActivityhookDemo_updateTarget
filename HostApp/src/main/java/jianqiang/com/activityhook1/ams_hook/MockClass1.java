package jianqiang.com.activityhook1.ams_hook;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import jianqiang.com.activityhook1.activity.B;

class MockClass1 implements InvocationHandler {
    
    private static final String TAG = "sanbo.MC1";
    
    Object mBase;
    
    public MockClass1(Object base) {
        mBase = base;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        try {
            Log.i(TAG, "MockClass1 invoke methodname: " + method.getName());
            if ("startActivity".equals(method.getName())) {
                // 只拦截这个方法
                // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱
                
                // 找到参数里面的第一个Intent 对象
                Intent raw;
                int index = 0;
                
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }
                raw = (Intent) args[index];
                
                Log.i(TAG, "MockClass1 invoke general raw: " + raw.toString());
                Intent newIntent = new Intent();
                // 替身Activity的包名, 也就是我们自己的包名
                String stubPackage = "jianqiang.com.activityhook1";
                
                // 这里我们把启动的Activity临时替换为 StubActivity
                ComponentName componentName = new ComponentName(stubPackage, B.class.getName());
                newIntent.setComponent(componentName);
                
                // 把我们原始要启动的TargetActivity先存起来
                newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, raw);
                
                // 替换掉Intent, 达到欺骗AMS的目的
                args[index] = newIntent;
                
                Log.d(TAG, "MockClass1 invoke . hook success");
                return method.invoke(mBase, args);
                
            }
        } catch (Throwable e) {
            Log.d(TAG, Log.getStackTraceString(e));
        }
        
        return method.invoke(mBase, args);
    }
}