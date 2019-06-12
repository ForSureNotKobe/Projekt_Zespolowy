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

public class AddProduct extends AppCompatActivity {

    Button btn_Add, btn_Cancel;
    EditText txt_ProdName, txt_Quant, txt_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        btn_Add = (Button) findViewById(R.id.btn_Add);
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel);
        txt_ProdName = (EditText) findViewById(R.id.txt_ProdName);
        txt_Quant = (EditText) findViewById(R.id.txt_Quant);
        txt_Price = (EditText) findViewById(R.id.txt_Price);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String LOGIN_URL = "https://barcodescanerproject.000webhostapp.com/AddProductApp.php";

                final String ProdName = txt_ProdName.getText().toString().trim();
                final String Quant = txt_Quant.getText().toString().trim();
                final String Price = txt_Price.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("error")) {
                                    MainActivity.LogPass = true;
                                    Toast.makeText(AddProduct.this, "Failed!!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(AddProduct.this, "Product added!", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AddProduct.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("Code", BarcodeScanner.NewBarcode);
                        map.put("product_name", ProdName);
                        map.put("price", Price);
                        map.put("quant", Quant);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(AddProduct.this);
                requestQueue.add(stringRequest);
                finish();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}