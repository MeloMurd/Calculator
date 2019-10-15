package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    /**
     * "Выражение". Описывает строки с уравнением, поставляемые пользователям (пока что в виде строки String,
     * в дальнейшем в виде текстового файла.). Программой выражение преобразуется в массив (альтернативно в список),
     * после чего "перебирается" каждый элемент (в классе Element).
     **/
    int result;                         // окончательный результат вычислений (переменная пока типа инт, потом можно будет передалать)
    String firstField = new String();   // (1) строка с уравнением
    ArrayList<String> secondField = new ArrayList<>(); // (2)  список после конвертации в него строки с уравнением 1
    ArrayList<String> thirdField = new ArrayList<>(); // (3) Окончательный список с многозначными элементами из списка 2
    ArrayList<String> symbolField = new ArrayList<>(); // поле для арифметических символов (АЛЬТЕРНАТИВА ЧЕРЕЗ ПАТТЕРНЫ)
    ArrayList<Integer>digitField = new ArrayList<>(); // поле для цифр (уже преобразованных) (АЛЬТЕРНАТИВА ЧЕРЕЗ ПАТТЕРНЫ)

    Scanner scan = new Scanner(System.in); // сканнер для ввода пользователем уравнения


    // Создание строки с уравнением через запрос пользователю сканером
    public String createFirstField() {
        System.out.println("Введите строку с уравнением: ");
        firstField = scan.nextLine();
        return firstField;
    }

    // TEST First field
    public void testFirstField() {
        System.out.println("----------------------------");
        System.out.println("Testing first field...");
        System.out.println(firstField);

    }

    // Конвертация строки в строчный список
    public ArrayList<String> createSecondField(){
        String[] array = firstField.split(""); // Преобразовал строку в массив строк (посимвольно)
        for (int i=0; i<array.length;i++){
            secondField.add(array[i]);
        }
        return secondField;
    }

     // TEST second field
     public void testSecondField (){
         System.out.println("------------------");
         System.out.println("Testing second field...");
         int count = 0;
         for (String i: secondField) {
             System.out.println("index "+count+": "+i);
             count++;
         }
         System.out.println();
      }

      // Конвертация списка в список с элементами в виде многозначных чисел и арифметических знаков
    public ArrayList<String> createThirdField(){
        String buf = new String();  // Буффер для чисел (пока в виде стрингов)
        for (int i=0; i<secondField.size(); i++){  // Перебираем второй список
            if (secondField.get(i)=="1"){  //Если элемент равен 1...
                buf = buf.concat(secondField.get(i));  //...записываем его в строку-буфер (дополняем имеющуюся для многозначности)
            }
            else {                         // Если элемент не число (пока что не единица)
                thirdField.add(buf);       // "Присваиваем" значение буфера следующему элементу нового списка
                buf="";                    // "Очищаем" буфер для следующих многозначных чисел
                thirdField.add(secondField.get(i)); // Присваиваем элемент (который не единица) следующему элементу нового списка
                // и вот тут происходит непонятная содомия................... ((((((((
                // проблема скорее всего именно со строкой-буфера.....
            }
        }
        return thirdField;
    }

    // Альтернативный метод для создания списка с многозначными числами через Стринг-Билдер
    // (Спойлер : такая же херня, как и с предыдущим методом)
    public ArrayList<String> createThirdField2(){
        String buff="";
        StringBuilder stB = new StringBuilder(buff);
        for (int i =0; i<secondField.size(); i++){
            if(secondField.get(i)=="1") {
                stB.append(secondField.get(i));
            }
            else {
            thirdField.add(stB.toString());
            stB.delete(0, buff.length());
            thirdField.add(secondField.get(i));
            }
        }
        return thirdField;
    }



    /** Дальше пошёл второй способ решения єтого бреда - через паттерны...)
     */
    // метод для "выкорчёвывания" цифр из  строки в отдельный список
    public ArrayList<String> createThirdField3(){
        Pattern pattern=Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(firstField);
        while (matcher.find()){
            //System.out.println(firstField.substring(matcher.start(),matcher.end()) );
            thirdField.add(matcher.group());
        }
        return thirdField;
    }
    // метод для "выкорчёвывания арифметических знаков в отдельный список
    public ArrayList<String> createSymbolList(){
        Pattern pattern =Pattern.compile("(\\+|-|\\*|/)+");
        Matcher matcher = pattern.matcher(firstField);
        while (matcher.find()){
            symbolField.add(matcher.group());
        }
        return symbolField;
    }

    // Приведение строчного списка с цифрами к целочисленному
    public ArrayList<Integer> convertToDigit(){
        for (int i =0; i<thirdField.size(); i++){
            digitField.add(Integer.parseInt(thirdField.get(i)));
        }
        return digitField;
    }

    // TEST Метод проверки разных списков :
    public void testThirdField(){
        System.out.println("-----------------------------");
        System.out.println("Testing third field......");  // Проверка строчного списка со строками-множественными числами
        int count = 0;
        for (String i: thirdField){
            System.out.println("index "+count+": "+i);
            count++;
        }
        System.out.println();
        System.out.println("Testing digit field......");  // Проверка списка с числами (уже в Integer которые)
        int countt = 0;
        for (Integer i: digitField){
            System.out.println("index "+countt+": "+i);
            countt++;
        }
        System.out.println();
        System.out.println("Testing symbol field......");  // Проверка списка с арифметическими символами
        int counttt = 0;
        for (String i: symbolField){
            System.out.println("index "+counttt+": "+i);
            counttt++;
        }
        System.out.println();
    }
    // Метод вычисления уравнения (работает неправильно пока - потом допилю......)
    public int calculate(){
        int buf=0;
        for (int i = 0; i<symbolField.size(); i++){
            switch (symbolField.get(i)){
                case "+" : buf = digitField.get(i)+ digitField.get(i+1); break;
                case "-" : buf = digitField.get(i)- digitField.get(i+1); break;
                case "*" : buf = digitField.get(i)* digitField.get(i+1); break;
                case "/" : buf = digitField.get(i)/ digitField.get(i+1); break;
            }
        }
         result = buf;
        System.out.println("Результат вычисления: "+result);
        return result;
    }

    }


    


