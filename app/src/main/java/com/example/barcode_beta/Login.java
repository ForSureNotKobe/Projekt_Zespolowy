package com.example.barcode_beta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btn_Login;
    EditText txt_User, txt_Password;

    private String User;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Login = (Button) findViewById(R.id.btn_Login);
        txt_Password = (EditText) findViewById(R.id.txt_Password);
        txt_User = (EditText) findViewById(R.id.txt_User);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String LOGIN_URL = "https://barcodescanerproject.000webhostapp.com/logApp.php";
                User = txt_User.getText().toString().trim();
                Password = txt_Password.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("success")) {
                                    MainActivity.LogPass = true;
                                    Toast.makeText(Login.this, "Logged in!", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Wrong username or password!", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("login", User);
                        map.put("haslo", Password);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}
