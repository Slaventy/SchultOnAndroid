package com.example.tablesschult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StartActivity extends AppCompatActivity {
    public static final String USER_NAME = "user name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        final EditText editTextName = findViewById(R.id.editText);
        Button exitButton = findViewById(R.id.button);
        Button startButton = findViewById(R.id.start);
        Button recordButton = findViewById(R.id.setting);

        exitButton.setOnClickListener(new View.OnClickListener() {
            //процедура выхода из программы
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();    //получение имени пользователя
                CharSequence text = "please input name";    //сообщение
                int duration = Toast.LENGTH_LONG;   //задержка для сообщения

                //проверка на наличие имени пользователя
                if (name.length() > 0){
                    //создание игрового окна - ТОЧКА ВХОДА
                    Intent intent =new Intent(v.getContext(), MainGame.class);
                    intent.putExtra(USER_NAME, name);
                    startActivity(intent);
                } else {
                    //если имя не введено выводится сообщение
                    Toast toast = Toast.makeText(v.getContext(), text, duration);
                    toast.show();
                }
            }
        });
    }
}