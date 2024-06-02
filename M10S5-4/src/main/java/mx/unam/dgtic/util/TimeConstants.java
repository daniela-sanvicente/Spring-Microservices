package mx.unam.dgtic.util;

import java.util.concurrent.TimeUnit;

public class TimeConstants {
    public static final long ONE_SECOND_IN_MILLIS = 1000;
    public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final long ONE_HOUR_IN_MILLIS = 3600000;
    public static final long ONE_DAY_IN_MILLIS = 86400000;

    public static long getExpirationTime(int time, TimeUnit timeUnit){
        switch (timeUnit){
            case SECONDS:
                return time * TimeConstants.ONE_SECOND_IN_MILLIS;
            case MINUTES:
                return time * TimeConstants.ONE_MINUTE_IN_MILLIS;

            case HOURS:
                return time * TimeConstants.ONE_HOUR_IN_MILLIS;

            case DAYS:
                return time * TimeConstants.ONE_DAY_IN_MILLIS;

            default:
                throw new IllegalArgumentException("Unidad de tiempo no soportada: " + timeUnit);
        }
    }
}
