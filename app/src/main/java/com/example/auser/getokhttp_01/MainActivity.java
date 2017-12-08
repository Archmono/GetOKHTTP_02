package com.example.auser.getokhttp_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainTest";
    EditText etUserID,etTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("OKHttp02");

        etUserID = (EditText)findViewById(R.id.etUserId);
        etTemperature = (EditText)findViewById(R.id.etTemperature);

    }

    //getMethod method
    public void getMethod(View v){
//        Log.d(TAG,"btn clicked.");
        OkHttpClient client = new OkHttpClient();
        String param = "userid=" + etUserID.getText().toString() + "&temperature=" + etTemperature.getText().toString();

        Request request = new Request.Builder()
                .url("http://192.168.58.008:8080/code/android_api/get_api_return.php?" + param)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "fail");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "連線失敗", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "ok");
                String json = response.body().string();
                Log.d(TAG, json);
                int flag = json.compareTo("0");
                if (flag == 0){
                    Log.d(TAG, "0");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "新增失敗，使用者ID已存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d(TAG, "1-android");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "新增資料", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
