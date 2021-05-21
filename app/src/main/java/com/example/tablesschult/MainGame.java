package com.example.tablesschult;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


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
        mediaPlayer.start();

        //запуск таймера
        chronometer = new Chronometer(this);
        chronometer.setId((int)1000099);
        long startTime = SystemClock.elapsedRealtime();
        chronometer.setBase(startTime);
        chronometer.setFormat("Your time: %s");
        chronometer.start();
        linLayout.addView(chronometer, lpView);

        //кнопка
        Button button = new Button(this);
        button.setTextSize(20);
        button.setOnClickListener(v->{
            if (button.getText() == "button is On Click"){
                button.setText(R.string.start);
            }else {
                button.setText(R.string.exit);
            }
        });
        linLayout.addView(button, lpView);

        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;

        Button button1 = new Button(this);
        button1.setText(R.string.records);
        linLayout.addView(button1, leftMarginParams);






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
