package br.com.stchost.lavid.lavid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Dashboard extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CAMERA = 1;


    private boolean haveCameraPermission() {
        if (Build.VERSION.SDK_INT < 23)
            return true;
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        Button brqrcode = (Button) findViewById(R.id.btqrcode);

        brqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register ourselves as a handler for scan results.
                if (!haveCameraPermission())
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                else {
                    Intent QR = new Intent(Dashboard.this, SimpleScannerActivity.class);
                    Dashboard.this.startActivity(QR);
                }
            }
        });


        // Request permission. This does it asynchronously so we have to wait for onRequestPermissionResult before trying to open the camera.

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.length == 0 || grantResults.length == 0)
            return;

        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent QR = new Intent(Dashboard.this, SimpleScannerActivity.class);
                    Dashboard.this.startActivity(QR);
                } else {
                    finish();
                }
            }
            break;
        }
    }

}
