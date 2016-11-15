package dev.zopa.safe;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FishkaActivity extends AppCompatActivity {

    Button dt;
    Button a2;
    Button a5;
    TextView fishka;
    EAN13CodeBuilder bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishka);


        dt = (Button) findViewById(R.id.dt);
        a2 = (Button) findViewById(R.id.a2);
        a5 = (Button) findViewById(R.id.a5);
        fishka = (TextView)findViewById(R.id.fishka);

        /// установить шрифт штрих-кода
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Normal2.ttf");
        fishka.setTypeface(font);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 switch (v.getId()){
                     case R.id.dt:
                        bb  = new EAN13CodeBuilder("1124958761310");
                         fishka.setText(bb.getCode());
                         break;

                     case R.id.a2:
                         bb = new EAN13CodeBuilder("1000340726119");
                         fishka.setText(bb.getCode());
                         break;

                     case R.id.a5:
                         bb = new EAN13CodeBuilder("1000000199277");
                         fishka.setText(bb.getCode());
                         break;

                 }



            }
        };
        dt.setOnClickListener(onClickListener);
        a2.setOnClickListener(onClickListener);
        a5.setOnClickListener(onClickListener);
    }



    }
