package com.example.tablesschult;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
    private MediaPlayer mediaPlayer;    //медиаплеер
    private int preButton = 0;          //номер уже нажатой кнопки
    private int correctlyPushedButtons = 0; //количество нажатых кнопок
    private LinearLayout linLayout;     //разметка
    private Chronometer chronometer;    //таймер
    private long startTime = 0;         //время таймера
    private String userName;            //имя пользователя
    private Long bestTime = 0L;         //лучшее время пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //запуск музыкального сопровождения
        mediaPlayer = MediaPlayer.create(this, R.raw.musik);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        chronometer = new Chronometer(this);
        int CHRONOMETER = 1000099;  //id хронометра
        chronometer.setId(CHRONOMETER);
        startGame();
    }

    private void startGame(){
        //Формирование разметки
        linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // устанавливаем linLayout как корневой элемент экрана
        setContentView(linLayout, linLayoutParam);

        //параметр слоя для добавляемых элементов
        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //получение информации об имени пользователя
        Intent intent = getIntent();
        userName = intent.getStringExtra(StartActivity.USER_NAME);

        //создание заголовка с именем пользователя
        TextView textView = new TextView(this);
        String title  = "Your name: " + userName;
        textView.setText(title);
        textView.setLayoutParams(lpView);
        linLayout.addView(textView);

        //запуск таймера
        startTime = SystemClock.elapsedRealtime();
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
        if (view.getId() - preButton == 1){
            preButton = view.getId();       //пересохраняем номер кнопки
            findViewById(view.getId()).setBackgroundColor(Color.GREEN); // меняем цвет кнопки или
            correctlyPushedButtons++;    //добавляю счетчик правильно нажатых кнопок
        }else {
            Toast toast1 = Toast.makeText(view.getContext(), "не верно",Toast.LENGTH_LONG);
            toast1.show();
        }
        //когда количество правильно нажатых кнопок совпадет с общим количеством
        //кнопок, то заканчивается игра
        if (correctlyPushedButtons == (9) ){
            //удачные расклады
//            goodDeal++; //увеличение счетчика удачных раскладов
//            if (goodDeal == 2){
//                goodDeal = 0;
//                row++;
//                col++;
//            }
//            Game.getGame().setResult(col, row);
            this.ResetGame();   //сброс поля и генерация нового с текущими настройками
        }

    }

    //сброс игры
    private void ResetGame(){
        linLayout.removeAllViews(); //стирание разметки
        correctlyPushedButtons = 0; //сброс нажатых кнопок
        preButton = 0;      //сброс нажатых кнопок
        long elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;  //получаем время
        if (bestTime == 0) {    //если время еще не ставили
            bestTime = elapsedMillis;   //ставим текущий результат
        }else if (bestTime > elapsedMillis){    //если уже было лучшее время сравниваем
            bestTime = elapsedMillis;   //устанавливаем лучшее время
        }
        startTime = 0;  //сброс таймера
        Toast.makeText(this, userName + " " + bestTime, Toast.LENGTH_LONG).show();
        startGame();    //начало новой игры
    }

    //формирование кнопки
    private Button addFieldNumber(int i, View.OnClickListener onClickListener){
        Button button = new Button(this);
        button.setId(i);
        button.setText(String.valueOf(i));
        button.setTextSize(20);
        button.setWidth(20);
        button.setHeight(300);
        button.setOnClickListener(onClickListener);
        return button;
    }

    //закрытие
    @Override
    public void onDestroy() {
        //при закрытии происходит остановка
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            stopPlay();
        }
    }

    //остановка проигрывания музыки
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

    //кнопка назад
    @Override
    public void onBackPressed(){
        chronometer.stop();
        openQuitDialog();
    }

    //диалоговой окно о подтверждении выхода из программы
    private void openQuitDialog(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Выход: Вы уверены?");
        quitDialog.setPositiveButton("Да", (dialog, which) -> {
            Records.getInstance().addString(userName, bestTime); //новый класс
            finish();
        });
        quitDialog.setNegativeButton("Нет", (dialog, which) -> chronometer.start());
        quitDialog.show();
    }


}