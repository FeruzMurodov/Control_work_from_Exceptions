import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String[] input = enterData();
        while (checkQtyOfElements(input) != 6) {
            if (checkQtyOfElements(input) == -1) {
                System.out.println("Ошибка! Введено БОЛЬШЕ значения чем требовалось!");
            } else if (checkQtyOfElements(input) == -2) {
                System.out.println("Ошибка! Введено МЕНЬШЕ значения чем требовалось!");
            }
            input = enterData();
        }
        if (checkData(input) == Boolean.TRUE) {
            try {
                saveData(input);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Ошибка при сохранении файла!");

            }
        }
    }

    public static String[] enterData(){
        Scanner iscanner = new Scanner(System.in);
        System.out.println("Введите данные английскими буквами, разделённые пробелом в формате: \nФамилия Имя Отчество дата_рождения(dd.mm.yyyy) номер_телефона пол");
        String[] input = iscanner.nextLine().split(" ");
        return input;
    }

    public static int checkQtyOfElements(String[] array){
        if (array.length > 6) return -1;
        else if (array.length < 6) return -2;
        return array.length;
    }

    public static Boolean checkData(String[] input) throws NumberFormatException{
        if (!checkLetter(input[0])){
            throw new RuntimeException("Фамилия введён неверно. Введите фамилию только английскими буквами!");
        }
        if (!checkLetter(input[1])){
            throw new RuntimeException("Имя введён неверно. Введите имю только английскими буквами!");
        }
        if (!checkLetter(input[2])){
            throw new RuntimeException("Отчество введён неверно. Введите отчество только английскими буквами!");
        }
        try {
            if (checkDate(input[3]) == Boolean.FALSE) {
                throw new RuntimeException("Дата введён неверно!");
            }
        } catch (NumberFormatException e){
            throw new RuntimeException("При вводе даты не указан число");
        }
        try {
            Integer.parseInt(input[4]);
        } catch (NumberFormatException e){
            throw new RuntimeException("В место номера телефона ввели неправильные данные!");
        }
        if (!input[5].equals("m") && !input[5].equals("f")){
            throw new RuntimeException("Для значения пола используйте 'm'(мужчина) или 'f'(женщина)");
        }
        return Boolean.TRUE;
    }

    public static void saveData(String[] input) throws IOException {
        String name = input[0];
        try (FileWriter fw = new FileWriter(name.toString(), true)) {
            for (int i = 0; i < input.length; i++) {
                fw.write("<");
                fw.write(input[i]);
                fw.write(">");
            }
            fw.write("\n");
            fw.flush();
        }
        System.out.println("Данные успешно сохранены!");
    }
    public static Boolean checkLetter(String word){
        boolean containsOnlyLetters = Pattern.matches("[a-zA-Z]+", word);
        if (containsOnlyLetters == true) return Boolean.TRUE;
        return Boolean.FALSE;
    }
    // ---
    public static Boolean checkDate(String word) throws NumberFormatException{
        String[] array = word.split("\\.");
        if (array.length == 3) {
            int day = Integer.parseInt(array[0]);
            int month = Integer.parseInt(array[1]);
            int year = Integer.parseInt(array[2]);
            if (year > 1900 && year < 2025){
                if (year % 4 == 0){
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                        if (day > 0 && day < 32){
                            return Boolean.TRUE;
                        }
                    } else if (month == 2) {
                        if (day > 0 && day < 30){
                            return Boolean.TRUE;
                        }
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day > 0 && day < 31){
                            return Boolean.TRUE;
                        }
                    }
                } else {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                        if (day > 0 && day < 32){
                            return Boolean.TRUE;
                        }
                    } else if (month == 2) {
                        if (day > 0 && day < 29){
                            return Boolean.TRUE;
                        }
                    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day > 0 && day < 31){
                            return Boolean.TRUE;
                        }
                    }
                }
            }

        }
        return Boolean.FALSE;
    }
}