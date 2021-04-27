package com.wly.jacocodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wly.secondlib.SecondLibUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.jump)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , JumpActivity.class);
                startActivity(intent);
                SecondLibUtils.Jump();
            }
        });

        String str = getSubUtilSimple( "#hjh##123#", "#(.*?)#");
        Log.d("TAG" , "str:" + str.substring(1 , str.length() -1));

//        Log.d("TAG" , "str:" + getSubUtilSimple( "#hjh##123#", "#(.*?)#"));
    }


    public String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);
        Matcher matcher = pattern.matcher(soap);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }


    public void unusedMethod(){
        Log.d("TAG" , "unused method");
    }
}
