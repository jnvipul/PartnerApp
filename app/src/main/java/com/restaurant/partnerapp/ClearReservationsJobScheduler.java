package com.restaurant.partnerapp;

import android.content.Context;
import android.widget.Toast;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.restaurant.partnerapp.utility.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Created by vJ on 3/11/17.
 */

public class ClearReservationsJobScheduler {

    // Time with job has to be rescheduled all the time
    private static final int REMINDER_INTERVAL = (int) TimeUnit.SECONDS.convert(2, TimeUnit.MINUTES);
    private static final int FLEXIBILITY = (int) TimeUnit.SECONDS.convert(5, TimeUnit.SECONDS);

    private static final String JOB_TAG = "reset_reservations";

    public static void scheduleClearReservationsJob(Context context){

        try {

            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
            Job myJob = dispatcher.newJobBuilder()
                    .setService(ClearReservationsService.class) // the JobService that will be called
                    .setTag(JOB_TAG)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setReplaceCurrent(false)
                    .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL, REMINDER_INTERVAL +FLEXIBILITY))
                    .build();

            int i = dispatcher.schedule(myJob);
            Logger.debug("Scheduling Job + " + i);
        } catch (Exception e) {
            Logger.debug(e.getMessage());
        }
    }
}
