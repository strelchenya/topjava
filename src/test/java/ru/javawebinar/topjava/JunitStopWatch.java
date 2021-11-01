package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;

public class JunitStopWatch extends Stopwatch {

    private static void logInfo(Description description, String status, long nanos) {
        String result = String.format("%-65s %-29s %7d", description.getMethodName(), status,
                TimeUnit.NANOSECONDS.toMillis(nanos));
        TimeMethods.results.append(result).append('\n');
        TimeMethods.logMethodExecutionTime.info(result + " ms\n");
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }
}
