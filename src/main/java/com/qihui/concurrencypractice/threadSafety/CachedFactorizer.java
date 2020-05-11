package com.qihui.concurrencypractice.threadSafety;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * Liveness and performance
 */
@ThreadSafe
public class CachedFactorizer implements Servlet{
    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long  cacheHits;

    public long getHits() {
        return hits;
    }

    public double getCacheHitRatio() {
        return (double) cacheHits /(double) hits;
    }


    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        //avoid holding a lock during lengthy computations or operations at risk of not completing quickly such as I/O
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors;
            }
        }
        encodeIntoResponse(resp, lastFactors);

    }
}
