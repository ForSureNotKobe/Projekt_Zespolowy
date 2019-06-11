package com.example.barcode_beta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public  static boolean LogPass;

    Button btn_Check, btn_Login, btn_Show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Check = (Button)findViewById(R.id.btn_Check);
        btn_Login = (Button)findViewById(R.id.btn_Login);
        btn_Show = (Button)findViewById(R.id.btn_Show);

        btn_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LogPass) {
                    openProductsList();
                }
                else{
                    Toast.makeText(MainActivity.this, "You have to log in first!", Toast.LENGTH_LONG).show();
                    loginUser();
                }
            }
        });

        btn_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LogPass) {
                    openScanner();
                } else {
                    Toast.makeText(MainActivity.this, "You have to log in first!", Toast.LENGTH_LONG).show();
                    loginUser();
                }
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser(){

       Intent intent_Login = new Intent(getApplicationContext(), Login.class);
       startActivity(intent_Login);
    }

    private void openScanner(){

        Intent intent_Scanner = new Intent(getApplicationContext(), BarcodeScanner.class);
        startActivity(intent_Scanner);
    }

    private void openProductsList(){
        Intent intent_List = new Intent(getApplicationContext(), ProdList.class);
        startActivity(intent_List);
    }

}