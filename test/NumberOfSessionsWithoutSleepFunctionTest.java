import analysis.NumberOfSessionsWithoutSleep;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberOfSessionsWithoutSleepFunctionTest {

    private final List<SleepingSession> sessions = List.of(
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 1, 22, 15),
                    LocalDateTime.of(2025, 10, 2, 8, 0),
                    SleepQuality.GOOD
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 2, 23, 0),
                    LocalDateTime.of(2025, 10, 3, 8, 0),
                    SleepQuality.NORMAL
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 3, 14, 30),
                    LocalDateTime.of(2025, 10, 3, 15, 20),
                    SleepQuality.NORMAL
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 3, 23, 30),
                    LocalDateTime.of(2025, 10, 4, 6, 20),
                    SleepQuality.BAD
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 5, 7, 30),
                    LocalDateTime.of(2025, 10, 5, 9, 0),
                    SleepQuality.GOOD
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 6, 13, 0),
                    LocalDateTime.of(2025, 10, 6, 14, 0),
                    SleepQuality.NORMAL
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 7, 22, 0),
                    LocalDateTime.of(2025, 10, 8, 8, 0),
                    SleepQuality.GOOD
            ),
            new SleepingSession(
                    LocalDateTime.of(2025, 10, 9, 7, 0),
                    LocalDateTime.of(2025, 10, 9, 10, 0),
                    SleepQuality.BAD
            ));


    @Test
    void NumberOfSessionsWithoutSleep() {
        NumberOfSessionsWithoutSleep NumberOfSessionsWithoutSleep = new NumberOfSessionsWithoutSleep();

        SleepAnalysisResult result = NumberOfSessionsWithoutSleep.apply(sessions);
        long count = (long) result.getValue();
        assertEquals(4, count);

    }

    @Test
    void NumberOfSessionsWithoutSleepNull() {
        List<SleepingSession> sessions = new ArrayList<>();
        NumberOfSessionsWithoutSleep NumberOfSessionsWithoutSleep = new NumberOfSessionsWithoutSleep();

        SleepAnalysisResult result = NumberOfSessionsWithoutSleep.apply(sessions);
        long count = (long) result.getValue();
        assertEquals(0, count);

    }

    @Test
    void fullNightSleep() {
        List<SleepingSession> sessions = List.of(new SleepingSession(
                LocalDateTime.of(2025, 10, 10, 1, 0),
                LocalDateTime.of(2025, 10, 10, 5, 0),
                SleepQuality.GOOD)
        );
        NumberOfSessionsWithoutSleep NumberOfSessionsWithoutSleep = new NumberOfSessionsWithoutSleep();

        SleepAnalysisResult result = NumberOfSessionsWithoutSleep.apply(sessions);
        long count = (long) result.getValue();
        assertEquals(0, count);
    }

    @Test
    void sleepBeforeNightOverlaps() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 10, 23, 30),
                        LocalDateTime.of(2025, 10, 11, 3, 30),
                        SleepQuality.GOOD
                )
        );

        NumberOfSessionsWithoutSleep function = new NumberOfSessionsWithoutSleep();
        SleepAnalysisResult result = function.apply(sessions);
        assertEquals(0L, result.getValue());
    }

    @Test
    void sleepAfterNight(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 10, 7, 30),
                        LocalDateTime.of(2025, 10, 11, 8, 30),
                        SleepQuality.GOOD
                )
        );

        NumberOfSessionsWithoutSleep function = new NumberOfSessionsWithoutSleep();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void sleepBeforeNight(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 10, 7, 0),
                        LocalDateTime.of(2025, 10, 10, 8, 0),
                        SleepQuality.GOOD
                )
        );

        NumberOfSessionsWithoutSleep function = new NumberOfSessionsWithoutSleep();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void consecutiveSleeplessNights(){
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 10, 7, 0),
                        LocalDateTime.of(2025, 10, 10, 8, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 11, 7, 0),
                        LocalDateTime.of(2025, 10, 11, 8, 0),
                        SleepQuality.NORMAL
                )

        );

        NumberOfSessionsWithoutSleep function = new NumberOfSessionsWithoutSleep();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(2L, result.getValue());
    }

    @Test
    void nightOverMonthChange() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 31, 23, 0),
                        LocalDateTime.of(2025, 11, 1, 5, 0),
                        SleepQuality.GOOD
                )
        );

        NumberOfSessionsWithoutSleep function = new NumberOfSessionsWithoutSleep();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue());
    }

}
