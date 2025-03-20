package com.vti.post.service;

import org.springframework.stereotype.Component;

import java.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DateTimeFormatter {
    Map<Long, Function<Instant, String>> startegyMap = new HashMap<>();

    public DateTimeFormatter() {
        startegyMap.put(60L, this::formatInSeconds);
        startegyMap.put(3600L, this::formatInMinutes);
        startegyMap.put(86400L, this::formatInHours);
        startegyMap.put(Long.MAX_VALUE, this::formatInDate);
    }

    public String format(Instant instant) {
        // Calculate elapsed seconds
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());

        // Find appropriate strategy based on elapsed seconds
        var strategy = startegyMap
                .entrySet()
                .stream()
                .filter(longFunctionEntry ->
                        elapseSeconds < longFunctionEntry.getKey()
                ).findFirst().get();

        // Apply the formatting function found
        return strategy.getValue().apply(instant);
    }


    private String formatInSeconds(Instant instant) {
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return String.format("%s seconds", elapseSeconds);
    }

    private String formatInMinutes(Instant instant) {
        long elapseMinutes = ChronoUnit.MINUTES.between(instant, Instant.now());
        return String.format("%s minutes", elapseMinutes);
    }

    private String formatInHours(Instant instant) {
        long elapseHours = ChronoUnit.HOURS.between(instant, Instant.now());
        return String.format("%s hours", elapseHours);
    }

    private String formatInDate(Instant instant) {
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ISO_DATE;

        return localDateTime.format(dateTimeFormatter);
    }

}


