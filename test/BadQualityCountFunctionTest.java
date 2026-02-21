import analysis.BadQualityCountFunction;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadQualityCountFunctionTest {
    private final  LocalDateTime base = LocalDateTime.now();

    @Test
    void badQualityCountTestNormal(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(6), SleepQuality.NORMAL),
                new SleepingSession(base, base.plusMinutes(45), SleepQuality.BAD),
                new SleepingSession(base, base.plusHours(5), SleepQuality.NORMAL),
                new SleepingSession(base, base.plusHours(2), SleepQuality.BAD)
        );

        BadQualityCountFunction badQualityCountFunction = new BadQualityCountFunction();

        SleepAnalysisResult result = badQualityCountFunction.apply(sessions);
        long count  = (long) result.getValue();
        assertEquals(2,count);
    }

    @Test
    void badQualityCountTestZero(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.GOOD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.GOOD));

        BadQualityCountFunction badQualityCountFunction = new BadQualityCountFunction();

        SleepAnalysisResult result = badQualityCountFunction.apply(sessions);
        long count  = (long) result.getValue();
        assertEquals(0,count);
    }

    @Test
    void badQualityCountTestMax(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(base, base.plusHours(8), SleepQuality.BAD),
                new SleepingSession(base, base.plusHours(1), SleepQuality.BAD),
                new SleepingSession(base, base.plusMinutes(45), SleepQuality.BAD),
                new SleepingSession(base, base.plusHours(5), SleepQuality.BAD),
                new SleepingSession(base, base.plusHours(2), SleepQuality.BAD)
        );

        BadQualityCountFunction badQualityCountFunction = new BadQualityCountFunction();

        SleepAnalysisResult result = badQualityCountFunction.apply(sessions);
        long count  = (long) result.getValue();
        assertEquals(5,count);
    }




}
