package pl.agh.student.ask.cronTask;

import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.agh.student.ask.cache.DbInfoCache;
import pl.agh.student.ask.jdbc.JDBCQueriesExecutor;

import java.sql.Time;

@Component
public class DBScraper {

    private static final Logger logger = LoggerFactory.getLogger(DBScraper.class);

    @Autowired
    private JDBCQueriesExecutor jdbcQueriesExecutor;

    @Scheduled(cron = "${spring.dbscraper.cronexpression}")
    public void scheduleTaskUsingCronExpression() {
        long now = System.currentTimeMillis() / 1000;
        System.out.println("schedule tasks using cron jobs - " + now);

        Tuple2<String, Time> fetchLongQueries = jdbcQueriesExecutor.fetchLongQueries();
        DbInfoCache.longQueriesList.add(fetchLongQueries);

        String fetchDataBaseSize = jdbcQueriesExecutor.fetchDataBaseSize();
        DbInfoCache.dataBaseSizeList.add(fetchDataBaseSize);

        Tuple4<String, Integer, Integer, Double> fetchIndexesAccess = jdbcQueriesExecutor.fetchIndexesAccess();
        DbInfoCache.indexesAccessList.add(fetchIndexesAccess);

        Tuple2<Integer, Integer> fetchCommitedAndRolledBackTransactions = jdbcQueriesExecutor.fetchCommitedAndRolledBackTransactions();
        DbInfoCache.commitedAndRolledBackTransactionsList.add(fetchCommitedAndRolledBackTransactions);

        Tuple4<String, Double, Integer, Integer> fetchIOTiming = jdbcQueriesExecutor.fetchIOTiming();
        DbInfoCache.IOTimingList.add(fetchIOTiming);

        logger.warn(String.join("\n", fetchLongQueries.toString(), fetchDataBaseSize.toString(), fetchIndexesAccess.toString(), fetchCommitedAndRolledBackTransactions.toString(), fetchIOTiming.toString()));
    }
}
