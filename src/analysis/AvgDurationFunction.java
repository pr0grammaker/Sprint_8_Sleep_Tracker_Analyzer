package analysis;

import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class AvgDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long average  = (long) sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult("Средняя продолжительность сессии (мин)", average);
    }
}
