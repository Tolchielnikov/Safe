package dev.zopa.safe;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText inquiry;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inquiry = (EditText) findViewById(R.id.inquiry);
        textView = (TextView) findViewById(R.id.textView);
        search = (Button) findViewById(R.id.search);
        final Intent intent = new Intent(this, FishkaActivity.class);

        search.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (inquiry.getText().toString().equals("ola")) {
                    startActivity(intent);
                    return;
                }

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(inquiry.getText().toString())));

            }
        });
    }
}
