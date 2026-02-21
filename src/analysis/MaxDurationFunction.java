package analysis;

import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long max  = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0);

        return new SleepAnalysisResult("Максимальная продолжительность сессии (мин)", max);
    }
}
