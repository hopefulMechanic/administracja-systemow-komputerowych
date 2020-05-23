package pl.agh.student.ask.cronTask;

import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.agh.student.ask.cache.DbInfoCache;

import java.sql.Timestamp;
import java.util.List;

import static pl.agh.student.ask.jdbc.JDBCQueriesExecutor.*;
import static pl.agh.student.ask.jdbc.JDBCQueriesExecutor.fetchIOTiming;

@Component
public class DBScraper {

    private static final Logger logger = LoggerFactory.getLogger(DBScraper.class);

    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks using cron jobs - " + now);

        List<Tuple2<String, Timestamp>> fetchLongQueries = fetchLongQueries();
        DbInfoCache.longQueriesList.add(fetchLongQueries);

        String fetchDataBaseSize = fetchDataBaseSize();
        DbInfoCache.dataBaseSizeList.add(fetchDataBaseSize);

        List<Tuple4<String, Integer, Integer, Double>> fetchIndexesAccess = fetchIndexesAccess();
        DbInfoCache.indexesAccessList.add(fetchIndexesAccess);

        List<Tuple2<Integer, Integer>> fetchCommitedAndRolledBackTransactions = fetchCommitedAndRolledBackTransactions();
        DbInfoCache.commitedAndRolledBackTransactionsList.add(fetchCommitedAndRolledBackTransactions);

        List<Tuple4<String, Double, Integer, Integer>> fetchIOTiming = fetchIOTiming();
        DbInfoCache.IOTimingList.add(fetchIOTiming);

        logger.warn(String.join("\n",fetchLongQueries.toString(), fetchDataBaseSize.toString(), fetchIndexesAccess.toString(), fetchCommitedAndRolledBackTransactions.toString(), fetchIOTiming.toString()));
    }
}
