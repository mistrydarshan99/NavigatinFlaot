package com.cabmax.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.cabmax.OnTheWay.R;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void doNext(View v){
        Intent intent = new Intent(this,NavigationDrawer.class);
        startActivity(intent);
    }
}
