import analysis.MinDurationFunction;
import analysis.TotalSessionsFunction;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepTrackerApp;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinDurationFunctionTest {
    private final  LocalDateTime base = LocalDateTime.now();
    
    @Test
    void minDurationTest(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.BAD)
        );

        MinDurationFunction minDurationFunction = new MinDurationFunction();

        SleepAnalysisResult result = minDurationFunction.apply(sessions);
        long minMinutes = (long) result.getValue();
        assertEquals(60, minMinutes);
    }

    @Test
    void minDurationTestSameValues(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(1), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.BAD)
        );

        MinDurationFunction minDurationFunction = new MinDurationFunction();

        SleepAnalysisResult result = minDurationFunction.apply(sessions);
        long minMinutes = (long) result.getValue();
        assertEquals(60, minMinutes);
    }
}
