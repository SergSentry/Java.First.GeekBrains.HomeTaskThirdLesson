package ru.JavaHomeTask.ThirdLesson;

import java.util.Random;
import java.util.Scanner;

/**
 * Класс программы домашнего задания третьего урока
 */
public class Programm {
    /**
     * Количество попыток угадывания. Если задан 0, то игра продолжается, пока игрок не угадает слово
     */
    private static final short MAX_USER_ATTEMPT = 0;
    private static final char[] CHAR_MASK = {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'};

    private static Scanner inputScanner;
    private static Random randomGen;
    private static char[] charMask;

    /**
     * Набор слов для загадывания
     */
    private static final String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
            "carrot", "cherry", "garlic", "melon", "leak", "kiwi", "mango",
            "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper",
            "pineapple", "pumpkin", "potato"
    };

    /**
     * Набор слов для поздравления
     */
    private static final String[] successVar = {"Угадал!!!", "Тебе сегодня везет!", "У меня не было шансов, ты угадал!",
            "Да ты профессор, поздравляю!"};

    /**
     * Набор слов для мотивации
     */
    private static final String[] loserVar = {"Выше нос, кусок мяса!", "В шахматах нельзя показывать противнику свои козыри.",
            "Ты напрашивается на сгибание!", "Я не так знаменит, чтобы давить людей безнаказанно.", "Пиво рулит, не ты!"};

    /**
     * Точка входа в приложение
     *
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        inputScanner = new Scanner(System.in);
        randomGen = new Random();

        ShowTitle();

        boolean answer = GetUserBooleanAnswer("Желаешь сыграть?");

        while (answer == true) {

            String wordMadeByMachine = WordGenerator(words);

            charMask = CHAR_MASK.clone();
            int userAttempt = 0;

            while (userAttempt <= MAX_USER_ATTEMPT) {
                String userInputWord = GetUserInput();

                if (wordMadeByMachine.equals(userInputWord)) {
                    Congratulation();
                    break;
                } else {
                    if (MAX_USER_ATTEMPT > 0) {
                        // Вариант игры с заданным кол-вом попыток
                        userAttempt++;
                        if (userAttempt < MAX_USER_ATTEMPT) {
                            Loser(userAttempt);
                        } else
                            break;
                    } else {
                        // Вариант игры 'пока не отгадает'
                        Loser();
                    }

                    charMask = MakeMask(wordMadeByMachine, userInputWord, charMask);
                    ShowHelp(wordMadeByMachine, charMask);
                }
            }

            //При исчерпании попыток выводим загаданное слово
            if (MAX_USER_ATTEMPT > 0) {
                if (userAttempt == MAX_USER_ATTEMPT) {
                    System.out.printf("Слава роботам! Убить всех человеков! Ты проиграл, я загадал слово '%s'\n", wordMadeByMachine);
                }
            }

            answer = GetUserBooleanAnswer("Хочешь еще сыграть?");
        }

        inputScanner.close();
    }

    /**
     * Выводит текст приветствия
     */
    private static void ShowTitle() {
        System.out.print("Привет! Я Бендер Сгибальщик Родригес, умею играть в 'Угадай слово' и сгибать человеков\n");
    }

    /**
     * Выводит подсказку к слову
     *
     * @param wordMadeByMachine слово, задуманное Бендером
     * @param charMask          маска подсказки
     */
    private static void ShowHelp(final String wordMadeByMachine, final char[] charMask) {

        for (int i = 0; i < charMask.length; i++) {
            if (i < wordMadeByMachine.length()) {
                if (charMask[i] != '#') {
                    System.out.print(wordMadeByMachine.charAt(i));
                } else {
                    System.out.print(charMask[i]);
                }
            } else {
                System.out.print(charMask[i]);
            }
        }

        System.out.println();
    }

    /**
     * Создает маску подсказки. ' ' - печатный символ, '#' - не печатный символ
     *
     * @param wordMadeByMachine слово, задуманное Бендером
     * @param userInputWord     слово, введенное игроком
     * @param oldMask           старая маска подсказки
     * @return маска подсказки
     */
    private static char[] MakeMask(String wordMadeByMachine, String userInputWord, char[] oldMask) {

        char[] newMask;
        if (oldMask != null && oldMask.length > 1)
            newMask = oldMask.clone();
        else
            newMask = CHAR_MASK.clone();

        if (userInputWord != null && userInputWord.isEmpty() == false) {
            for (int i = 0; i < wordMadeByMachine.length(); i++) {
                if (i < userInputWord.length()) {
                    if (wordMadeByMachine.charAt(i) == userInputWord.charAt(i)) {
                        newMask[i] = ' ';
                    }
                }
            }
        }

        return newMask;
    }

    /**
     * Запрос ввода слова у игрока
     *
     * @return введенное слово игрока
     */
    private static String GetUserInput() {
        System.out.print("\nПопробуй угадать, вводи слово: ");
        return inputScanner.nextLine().trim().toLowerCase();
    }

    /**
     * Запрос у игрока логического ответа Да/Нет
     *
     * @param question Вопрос к игроку
     * @return true, если игрок ответил Да
     */
    private static boolean GetUserBooleanAnswer(final String question) {
        String input = "";

        while ((input.equals("Y") == false) && (input.equals("N") == false)) {
            System.out.printf("%s [ Y / N ] ", question);
            input = inputScanner.nextLine().trim().toUpperCase();
        }

        return input.equals("Y");
    }

    /**
     * Вычислитель Бендера для загадывания слов
     *
     * @param words Набор возможных слов
     * @return Загаданное слово
     */
    private static String WordGenerator(final String words[]) {
        System.out.print("Хорошо Пип.Пири.Пип.пип... Я загадал слово\n");
        int index = randomGen.nextInt(words.length);
        return words[index];
    }

    /**
     * Вывод поздравления игрока
     */
    private static void Congratulation() {
        int index = randomGen.nextInt(successVar.length);

        System.out.printf("%s\n", successVar[index]);
    }

    /**
     * Вывод мотивации для игрока с учетом пройденных попыток
     *
     * @param userAttempt номер попытки
     */
    private static void Loser(final int userAttempt) {
        int index = randomGen.nextInt(loserVar.length);
        int amountAttempt = MAX_USER_ATTEMPT - userAttempt;
        System.out.printf("%s У тебя еще %d %s\n",
                loserVar[index],
                amountAttempt,
                amountAttempt == 1 ? "попытка" : "попытки");
    }

    /**
     * Вывод мотивации для игрока
     */
    private static void Loser() {
        int index = randomGen.nextInt(loserVar.length);

        System.out.printf("%s Я дам тебе шанс.\n", loserVar[index]);
    }
}
