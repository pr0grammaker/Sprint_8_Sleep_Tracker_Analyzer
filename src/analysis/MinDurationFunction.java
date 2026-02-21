package analysis;

import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long min = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0);

        return new SleepAnalysisResult("Минимальная продолжительность сессии (мин)", min);
    }
}
