package sleeptracker;

import analysis.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class SleepTrackerApp {

    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = new ArrayList<>();

    public SleepTrackerApp() {
        functions.add(new TotalSessionsFunction());
        functions.add(new MinDurationFunction());
        functions.add(new MaxDurationFunction());
        functions.add(new AvgDurationFunction());
        functions.add(new BadQualityCountFunction());
        functions.add(new NumberOfSessionsWithoutSleep());
        functions.add(new ChronotypeFunction());
    }

    public List<SleepAnalysisResult> printAnalysis(List<SleepingSession> sessions){
        return functions.stream().
                map(f -> f.apply(sessions)).
                toList();
    }

    public static void main(String[] args) {
        System.out.println("""
                Добро пожаловать в приложение Sleep Tracker Analyzer!
                Введите путь к Вашему файлу с логом сна:
                """);
        Scanner sc = new Scanner(System.in);
        String pathSleepLogs = sc.next();
        if (pathSleepLogs.isEmpty()){
            System.out.println("Укажите путь к файлу с логом сна");
            return;
        }

        File file = new File(pathSleepLogs);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm", Locale.ENGLISH);
        List<SleepingSession> sessionList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            sessionList = br.lines()
                    .map(line -> line.split(";"))
                    .map(paths -> new SleepingSession(
                            LocalDateTime.parse(paths[0],formatter),
                            LocalDateTime.parse(paths[1],formatter),
                            SleepQuality.valueOf(paths[2])
                    ))
                    .toList();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        if (sessionList.isEmpty()) {
            System.out.println("Файл с логами сна пустой. Нет данных для анализа.");
            return;
        }

        SleepTrackerApp sleepTrackerApp = new SleepTrackerApp();

        sleepTrackerApp.printAnalysis(sessionList).forEach(sleepAnalysisResult
                -> System.out.println(sleepAnalysisResult.getDescription() + " " + sleepAnalysisResult.getValue()));



    }


}