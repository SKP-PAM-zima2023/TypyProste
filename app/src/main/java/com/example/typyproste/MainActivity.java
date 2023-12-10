package com.example.typyproste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonZapisz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

                EditText editText = findViewById(R.id.editText);
                CheckBox checkBox = findViewById(R.id.checkbox);

                //zapis stanu
                prefsEditor.putString("editText", editText.getText().toString());
                prefsEditor.putBoolean("checkbox", checkBox.isChecked());
                prefsEditor.apply();
            }
        });

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String text = sharedPreferences.getString("editText", "");
        boolean value = sharedPreferences.getBoolean("checkbox", false);
        EditText editText = findViewById(R.id.editText);
        CheckBox checkBox = findViewById(R.id.checkbox);
        editText.setText(text);
        checkBox.setChecked(value);

        Button buttonSave = findViewById(R.id.buttonZapiszPlik);
        Button buttonRead = findViewById(R.id.buttonWczytajZpliku);
        EditText editText2 = findViewById(R.id.editText2);

        buttonSave.setOnClickListener((view)->{
            try {
                FileOutputStream fileOutputStream = openFileOutput("data1.txt", MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(editText2.getText().toString());
                outputStreamWriter.close();
                buttonRead.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        buttonRead.setOnClickListener((view)-> {
            try {
                FileInputStream fileInputStream = openFileInput("data1.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                // usuwam ostatni enter
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                bufferedReader.close();
                editText2.setText(stringBuilder.toString());

            } catch (FileNotFoundException e) {
                Toast.makeText(getBaseContext(), "Plik nie istnieje", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        // czy ma byc aktywny?
        try {
            FileInputStream fileInputStream = openFileInput("data1.txt");
            buttonRead.setEnabled(true);
            fileInputStream.close();
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}