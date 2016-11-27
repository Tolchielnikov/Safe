package dev.zopa.safe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final Context context = this;

    private static final String HTTP = "http://";
    public static final String PASSWORD = "pas";
    SharedPreferences mSettings;

    public EditText inquiry;
    public Button search;
    public TextView textView;
    public TextView newPasText;
    public EditText inputNewPas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inquiry = (EditText) findViewById(R.id.inquiry);
        inputNewPas = (EditText) findViewById(R.id.input_newPas);
        textView = (TextView) findViewById(R.id.textView);
        newPasText = (TextView) findViewById(R.id.newPasText);
        search = (Button) findViewById(R.id.search);
        mSettings = getSharedPreferences(PASSWORD, MODE_PRIVATE);
        final Intent intent = new Intent(this, FishkaActivity.class);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (inquiry.getText().toString().equals(mSettings.getString(PASSWORD,""))) {

                    // open FishkaActivity
                    startActivity(intent);

                    //clear EditText's text
                    inquiry.setText("");

                    return;
                }

                // вводим новый пароль
                if (inquiry.getText().toString().equals("save a new pas.:")){

                    //Получаем вид с файла prompt.xml, который применим для диалогового окна:
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.dialog_savepas, null);

                    //Создаем AlertDialog
                    AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                    //Настраиваем prompt.xml для нашего AlertDialog:
                    mDialogBuilder.setView(promptsView);

                    //Настраиваем отображение поля для ввода текста в открытом диалоге:
                    final EditText userInput = (EditText) promptsView.findViewById(R.id.input_newPas);

                    mDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("сохранить",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //Вводим текст и сохраняем
                                            //password = userInput.getText().toString();
                                            SharedPreferences.Editor ed = mSettings.edit();
                                            ed.putString(PASSWORD, userInput.getText().toString());
                                            ed.commit();
                                            Toast.makeText(MainActivity.this, "Кодовое слово измененно", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                    //create AlertDialog:
                    AlertDialog alertDialog = mDialogBuilder.create();

                    //show dialog
                    alertDialog.show();

                    //clear EditText's text
                      inquiry.setText("");

               }else if  (inquiry.getText().toString().matches("^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$")){
                    if (inquiry.getText().toString().contains("http://")){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(inquiry.getText().toString())));}else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(HTTP + inquiry.getText().toString())));}

                }else {

                    final String googleSech = "http://www.google.com/#q=" + inquiry.getText().toString();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(googleSech)));
                }
            }
        });
    }
}
