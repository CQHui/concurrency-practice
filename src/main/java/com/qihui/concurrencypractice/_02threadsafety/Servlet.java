package com.qihui.concurrencypractice._02threadsafety;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * @author chenqihui
 * @date 2020/5/11
 */
public interface Servlet {
    void service(ServletRequest req, ServletResponse resp);

    default BigInteger extractFromRequest(ServletRequest request) {
        return new BigInteger("5");
    }

    default  void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    default BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
