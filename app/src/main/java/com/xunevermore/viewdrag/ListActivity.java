package com.xunevermore.viewdrag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this,MyViewActivity.class));
                break;
        }
    }
}
