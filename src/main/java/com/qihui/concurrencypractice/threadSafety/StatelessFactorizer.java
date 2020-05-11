package com.qihui.concurrencypractice.threadSafety;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Stateless objects are always thread-safe
 * @author chenqihui
 * @date 2020/5/11
 */
@ThreadSafe
public class StatelessFactorizer implements Servlet{

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }
}
