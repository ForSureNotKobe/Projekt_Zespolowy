package com.example.barcode_beta;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowContentFrameStats;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class BarcodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ProductSpec productSpec = new ProductSpec();
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    public  static String NewBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(BarcodeScanner.this, "Permission is granted!", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(BarcodeScanner.this, Manifest.permission.CAMERA)) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(BarcodeScanner.this, "Permission granted!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(BarcodeScanner.this, "Permission denied! Camera permission needed!", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to grant both permissions!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(BarcodeScanner.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        NewBarcode = scanResult;
        searchProduct(scanResult);
    }

    public void searchProduct(final String result) {
        final RequestQueue requestQueue = Volley.newRequestQueue(BarcodeScanner.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SHOW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            productSpec.setProductName(jsonObject.getString("message"));
                            requestQueue.stop();
                            AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeScanner.this);
                            builder.setTitle("Scan Result");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    scannerView.resumeCameraPreview(BarcodeScanner.this);
                                }
                            });
                            if (response.trim().contains("Product not found")) {
                                builder.setNegativeButton("ADD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent_AddProduct = new Intent(getApplicationContext(), AddProduct.class);
                                        startActivity(intent_AddProduct);
                                        scannerView.resumeCameraPreview(BarcodeScanner.this);
                                    }
                                });
                            }

                            builder.setMessage(productSpec.getProductName());
                            AlertDialog alert = builder.create();
                            alert.show();
                        } catch (JSONException exc) {
                            exc.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "search failed", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("result", result);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}