package com.example.tablesschult;



import java.util.HashMap;
import java.util.Map;


public class Records  {
    private static Records records;
    HashMap<String, Long> tables;


    private Records(){
        tables = new HashMap<>();
    }

    public static Records getInstance(){
        if (records == null){
            records = new Records();
        }
        return records;
    }

    public void addString(String userName, Long time){
        if (!tables.containsKey(userName)){
            tables.put(userName, time);
            return;
        }
        for (Map.Entry<String, Long> entry : tables.entrySet()) {
            if (entry.getKey().equals(userName)){
                if (entry.getValue()> time){
                    tables.put(userName, time);
                }
            }
        }
    }


    public HashMap<String, Long> getTablesArrayListString(){
        return tables;
    }



}
