package com.qihui.concurrencypractice._02threadsafety;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * Locking
 */
@ThreadSafe
public class SynchoriizedFactorizer implements Servlet{
    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;

    /**
     *  thread-safe, but don't do this. Poor concurrency.
     */
    @Override
    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(resp, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }
}

class Widget {
    public synchronized void doSomething() {
        //do something
    }
}

class LoggingWidget extends Widget {
    /**
     * If intrinsic locks were not reentrant, code would be dead lock.
     */
    @Override
    public synchronized void doSomething() {
        System.out.println("calling doSomething");
        super.doSomething();
    }
}
