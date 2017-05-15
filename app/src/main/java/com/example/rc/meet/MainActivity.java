package com.example.rc.meet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PHONE_REQUEST = 100;
    private static final int SEND_SMS_REQUEST = 200;

    private Context context;
    private Cursor cursor;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.name)
    protected TextView name;

    @BindView(R.id.rating)
    protected RatingBar rating;

    @BindView(R.id.message)
    protected EditText message;

    @BindView(R.id.send)
    protected ImageButton send;

    @BindView(R.id.call)
    protected ImageButton call;

//    @OnClick(R.id.call)
//    public void makeCall() {
//        Intent intent = new Intent(Intent.ACTION_CALL);
//
//        intent.setData(Uri.parse("tel:508071086"));
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST);
//            return;
//        }
//        context.startActivity(intent);
//    }

    @OnClick(R.id.call)
    public void setCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST);
            Log.i("TAG", "PERMISSIONS NEEDED");
            return;
        }


        Intent intent = new Intent(Intent.ACTION_CALL);
//        String number = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        intent.setData(Uri.parse("tel:508071086"));
        startActivity(intent);
    }

//    MOJE
// @OnClick(R.id.send)
//    public void sendSMS() {
//
////                Toast.makeText(context,"a",Toast.LENGTH_SHORT).show();
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST);
//            return;
//        }
//
//
//        SmsManager sms = SmsManager.getDefault();
//
//        sms.sendTextMessage("508071086", null, "asd", null, null);
//    }

    @OnClick(R.id.send)
    public void sendMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST);
            Log.i("TAG", "PERMISSIONS NEEDED");
            return;
        }
        SmsManager sms = SmsManager.getDefault();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (message.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Nie wysyłaj pustej wiadomości debilu", Toast.LENGTH_SHORT).show();
        } else {
//            sms.sendTextMessage(cursor.getString(
//                    cursor.getColumnIndexOrThrow("phone")), null, message.getText().toString(), null, null);
            Toast.makeText(MainActivity.this, message.getText(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://com.example.rc.samples.contentprovider/people"),
                null,
                null,
                null,
                "rating DESC");

//        while (cursor.moveToNext()){
//            Log.i("T",cursor.getString(cursor.getColumnIndex("phone")));
//        }

        cursor.moveToFirst();
        name.setText(cursor.getString(cursor.getColumnIndex("name")));
        rating.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));


    }
}
