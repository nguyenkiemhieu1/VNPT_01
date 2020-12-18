package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.view.model;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by linhl on 11/22/2018.
 */

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Context myContext;
    private final Class<?> myActivityClass;

    public MyExceptionHandler(Context myContext, Class<?> myActivityClass) {
        this.myContext = myContext;
        this.myActivityClass = myActivityClass;
    }

    @Override
    public void uncaughtException(Thread t, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);// You can use LogCat too
        Log.e("uncaughtException",stackTrace.toString());
        Intent intent = new Intent(myContext, myActivityClass);
        String s = stackTrace.toString();
        //you can use this String to know what caused the exception and in which Activity
        intent.putExtra("uncaughtException",
                "Exception is: " + stackTrace.toString());
        intent.putExtra("stacktrace", s);
        myContext.startActivity(intent);
        //for restarting the Activity
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
