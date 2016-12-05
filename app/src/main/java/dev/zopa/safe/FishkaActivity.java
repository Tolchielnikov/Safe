package dev.zopa.safe;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FishkaActivity extends Activity implements View.OnClickListener {

    //todo del
    final String LOG_TAG = "myLogs";

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

    DBHelper dbHelperButton;

    RadioGroup rgGravity;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int buttonPosition;

    LinearLayout.LayoutParams lParams;

    SQLiteDatabase db;

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

        dbHelperButton = new DBHelper(this);

        lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);

        // font for bar-code
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Normal2.ttf");
        fishka.setTypeface(font);

        db = dbHelperButton.getWritableDatabase();

        Cursor c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        Log.d(LOG_TAG, "----- рисуем кол-во кнопок -----  =  "+c.getCount());

        c.moveToFirst();

        // paint buttons
        for (int i = 0; i < c.getCount(); i++) {

            int idButton = c.getInt(c.getColumnIndex(DBHelper.KEY_ID));
            String nameButton = c.getString(c.getColumnIndex(DBHelper.NAME));
            String barCode = c.getString(c.getColumnIndex(DBHelper.BARCODE));
            int positionButton = c.getInt(c.getColumnIndex(DBHelper.POSITION));


            Button button = new Button(this);
            button.setId(idButton);
            button.setText(nameButton);
            button.setOnClickListener(onClickListener);

            lParams.gravity = Gravity.LEFT;

            if (positionButton == 1) {
                llverFirst.addView(button, lParams);
            } else if (positionButton == 2) {
                llverSecond.addView(button, lParams);
            } else if (positionButton == 3) {
                llverThird.addView(button, lParams);
            } else {
                llverFour.addView(button, lParams);
            }

            Log.d(LOG_TAG, "------ID-----" + idButton);
            Log.d(LOG_TAG, "------NAME----- " + nameButton);
            Log.d(LOG_TAG, "------BARCODE----- " + barCode);
            Log.d(LOG_TAG, "------POSITION----- " + positionButton);
            Log.d(LOG_TAG, "************************************************************");

            c.moveToNext();
        }
          c.close();
        }


    View.OnClickListener onClickListener = new View.OnClickListener() {

        // Listener for dynamic created buttons
        @Override
        public void onClick(View view) {
            bb = new EAN13CodeBuilder( getBarcode(((Button) view).getId())); //barCode.get(Integer.toString(((Button) view).getId())));
            fishka.setText(bb.getCode());
        }
    };

    /***
     * create new button
     */

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();

        switch (v.getId()) {
            case R.id.add:

                int benGravity = Gravity.LEFT;
                aktualLL = llverFirst;

                switch (rgGravity.getCheckedRadioButtonId()) {
                    case R.id.first:
                        aktualLL = llverFirst;
                        buttonPosition = 1;
                        break;
                    case R.id.second:
                        aktualLL = llverSecond;
                        buttonPosition = 2;
                        break;
                    case R.id.third:
                        aktualLL = llverThird;
                        buttonPosition = 3;
                        break;
                    case R.id.four:
                        aktualLL = llverFour;
                        buttonPosition = 4;
                        break;
                }

                // check valid number fishka
                if (numFishka.getText().toString().length() != 13) {
                    Toast.makeText(FishkaActivity.this,
                            "Фишка состоит из 13 цифр!!! Вы ввели = " + numFishka.getText().toString().length(),
                            Toast.LENGTH_SHORT).show();

                // check for letter
                } else if (!numFishka.getText().toString().matches("^\\d{13}$")){
                    Toast.makeText(FishkaActivity.this,
                            "Фишка состоит только из ЦИФР", Toast.LENGTH_SHORT).show();

                //check for presence
                } else if ( presence(Integer.parseInt(numFishka.getText().toString().substring(4)))) {
                    Toast.makeText(FishkaActivity.this, "у Вас уже есть такая фишка", Toast.LENGTH_SHORT).show();

                    // create new button
                  }else{
                    lParams.gravity = benGravity;
                    Button btnNew = new Button(this);
                    btnNew.setText(petrol.getText());
                    btnNew.setOnClickListener(onClickListener);
                    aktualLL.addView(btnNew, lParams);
                    btnNew.setId(Integer.parseInt(numFishka.getText().toString().substring(4)));

                    // save button's param. in DB
                    int id = Integer.parseInt(numFishka.getText().toString().substring(4));
                    String name = petrol.getText().toString();

                    cv.put(DBHelper.KEY_ID, id);
                    cv.put(DBHelper.NAME, name);
                    cv.put(DBHelper.POSITION, buttonPosition);
                    cv.put(DBHelper.BARCODE, numFishka.getText().toString());

                    db.insert(DBHelper.TABLE_NAME, null, cv);

                    petrol.setText("");
                    numFishka.setText("");
                }
                break;


                 // delete button
            case R.id.delBut:
                //todo create check method
                if (presence(Integer.parseInt(numFishka.getText().toString().substring(4)))) {

                    ViewGroup layout = (ViewGroup) findViewById(Integer.parseInt(numFishka.getText().toString().substring(4))).getParent();
                    layout.removeView((Button) findViewById(Integer.parseInt(numFishka.getText().toString().substring(4))));

                    db.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + " = " + numFishka.getText().toString().substring(4), null);

                Toast.makeText(FishkaActivity.this,
                        "Фишка удалена", Toast.LENGTH_SHORT).show();

                    numFishka.setText("");

                }else {
                    Toast.makeText(FishkaActivity.this,
                            "Фишки с таким номером у Вас нет", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }


    private boolean presence (int id) {
        boolean check;
        Cursor cursor = db.query(DBHelper.TABLE_NAME, new String[]{DBHelper.KEY_ID}, DBHelper.KEY_ID + " = " + id, null, null, null, null);
        if  (cursor.getCount() > 0){
            check = true;
        }else check = false;

        cursor.close();

        return check;
    }

    private String getBarcode (int id){
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, DBHelper.KEY_ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);

        cursor.moveToFirst();

        String barCode = cursor.getString(cursor.getColumnIndex(DBHelper.BARCODE));

        cursor.close();

        return barCode;
    }
}
