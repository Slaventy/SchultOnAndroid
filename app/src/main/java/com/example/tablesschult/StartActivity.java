package com.example.tablesschult;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class StartActivity extends AppCompatActivity {
    public static final String USER_NAME = "user name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //переменные и объекты
        final EditText editTextName = findViewById(R.id.editText);
        Button exitButton = findViewById(R.id.button);
        Button startButton = findViewById(R.id.start);
        Button recordButton = findViewById(R.id.setting);

        //процедура выхода из программы
        exitButton.setOnClickListener(v -> {
            openQuitDialog();
        });

        //вывод таблицы рекордов
        recordButton.setOnClickListener(v -> {
            //создание  окна рекордов - ВХОД
            Intent intentRecords =new Intent(v.getContext(), RecordsActivity.class);
            startActivity(intentRecords);
        });

        //запуск игровой сессии
        startButton.setOnClickListener(v -> {
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
        });

        //получаем
    }

    //кнопка назад
    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        openQuitDialog();
    }

    //кнопка назад или выход
    private void openQuitDialog(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });

        quitDialog.show();
    }

}