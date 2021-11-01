package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class TimeMethods {
    static final Logger logMethodExecutionTime = LoggerFactory.getLogger("result");
    static final StringBuilder results = new StringBuilder();
    private static final String DELIMITER = String.join("", Collections.nCopies(103, "-"));

    public static final ExternalResource REPORT = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            logMethodExecutionTime.info("\n" + DELIMITER +
                    "\nTest                                                               status                  Duration, ms" +
                    "\n" + DELIMITER + "\n" + results + DELIMITER + "\n");
        }
    };
}