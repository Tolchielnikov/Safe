package dev.zopa.safe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FishkaActivity extends Activity implements View.OnClickListener {

    Button add;
    Button delBut;

    TextView fishka;

    EditText petrol;
    EditText numFishka;

    EAN13CodeBuilder bb;

    LinearLayout aktualLL;
    LinearLayout llverFirst;
    LinearLayout llverSecond;
    LinearLayout llverThird;
    LinearLayout llverFour;

    RadioGroup rgGravity;

    Map barCode = new HashMap<String, String>();

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_COUNTER = "button";
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishka);

        llverFirst = (LinearLayout) findViewById(R.id.firstColon);
        llverSecond = (LinearLayout) findViewById(R.id.secondColon);
        llverThird = (LinearLayout) findViewById(R.id.thirdColon);
        llverFour = (LinearLayout) findViewById(R.id.fourColon);

        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(this);

        delBut = (Button) findViewById(R.id.delBut);
        delBut.setOnClickListener(this);

        fishka = (TextView) findViewById(R.id.fishka);
        petrol = (EditText) findViewById(R.id.petrol);
        numFishka = (EditText) findViewById(R.id.numberFishka);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        // font for bar-code
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Normal2.ttf");
        fishka.setTypeface(font);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        // Listener for dynamic created buttons
        @Override
        public void onClick(View view) {
            bb = new EAN13CodeBuilder((String) barCode.get(Integer.toString(((Button) view).getId())));
            fishka.setText(bb.getCode());
        }
    };

    /***
     * create new button
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
                int benGravity = Gravity.LEFT;
                aktualLL = llverFirst;


                switch (rgGravity.getCheckedRadioButtonId()) {
                    case R.id.first:
                        aktualLL = llverFirst;
                        break;
                    case R.id.second:
                        aktualLL = llverSecond;
                        break;
                    case R.id.third:
                        aktualLL = llverThird;
                        break;
                    case R.id.four:
                        aktualLL = llverFour;
                        break;
                }
                // check valid number fishka
                if (numFishka.getText().toString().length() != 13) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Фишка состоит из 13 цифр!!! Вы ввели = " + numFishka.getText().toString().length(),
                            Toast.LENGTH_SHORT);
                    toast.show();
//todo kakto huevo hranit kluh poslednie cifri
                } else if (!numFishka.getText().toString().matches("^\\d{13}$")){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Фишка состоит только из ЦИФР", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (barCode.containsKey(numFishka.getText().toString().substring(4))) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "у Вас уже есть такая фишка", Toast.LENGTH_SHORT);
                    toast.show();
                  }else{ // create new button
                    lParams.gravity = benGravity;
                    Button btnNew = new Button(this);
                    btnNew.setText(petrol.getText());
                    btnNew.setOnClickListener(onClickListener);
                    aktualLL.addView(btnNew, lParams);
                    btnNew.setId(Integer.parseInt(numFishka.getText().toString().substring(4)));
                    barCode.put(numFishka.getText().toString().substring(4), numFishka.getText().toString());
                }
                break;
                 // delete button
            case R.id.delBut:
                if (barCode.containsKey(numFishka.getText().toString().substring(4))) {

                ViewGroup layout = (ViewGroup) findViewById(Integer.parseInt(numFishka.getText().toString().substring(4))).getParent();
                    layout.removeView((Button) findViewById(Integer.parseInt(numFishka.getText().toString().substring(4))));
                barCode.remove(numFishka.getText().toString().substring(4));
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Фишка удалена", Toast.LENGTH_SHORT);
                toast.show();

                }else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Фишки с таким номером у Вас нет", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }
}
