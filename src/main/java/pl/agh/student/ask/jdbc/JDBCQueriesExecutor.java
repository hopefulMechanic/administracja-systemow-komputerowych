package pl.agh.student.ask.jdbc;

import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class JDBCQueriesExecutor {

    private static final Logger logger = LoggerFactory.getLogger(JDBCQueriesExecutor.class);

    @Autowired
    private ConfigHolder config;

    public Tuple2<String, Time> fetchLongQueries() {
        Tuple2<String, Time> data = null;
        try {
            Connection c = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select state_change - query_start as \"runtime\", query from pg_stat_activity where state_change - query_start < '1 seconds'::interval order by runtime desc;");
            while (rs.next()) {
                Time runTime = rs.getTime("runtime");
                String query = rs.getString("query");
                data = new Tuple2<>(query, runTime);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }

    public String fetchDataBaseSize() {
        String data = "";
        try {
            Connection c = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select pg_size_pretty(pg_database_size(datname)) from pg_database  where datname = 'northwind';");
            while (rs.next()) {
                data = rs.getString("pg_size_pretty");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }

    public Tuple4<String, Integer, Integer, Double> fetchIndexesAccess() {
        Tuple4<String, Integer, Integer, Double> data = null;
        try {
            Connection c = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select relname,seq_tup_read,idx_tup_fetch,cast(idx_tup_fetch as numeric) / (idx_tup_fetch + seq_tup_read) as idx_tup_pct from pg_stat_user_tables where (idx_tup_fetch + seq_tup_read)>0 order by idx_tup_pct;");
            while (rs.next()) {
                String relname = rs.getString("relname");
                Integer seq_tup_read = rs.getInt("seq_tup_read");
                Integer idx_tup_fetch = rs.getInt("idx_tup_fetch");
                Double idx_tup_pct = rs.getDouble("idx_tup_pct");
                data = new Tuple4<>(relname, seq_tup_read, idx_tup_fetch, idx_tup_pct);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }

    public Tuple2<Integer, Integer> fetchCommitedAndRolledBackTransactions() {
        Tuple2<Integer, Integer> data = null;
        try {
            Connection c = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select xact_commit as commited, xact_rollback rollbacked from pg_stat_database where datname = 'northwind';");
            while (rs.next()) {
                Integer commited = rs.getInt("commited");
                Integer rollbacked = rs.getInt("rollbacked");
                data = new Tuple2<>(commited, rollbacked);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }


    public Tuple4<String, Double, Integer, Integer> fetchIOTiming() {
        Tuple4<String, Double, Integer, Integer> data = null;
        try {
            Connection c = DriverManager.getConnection(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select relname,cast(heap_blks_hit as numeric) / (heap_blks_hit + heap_blks_read)*100 as hit_pct, heap_blks_hit,heap_blks_read from pg_statio_user_tables where (heap_blks_hit + heap_blks_read)>0 order by hit_pct;");
            while (rs.next()) {
                String relname = rs.getString("relname");
                Double hit_pct = rs.getDouble("hit_pct");
                Integer heap_blks_hit = rs.getInt("heap_blks_hit");
                Integer heap_blks_read = rs.getInt("heap_blks_read");
                data = new Tuple4<>(relname, hit_pct, heap_blks_hit, heap_blks_read);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }
}
