package com.planetinnovative.smsprovider;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGetMessage = findViewById(R.id.btnGetMessage);
        Button btnPhoneContact = findViewById(R.id.btnPhoneContact);
        btnGetMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri allMessages = Uri.parse("content://sms/");
                String[] projection = new String[]{"_id", "date", "body"};
                String selection = "date>=? and _id>=?";
                String[] selectionArgs = new String[]{"1534807790000", "4"};
                Cursor cursor = getContentResolver().query(allMessages, projection, selection, selectionArgs, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            Log.d(cursor.getColumnName(i) + "", cursor.getString(i) + "");
                        }
                    }
                }

            }
        });
        btnPhoneContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mCalendar = CalendarContract.CONTENT_URI;
                Uri calendar = Uri.parse(mCalendar + "/events");
                ContentValues eventValues = new ContentValues();
                eventValues.put("calendar_id", 1); // id, We need to choose from
                eventValues.put("title", "HELLO");
                eventValues.put("description", "THAMLE");
                eventValues.put("eventLocation", "TKLJSDFJ");
                eventValues.put("eventTimezone", TimeZone.getDefault().getID());
                Long startDate = System.currentTimeMillis();
                long endDate = startDate + 1000 * 60 * 60; // For next 1hr
                eventValues.put("dtstart", startDate);
                eventValues.put("dtend", endDate);
                getContentResolver().insert(calendar, eventValues);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Cursor cursor = getContentResolver().query(calendar, null, null, null, null);
                for (String value : cursor.getColumnNames()) {
                    System.out.println(value);
                }
            }
        });
    }
}