package analysis;

import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;

import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;

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

        List<SleepingSession> finalSleepingSessions = sleepingSessions;
        long sleeplessNights = LongStream.range(0, totalNights)
                .mapToObj(firstDate::plusDays)
                .filter(date -> !sleptThisNight(date, finalSleepingSessions))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей ", sleeplessNights);

    }

    private boolean sleptThisNight(LocalDate currentNight, List<SleepingSession> sessions) {

        LocalDateTime nightStart = currentNight.atTime(0, 0);
        LocalDateTime nightEnd = currentNight.atTime(6, 0);

        return sessions.stream()
                .anyMatch(sleepingSession -> sleepingSession.getStart().isBefore(nightEnd) &&
                                sleepingSession.getEnd().isAfter(nightStart));
    }
}
