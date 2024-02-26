package com.example.loginform;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TURNING_CODE = 0;
    private static final int REQUEST_BIND_CODE = 1;
    private Button logout;
    private Button expDb;
    private Button randomNum;
    private Button bindBtn;
    private Button unbindBtn;
    private String[] permissions;

    private String SERVICE_PACKAGE_NAME = "com.example.filecontrol";
    private String SERVICE_CLASS_NAME ="com.example.filecontrol.RandomService";

    private Context mContext;
    public static final int GET_RANDOM_FLAG =0;

    private boolean mIsBound;
    private int randomNumberValue;

    Messenger requestRandomNumber, receiveRandomNumber;

    private Intent serviceIntent;

    private TextView textViewRandomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        }, 60000000);
        permissions = new String[]{"com.example.filecontrol.TURNAPP_PERMISSION","com.example.filecontrol.BIND_SERVICE"};
        mContext=getApplicationContext();
        textViewRandomNumber=(TextView)findViewById(R.id.viewnum);
        serviceIntent=new Intent();
        serviceIntent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME,SERVICE_CLASS_NAME));

        logout = (Button) findViewById(R.id.logoutBtn);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
        String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        expDb = (Button) findViewById(R.id.expDb);
        expDb.setOnClickListener(v -> {
            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                String[] permission = new String[]{permissions[0]};
                requestPermissions(permission, REQUEST_TURNING_CODE);
            } else {
                exportData(savePath);
                Intent intent = new Intent();
                intent.setAction(new String("android.intent.action.FILE_CONTROL"));
                startActivity(intent);
            }
        });
        randomNum = findViewById(R.id.random);
        randomNum.setOnClickListener(v -> {
            getRandomNumber();
        });
        bindBtn = findViewById(R.id.bindBtn);
        bindBtn.setOnClickListener(v -> {
            if(checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED)
                bindToService();
            else{
                String[] permission = new String[]{permissions[1]};
                requestPermissions(permission, REQUEST_BIND_CODE);
            }

        });
        unbindBtn = findViewById(R.id.unbindBtn);
        unbindBtn.setOnClickListener(v -> unbindFromService());
    }

    private void exportData(String savePath) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        List<String> data = databaseHelper.getAllData();

        String fileName = "data.txt"; // Tên tệp tin
        File file = new File(savePath, fileName);

        try {
            if(file.exists())
                file.delete();
            FileWriter writer = new FileWriter(file);

            // Ghi dữ liệu vào tệp tin
            for (String d : data) {
                writer.write(d + "\n");
            }
            writer.close();
            // Thông báo thành công
            Toast.makeText(this, "Dữ liệu đã được xuất ra tệp tin", Toast.LENGTH_SHORT
            ).show();
        } catch (IOException e){};
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_TURNING_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission for TURNING app GRANTED", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission to TURNING app DENIED", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_BIND_CODE:
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission for BINDING service GRANTED", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission to BINDING service DENIED", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
            }
        }

    class RecieveRandomNumberHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            randomNumberValue =0;
            switch (msg.what) {
                case GET_RANDOM_FLAG:
                    randomNumberValue =msg.arg1;
                    textViewRandomNumber.setText("Random Number: "+ randomNumberValue);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }
    ServiceConnection randomNumberServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            requestRandomNumber=null;
            receiveRandomNumber=null;
            mIsBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            requestRandomNumber=new Messenger(binder);
            receiveRandomNumber=new Messenger(new RecieveRandomNumberHandler());
            mIsBound=true;
        }
    };

    private void bindToService(){
        bindService(serviceIntent, randomNumberServiceConnection, BIND_AUTO_CREATE);
        Toast.makeText(mContext,"Service bound",Toast.LENGTH_SHORT).show();
    }

    private void unbindFromService(){
        if(mIsBound){
            unbindService(randomNumberServiceConnection);
            mIsBound=false;
            Toast.makeText(mContext,"Service Unbound",Toast.LENGTH_SHORT).show();
        }
    }

    private void getRandomNumber(){
        if (mIsBound == true) {
            Message requestMessage=Message.obtain(null, GET_RANDOM_FLAG);
            requestMessage.replyTo=receiveRandomNumber;
            try {
                requestRandomNumber.send(requestMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(mContext,"Service Unbound, can't get random number",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomNumberServiceConnection=null;
    }

}


