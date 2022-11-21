import java.util.List;
import java.util.Scanner;

public class cals  {
  static Scanner in = new Scanner(System.in);
  public static void main(String[] input) throws Exception {
      while (true) {
          System.out.println("Введите числа с пробелами: ");
          String example = in.nextLine();
          String[] num = example.split(" ");
          if (num.length < 3) {
              throw new Exception("строка не является математической операцией");
          }
          if (num.length > 3) {
              throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
          }
          String x = num[0];
          String y = num[2];
          String op = num[1];
          int num1 = tryParseInt(x); // попытка спарсить строку в int, при не удаче вернется -1
          int num2 = tryParseInt(y); // попытка спарсить строку в int, при не удаче вернется -1
          if ((num1 < 0 && num2 > 0) || (num2 < 0 && num1 > 0)) {
              throw new Exception("используется одновременно разные системы счисления");
          }
          if (num1 > 10 || num2 > 10) {
              throw new Exception("не должно превышать 10");
          }
          if (num1 < 0 && num2 < 0) { // если корректно введены 2 римских числа
              int numb1 = romanToArabic(x); // из III -> 3
              int numb2 = romanToArabic(y);
              int resArab = operationForArab(op, numb1, numb2);
              String resRoman = arabicToRoman(resArab);
              System.out.println("Ответ: " + resRoman);
          } else { // если корректо введены 2 арабских числа
              System.out.println("Ответ: " + operationForArab(op, num1, num2));
          }
      }
  }

  private static int tryParseInt(String value) throws Exception {
    try {
      int res = Integer.parseInt(value);
      if (res < 1) {
        throw new Exception("не должно быть меньше 1");
      }
      return res;
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private static int operationForArab(String op, int num1, int num2) throws Exception {
    switch (op){
      case "+":
        return (num1+num2);
      case "-":
        return (num1 - num2);
      case "/":
        return (num1 / num2);
      case "*":
        return (num1 * num2);
    }
    throw new Exception("отсутствует операция");
  }

  private static int romanToArabic(String input) {
    String romanNumeral = input.toUpperCase();
    int result = 0;   
    List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
    int i = 0;
    while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
        RomanNumeral symbol = romanNumerals.get(i);
        if (romanNumeral.startsWith(symbol.name())) {
            result += symbol.getValue();
            romanNumeral = romanNumeral.substring(symbol.name().length());
        } else {
            i++;
        }
    }
    if (romanNumeral.length() > 0) {
        throw new IllegalArgumentException(input + "Не может быть римским числом");
    }
    return result;
  }

  private static String arabicToRoman(int number) {
      if ((number <= 0) || (number > 4000)) {
          throw new IllegalArgumentException(number + "Числа должны быть больше 0");
      }
      List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
      int i = 0;
      StringBuilder sb = new StringBuilder();
      while ((number > 0) && (i < romanNumerals.size())) {
          RomanNumeral currentSymbol = romanNumerals.get(i);
          if (currentSymbol.getValue() <= number) {
              sb.append(currentSymbol.name());
              number -= currentSymbol.getValue();
          } else {
              i++;
          }
      }
      return sb.toString();
  }
}
