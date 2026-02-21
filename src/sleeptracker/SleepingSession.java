package sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime start, LocalDateTime end, SleepQuality sleepQuality) {
        this.start = start;
        this.end = end;
        this.sleepQuality = sleepQuality;
    }

    public long getDurationInMinutes(){
        return Duration.between(start,end).toMinutes();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }


}
