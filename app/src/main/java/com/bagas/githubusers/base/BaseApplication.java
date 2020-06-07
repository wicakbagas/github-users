package com.bagas.githubusers.base;


import com.bagas.githubusers.di.component.ApplicationComponent;
import com.bagas.githubusers.di.component.DaggerApplicationComponent;
import com.facebook.drawee.backends.pipeline.Fresco;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }
}
