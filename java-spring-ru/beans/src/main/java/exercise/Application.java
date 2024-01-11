package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

// BEGIN

// END

@SpringBootApplication
public class Application {

    private final LocalTime day = LocalTime.of(5,59,59);
    private final LocalTime night = LocalTime.of(22,0,0);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @RequestScope
    @Bean
    public Daytime getCurrentTime() {
        LocalTime localTime = LocalTime.now();
        if (localTime.isAfter(day)
                && localTime.isBefore(night)) {
            return new Day();
        } else
            return new Night();

    }
    // END
}
