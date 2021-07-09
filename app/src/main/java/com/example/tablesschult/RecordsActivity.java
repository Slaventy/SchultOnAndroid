package com.example.tablesschult;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;


public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);

        TableLayout tableLayout = findViewById(R.id.tableRecords);
        TableRow row ;
        HashMap<String, Long> map = Records.getInstance().getTablesArrayListString();
        TextView textViewName;
        TextView textViewTime;

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        for(Map.Entry<String, Long> entry: map.entrySet()){
            row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            row.setLayoutParams(layoutParams);

            textViewName = new TextView(this);
            textViewName.setTextSize(20);
            textViewName.setTextColor(getResources().getColor(R.color.rowTableTextRecords));
            textViewName.setGravity(Gravity.CENTER);
            textViewName.setText(entry.getKey());


            textViewTime = new TextView(this);
            textViewTime.setTextSize(20);
            textViewTime.setTextColor(getResources().getColor(R.color.rowTableTextRecords));
            textViewTime.setGravity(Gravity.CENTER);
            textViewTime.setText(String.valueOf(entry.getValue()));

            row.addView(textViewName);
            row.addView(textViewTime);
            tableLayout.addView(row);
        }
    }
}
