import analysis.TotalSessionsFunction;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSessionsFunctionTest {
    private final  LocalDateTime base = LocalDateTime.now();

    @Test
    void testTotalSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.BAD)
        );

        TotalSessionsFunction totalSessionsFunction = new TotalSessionsFunction();

        SleepAnalysisResult result = totalSessionsFunction.apply(sessions);
        int total = (int) result.getValue();
        assertEquals(2, total);
    }

    @Test
    void testTotalSessionsNull() {
        List<SleepingSession> sessions = new ArrayList<>();

        TotalSessionsFunction totalSessionsFunction = new TotalSessionsFunction();

        SleepAnalysisResult result = totalSessionsFunction.apply(sessions);
        int total = (int) result.getValue();
        assertEquals(0, total);
    }



}
