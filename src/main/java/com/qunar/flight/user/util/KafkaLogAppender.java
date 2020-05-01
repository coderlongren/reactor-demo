package com.qunar.flight.user.util;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.IOException;

public class KafkaLogAppender extends AppenderBase<ILoggingEvent> {
    private static int DEFAULT_LIMIT = 10;
    private int counter = 0;
    private int limit = DEFAULT_LIMIT;
    private PatternLayoutEncoder encoder;

    public KafkaLogAppender() {
    }

    public static int getDefaultLimit() {
        return DEFAULT_LIMIT;
    }

    public static void setDefaultLimit(int defaultLimit) {
        DEFAULT_LIMIT = defaultLimit;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        encoder.encode(eventObject);
        System.out.println("kafkaAppender" + eventObject.getFormattedMessage());
    }

    @Override
    public void start() {
        if (this.encoder == null) {
            addError("No layout set for the appender named [" + name + "].");
            return;
        }
        encoder.start();
        super.start();
    }
}
