package com.example.tablesschult;

import java.util.Random;

public class MakeRandomMassive {

    //генератор случайных неповторяющихся чисел
    public static int[] rnd(int length){
        int tmp = 0;
        int[] mass = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++){
            boolean flag = true;
            while (flag){
                tmp = random.nextInt(length + 1);
                flag = isHere(mass, tmp);
            }
            mass[i] = tmp;
        }
        return mass;
    }

    //проверка наличия нового числа в массиве
    private static boolean isHere(int[] mas, int num){
        boolean is = false;
        for (int ma : mas) {
            if (ma == num) {
                is = true;
                break;
            }
        }
        return is;
    }
}
