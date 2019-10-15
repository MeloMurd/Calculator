package com.company;

public class Main {

    public static void main(String[] args) {

        Expression a = new Expression();
           a.createFirstField();
        a.testFirstField();
//        a.createSecondField();    // методы, если первым путём идти, который у меня почему то не получается зараза
//        a.testSecondField();      // а именно третий список не хочет нормально создаваться - почему то элементы со второго списка не хотят
//        a.createThirdField();    // "групироваться" в многозначные числа и добавляются в третий список через один (каждый второй  элемент - пустой почему то)
        a.createThirdField3();
        a.convertToDigit();
        a.createSymbolList();
        a.testThirdField();
        a.calculate();
    }
}
