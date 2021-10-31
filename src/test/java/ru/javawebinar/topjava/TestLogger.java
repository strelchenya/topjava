package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Date;

public class TestLogger implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Date dateBefore = new Date();
                base.evaluate();
                Date dateAfter = new Date();
                long methodExecutionTime = dateAfter.getTime() - dateBefore.getTime();
                String result = String.format("%-95s %7d", description.getDisplayName(), methodExecutionTime);
                TimeMethods.results.append(result).append('\n');
                TimeMethods.logMethodExecutionTime.info(result + " ms\n");
            }
        };
    }
}
