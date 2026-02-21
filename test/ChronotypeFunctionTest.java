import analysis.ChronotypeFunction;
import org.junit.jupiter.api.Test;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepQuality;
import sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChronotypeFunctionTest {
    @Test
    void ChronotypePIGEONTest() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 15),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 8, 0),
                        SleepQuality.NORMAL
                ));

        ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
        SleepAnalysisResult result = chronotypeFunction.apply(sessions);

        assertEquals("Голубь", result.getValue());
    }

    @Test
    void chronotypeOWLTest() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 45),
                        LocalDateTime.of(2025, 10, 3, 9, 30),
                        SleepQuality.NORMAL
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Сова", result.getValue());
    }

    @Test
    void chronotypeLARKTest() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 45),
                        LocalDateTime.of(2025, 10, 3, 6, 15),
                        SleepQuality.NORMAL
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Жаворонок", result.getValue());
    }

    @Test
    void chronotypeTieReturnsPigeon() {
        List<SleepingSession> sessions = List.of(
                //Сова
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        SleepQuality.GOOD
                ),
                //Жаворонок
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Голубь", result.getValue());
    }

    @Test
    void boundaryTimeNotOwl() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 9, 0),
                        SleepQuality.GOOD
                )
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Голубь", result.getValue());
    }


}
