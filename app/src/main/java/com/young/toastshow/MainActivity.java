package com.young.toastshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_long).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewToastShow newToastShow = NewToastShow.makeText(MainActivity.this, "test", "this is a test", NewToastShow.TOAST_LONG);
                newToastShow.setGravity(Gravity.TOP, 0, 20);
                newToastShow.show();
            }
        });

        findViewById(R.id.btn_short).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewToastShow newToastShow = NewToastShow.makeText(MainActivity.this, "test", "this is a test", NewToastShow.TOAST_SHORT);
                newToastShow.setGravity(Gravity.TOP, 0, 20);
                newToastShow.show();
            }
        });
    }
}
