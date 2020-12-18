package vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service;

import android.support.annotation.Nullable;

import vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.model.service.api.ILoginService;


public class LoginServiceHolder {
    ILoginService myService = null;

    @Nullable
    public ILoginService get() {
        return myService;
    }

    public void set(ILoginService myService) {
        this.myService = myService;
    }
}
