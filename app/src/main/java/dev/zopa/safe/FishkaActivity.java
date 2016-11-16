package dev.zopa.safe;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FishkaActivity extends Activity implements View.OnClickListener {

    Button dt;
    Button a2;
    Button a5;
    Button add;

    TextView fishka;

    EditText petrol;
    EditText numFishka;

    EAN13CodeBuilder bb;

    LinearLayout aktualLL;
    LinearLayout llverLeft;
    LinearLayout llverRight;

    //todo del radioButt
    RadioButton four;
    RadioButton first;
    RadioButton third;
    RadioButton second;
    RadioGroup rgGravity;


    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishka);

        llverLeft = (LinearLayout) findViewById(R.id.llverLeft);
        llverRight = (LinearLayout) findViewById(R.id.llverRight);

        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        first = (RadioButton) findViewById(R.id.first);
        second = (RadioButton) findViewById(R.id.second);
        third = (RadioButton) findViewById(R.id.third);
        four = (RadioButton) findViewById(R.id.four);

        dt = (Button) findViewById(R.id.dt);
        dt.setOnClickListener(this);

        a2 = (Button) findViewById(R.id.a2);
        a2.setOnClickListener(this);

        a5 = (Button) findViewById(R.id.a5);
        a5.setOnClickListener(this);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        fishka = (TextView)findViewById(R.id.fishka);
        petrol = (EditText) findViewById(R.id.petrol);
        numFishka = (EditText) findViewById(R.id.numberFishka);

        /// установить шрифт штрих-кода
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Normal2.ttf");
        fishka.setTypeface(font);
    }


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

            case R.id.add:
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
                int benGravity = Gravity.LEFT;


                switch (rgGravity.getCheckedRadioButtonId()){
                    case R.id.first:
                        benGravity = Gravity.LEFT;
                        aktualLL = llverLeft;
                        break;
                    case R.id.second:
                        benGravity = Gravity.CENTER_HORIZONTAL;
                        aktualLL = llverLeft;
                        break;
                    case R.id.third:
                        benGravity = Gravity.LEFT;
                        aktualLL = llverRight;
                        break;
                    case R.id.four:
                        benGravity = Gravity.RIGHT;
                        aktualLL = llverRight;
                        break;
                }
                lParams.gravity = benGravity;
                Button btnNew = new Button(this);
                btnNew.setText(petrol.getText().toString());
                //btnNew.setId(Integer.parseInt(petrol.getText().toString()));
                aktualLL.addView(btnNew, lParams);

                break;
        }
    }
}
