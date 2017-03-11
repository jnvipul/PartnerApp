package com.restaurant.partnerapp;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.restaurant.partnerapp.database.DBHelper;
import com.restaurant.partnerapp.database.TableContract;
import com.restaurant.partnerapp.utility.Logger;

import java.util.Date;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vJ on 3/11/17.
 */


/**
 * This service runs every 10 minutes and cancels all the reservations.
 */

public class ClearReservationsService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        Logger.debug("Inside on start job");
        Toast.makeText(getApplicationContext(), "Started job", Toast.LENGTH_LONG).show();
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(TableContract.TableAvailability.AVAILABLE, 1);
                cv.putNull(TableContract.TableAvailability.CUSTOMER_FIRST_NAME);
                cv.putNull(TableContract.TableAvailability.CUSTOMER_LAST_NAME);
                cv.putNull(TableContract.TableAvailability.CUSTOMER_ID);

                db.update(TableContract.TableAvailability.TABLE_NAME, cv, null, null);

                return null;
            }
        }).flatMap(new Func1<Object, Observable<?>>() {
            @Override
            public Observable<?> call(Object o) {
                Logger.debug("Main Thread in service = " + (Looper.myLooper() == Looper.getMainLooper()));
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object -> onUpdateSuccessful(object, job), this::onError);

        return true;
    }

    private void onUpdateSuccessful(Object o, JobParameters jobParameters) {
        Toast.makeText(getApplicationContext(), "Finished job", Toast.LENGTH_LONG).show();
        // TODO - Remove after testing
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.table_available)
                        .setContentTitle("Yayy!")
                        .setContentText("Mission SuccessFul");
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), mBuilder.build());

        jobFinished(jobParameters, true);
    }

    private void onError(Throwable throwable) {
        Logger.debug("Error executing job -" + throwable.getMessage());
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Logger.debug("Inside on stop job");
        return false;
    }
}
