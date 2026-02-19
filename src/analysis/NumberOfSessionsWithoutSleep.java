package analysis;

import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;

import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class NumberOfSessionsWithoutSleep implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions == null || sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        sleepingSessions = sleepingSessions.stream()
                .sorted(Comparator.comparing(SleepingSession::getStart))
                .toList();

        LocalDate firstDate = sleepingSessions.getFirst().getStart().toLocalDate();
        LocalDate lastDate = sleepingSessions.getLast().getEnd().toLocalDate();

        if (sleepingSessions.getFirst().getStart().getHour() >= 12) {
            firstDate = firstDate.plusDays(1);
        }

        Period period = Period.between(firstDate, lastDate);
        int totalNights = period.getDays() + 1;

        long sleeplessNights = 0;

        for (int i = 0; i < totalNights; i++) {
            LocalDate currentNight = firstDate.plusDays(i);

            LocalDateTime nightStart = currentNight.atTime(0, 0);
            LocalDateTime nightEnd = currentNight.atTime(6, 0);

            boolean sleptThisNight = sleepingSessions.stream()
                    .anyMatch(sleepingSession -> sleepingSession.getStart().isBefore(nightEnd) &&
                            sleepingSession.getEnd().isAfter(nightStart));

            if (!sleptThisNight) {
                sleeplessNights++;
            }
        }
        return new SleepAnalysisResult("Количество бессонных ночей ", sleeplessNights);

    }
}
