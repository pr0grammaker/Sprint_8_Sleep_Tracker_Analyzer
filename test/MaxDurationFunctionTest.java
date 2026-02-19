import analysis.MaxDurationFunction;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDurationFunctionTest {
    private final  LocalDateTime base = LocalDateTime.now();
    @Test
    void maxDurationTest() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.BAD));

        MaxDurationFunction maxDurationFunction = new MaxDurationFunction();

        SleepAnalysisResult result = maxDurationFunction.apply(sessions);
        long max  = (long) result.getValue();
        assertEquals(480,max);

    }

    @Test
    void maxDurationTestSameValue() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(8), SleepQuality.BAD));

        MaxDurationFunction maxDurationFunction = new MaxDurationFunction();

        SleepAnalysisResult result = maxDurationFunction.apply(sessions);
        long max  = (long) result.getValue();
        assertEquals(480,max);

    }

}
