package com.example.tablesschult;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Точка запуска игрового поля
 * */
public class MainGame extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Chronometer chronometer;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создание LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(linLayout, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        //получение информации об имени пользователя
        Intent intent = getIntent();
        String userName = "Your name: " + intent.getStringExtra(StartActivity.USER_NAME);

        textView = new TextView(this);
        textView.setText(userName);
        textView.setLayoutParams(lpView);
        linLayout.addView(textView);

        //запуск музыкального сопровождения
        mediaPlayer = MediaPlayer.create(this, R.raw.musik);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        //запуск таймера
        chronometer = new Chronometer(this);
        chronometer.setId((int)1000099);
        long startTime = SystemClock.elapsedRealtime();
        chronometer.setBase(startTime);
        chronometer.setFormat("Your time: %s");
        chronometer.start();
        linLayout.addView(chronometer, lpView);


        //создание разметки поля layout
        TableLayout tableLayout = new TableLayout(this);        //таблица
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));                //параметры таблицы
        tableLayout.setStretchAllColumns(true);                         //растянули


        int[] numbers = MakeRandomMassive.rnd(9);               //массив случайных чисел
        TableRow tableRow;
        int count =0;
        //строка
        for (int i = 0; i < 3; i++){
            tableRow = new TableRow(this);
            for (int j = 0; j < 3; j++){
                tableRow.addView(addFieldNumber(numbers[count++], this::press));
            }
            tableLayout.addView(tableRow);
        }
        //добавим таблицу на экран
        linLayout.addView(tableLayout);


    }

    //если нажали кнопку
    private void press(View view) {
        String s = String.valueOf(view.getId());
        Toast toast = Toast.makeText(view.getContext(), s,Toast.LENGTH_LONG);
        toast.show();
    }


    private Button addFieldNumber(int i, View.OnClickListener onClickListener){
        Button button = new Button(this);
        button.setId(i);
        button.setText(String.valueOf(i));
        button.setTextSize(20);
        button.setOnClickListener(onClickListener);
        return button;
    }
    

    @Override
    public void onDestroy() {
        //при закрытии происходит остановка
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            stopPlay();
        }
    }

    private void stopPlay(){
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}