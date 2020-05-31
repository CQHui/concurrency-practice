package com.qihui.concurrencypractice.taskExecution;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author chenqihui
 * @date 2020/5/31
 */
public class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany travelCompany;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany travelCompany, TravelInfo travelInfo) {
        this.travelCompany = travelCompany;
        this.travelInfo = travelInfo;
    }

    @Override
    public TravelQuote call() throws Exception {
        return travelCompany.solicitQuote(travelInfo);
    }

}

class TravelQuote {

}

class TravelCompany {

    public TravelQuote solicitQuote(TravelInfo travelInfo) {
        return new TravelQuote();
    }

}

class TravelInfo {

}

class Quote {
    static ExecutorService exec = Executors.newFixedThreadPool(5);

    public List<TravelQuote> getRankedTravelQuotes(
            TravelInfo travelInfo,
            Set<TravelCompany> companySet,
            Comparator<TravelQuote> ranking,
            long time,
            TimeUnit unit) throws InterruptedException, ExecutionException {
        ArrayList<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companySet) {
            tasks.add(new QuoteTask(company, travelInfo));
        }
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
        ArrayList<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> taskIter = tasks.iterator();
        for (Future<TravelQuote> future : futures) {
            QuoteTask task = taskIter.next();
            quotes.add(future.get());
        }
        Collections.sort(quotes, ranking);
        return quotes;
    }
}

