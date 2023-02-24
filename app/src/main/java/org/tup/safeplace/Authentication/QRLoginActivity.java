package org.tup.safeplace.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Size;
import android.widget.EditText;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.zxing.Result;

import org.tup.safeplace.R;

import java.util.concurrent.ExecutionException;

public class QRLoginActivity extends AppCompatActivity {

    private EditText qrCodetxt;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;
    private static final int REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrlogin);

        codeScannerView = findViewById(R.id.codeScannerView);
        codeScanner = new CodeScanner(this,codeScannerView);
        qrCodetxt = findViewById(R.id.qrCodeEdtTxt);



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CODE);
        }

        init();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.recreate();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        codeScanner.startPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        codeScanner.stopPreview();
    }


    private void init(){
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text = result.getText();
                        qrCodetxt.setText(text);
                    }
                });
            }
        });

    }
}




















