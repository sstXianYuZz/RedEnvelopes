package com.sst.redenvelopes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        super.start();
    }

    public void stop(View view) {
        super.stop();
    }

    public void next(View view) {
      startActivity(new Intent(this,MainActivity2.class));
    }
}
