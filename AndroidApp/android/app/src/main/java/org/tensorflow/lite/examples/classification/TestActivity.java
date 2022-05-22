package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import org.tensorflow.lite.examples.classification.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String [] names = getIntent().getStringArrayExtra("classNames");
        String [] values = getIntent().getStringArrayExtra("classValues");

        AlertDialog.Builder ad = new AlertDialog.Builder(TestActivity.this);
        ad.setIcon(R.mipmap.ic_launcher);
        ad.setTitle("예측 결과");
        ad.setMessage(names[0] + "\t\t" + values[0] + "\n"
                + names[1] + "\t\t" + values[1] + "\n"
                + names[2] + "\t\t" + values[2] + "\n"
        );

        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();

    }
}