package com.duosi.myfavoritemovies;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MyExecutors {

    private static MyExecutors instance;

    public static MyExecutors getInstance(){
        if(instance == null){
            instance = new MyExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }

}
