package com.project.dj.ihouseservices;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class smsService extends Service{

    Context mContext=this ;
    private SharedPreferences prefs;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public smsService() {


    }
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//        String message = intent.getStringExtra("message");
//        String number = intent.getStringExtra("number");
//        Toast.makeText(mContext, "1111111111", Toast.LENGTH_SHORT).show();
//
//        if (message != null && number!=null){
//
//            sendSMS(number,message);
//        }
//    }
    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = intent.getStringExtra("message");
        String number = intent.getStringExtra("number");

        if (message != null && number!=null){

            sendSMS(number,message);
        }

         stopSelf();
       return START_STICKY;
    }

    private void sendSMS(String phoneNumber, String message) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0,
                new Intent(mContext, SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents);
            Toast.makeText(getBaseContext(), "Sending SMS to..."+phoneNumber,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed..."+phoneNumber,Toast.LENGTH_SHORT).show();
        }

    }
}
