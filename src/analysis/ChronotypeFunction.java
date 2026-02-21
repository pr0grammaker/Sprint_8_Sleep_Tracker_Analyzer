package analysis;

import sleeptracker.Chronotype;
import sleeptracker.SleepAnalysisResult;
import sleeptracker.SleepingSession;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        int owls = 0;
        int larks = 0;
        int pigeons = 0;

        for (SleepingSession sleepingSession : sleepingSessions) {

            boolean isNightSession = !sleepingSession.getStart().toLocalDate()
                    .equals(sleepingSession.getEnd().toLocalDate());

            if (!isNightSession){
                continue;
            }

            LocalTime startTime = sleepingSession.getStart().toLocalTime();
            LocalTime endTime = sleepingSession.getEnd().toLocalTime();

            //сова
            if (startTime.isAfter(LocalTime.of(23,0)) && endTime.isAfter(LocalTime.of(9,0))){
                owls++;
            }
            // жаворонок
            else  if (startTime.isBefore(LocalTime.of(22,0)) && endTime.isBefore(LocalTime.of(7,0))){
                larks++;
            }
            // голубь
            else  {
                pigeons++;
            }
        }

        Chronotype chronotype;
        if (owls > larks && owls > pigeons){
            chronotype = Chronotype.OWL;
        } else if (larks > owls && larks > pigeons) {
            chronotype = Chronotype.LARK;
        } else {
            chronotype = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult("Пользователь по хронотипу является ->", chronotype.getDescription());
    }
}
