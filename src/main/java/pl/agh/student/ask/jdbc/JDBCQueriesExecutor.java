package pl.agh.student.ask.jdbc;

import ch.qos.logback.classic.spi.EventArgUtil;
import io.vavr.Tuple2;
import io.vavr.Tuple4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JDBCQueriesExecutor {

    private static final Logger logger = LoggerFactory.getLogger(JDBCQueriesExecutor.class);

//    public static void main(String[] args) {
//        List<Tuple2<String, Timestamp>> tuple2s1 = fetchLongQueries();
//        String s = fetchDataBaseSize();
//        List<Tuple4<String, Integer, Integer, Double>> tuple4s = fetchIndexesAccess();
//        List<Tuple2<Integer, Integer>> tuple2s = fetchCommitedAndRolledBackTransactions();
//        List<Tuple4<String, Double, Integer, Integer>> tuple4s1 = fetchIOTiming();
//        logger.error(tuple2s1.toString() + s.toString() + tuple4s.toString() + tuple2s.toString() + tuple4s1.toString());
//    }

    public static List<Tuple2<String, Timestamp>> fetchLongQueries() {
        LinkedList<Tuple2<String, Timestamp>> data = new LinkedList<>();
        try {
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
                            "northwind", "northwind");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select state_change - query_start as \"runtime\", query from pg_stat_activity where state_change - query_start > '1 seconds'::interval order by runtime desc;");
            while ( rs.next() ) {
                Timestamp runTime = rs.getTimestamp("runtime");
                String query = rs.getString("query");
                Tuple2<String, Timestamp> tuple2 = new Tuple2<>(query, runTime);
                data.add(tuple2);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return data;
    }

    public static String fetchDataBaseSize() {
        String data = "";
        try {
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
                            "northwind", "northwind");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select pg_size_pretty(pg_database_size(datname)) from pg_database  where datname = 'northwind';");
            while ( rs.next() ) {
                data = rs.getString("pg_size_pretty");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return data;
    }

    public static List<Tuple4<String, Integer, Integer, Double>> fetchIndexesAccess() {
        LinkedList<Tuple4<String, Integer, Integer, Double>> data = new LinkedList<>();
        try {
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
                            "northwind", "northwind");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select relname,seq_tup_read,idx_tup_fetch,cast(idx_tup_fetch as numeric) / (idx_tup_fetch + seq_tup_read) as idx_tup_pct from pg_stat_user_tables where (idx_tup_fetch + seq_tup_read)>0 order by idx_tup_pct;");
            while ( rs.next() ) {
                String relname = rs.getString("relname");
                Integer seq_tup_read = rs.getInt("seq_tup_read");
                Integer idx_tup_fetch = rs.getInt("idx_tup_fetch");
                Double idx_tup_pct = rs.getDouble("idx_tup_pct");
                Tuple4<String, Integer, Integer, Double> tuple4 = new Tuple4<>(relname, seq_tup_read, idx_tup_fetch, idx_tup_pct);
                data.add(tuple4);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return data;
    }

    public static List<Tuple2<Integer, Integer>> fetchCommitedAndRolledBackTransactions() {
        LinkedList<Tuple2<Integer, Integer>> data = new LinkedList<>();
        try {
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
                            "northwind", "northwind");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select xact_commit as commited, xact_rollback rollbacked from pg_stat_database where datname = 'northwind';");
            while ( rs.next() ) {
                Integer commited = rs.getInt("commited");
                Integer rollbacked = rs.getInt("rollbacked");
                Tuple2<Integer, Integer> tuple2 = new Tuple2<>(commited, rollbacked);
                data.add(tuple2);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return data;
    }


    public static List<Tuple4<String, Double, Integer, Integer>> fetchIOTiming() {
        LinkedList<Tuple4<String, Double, Integer, Integer>> data = new LinkedList<>();
        try {
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
                            "northwind", "northwind");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "select relname,cast(heap_blks_hit as numeric) / (heap_blks_hit + heap_blks_read)*100 as hit_pct, heap_blks_hit,heap_blks_read from pg_statio_user_tables where (heap_blks_hit + heap_blks_read)>0 order by hit_pct;");
            while ( rs.next() ) {
                String relname = rs.getString("relname");
                Double hit_pct = rs.getDouble("hit_pct");
                Integer heap_blks_hit = rs.getInt("heap_blks_hit");
                Integer heap_blks_read = rs.getInt("heap_blks_read");
                Tuple4<String, Double, Integer, Integer> tuple4 = new Tuple4<>(relname, hit_pct, heap_blks_hit, heap_blks_read);
                data.add(tuple4);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            logger.error( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return data;
    }





    // komity i rolbaki
//    select xact_commit as commited, xact_rollback rollbacked from pg_stat_database where datname = 'northwind';
    // zapytania trwajace powyzej jednej sekundy
//    SELECT now() - query_start as "runtime", usename, datname, state, query FROM pg_stat_activity WHERE now() - query_start > '1 seconds'::interval ORDER BY runtime DESC;
//    SELECT state_change - query_start as "runtime", usename, datname, state, query FROM pg_stat_activity WHERE state_change - query_start > '1 seconds'::interval ORDER BY runtime DESC;
    // czesto uzywane tabele i dostepnosc ich indeksow
// SELECT relname,seq_tup_read,idx_tup_fetch,cast(idx_tup_fetch AS numeric) / (idx_tup_fetch + seq_tup_read) AS idx_tup_pct FROM pg_stat_user_tables WHERE (idx_tup_fetch + seq_tup_read)>0 ORDER BY idx_tup_pct;
    // I/O timing
// SELECT relname,cast(heap_blks_hit as numeric) / (heap_blks_hit + heap_blks_read) AS hit_pct, heap_blks_hit,heap_blks_read FROM pg_statio_user_tables WHERE (heap_blks_hit + heap_blks_read)>0 ORDER BY hit_pct;
    // zajetosc bazy danych
// select pg_size_pretty(pg_database_size(datname)) from pg_database  where datname = 'northwind';

    //    public static List<String> callDb(List<String> queries) {
//    public static void main( String args[] ) {
//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection c = DriverManager
//                    .getConnection("jdbc:postgresql://localhost:5434/northwind",
//                            "northwind", "northwind");
//            c.setAutoCommit(false);
//            logger.info("Opened database successfully");
//
//            Statement stmt = c.createStatement();
////            ResultSet rs = stmt.executeQuery(queries.get(0));
//            ResultSet rs = stmt.executeQuery( "SELECT relname,seq_tup_read,idx_tup_fetch,cast(idx_tup_fetch AS numeric) / (idx_tup_fetch + seq_tup_read) AS idx_tup_pct FROM pg_stat_user_tables WHERE (idx_tup_fetch + seq_tup_read)>0 ORDER BY idx_tup_pct;" );
//            while ( rs.next() ) {
//                String relName = rs.getString("relName");
//                int seqTupRead  = rs.getInt("seq_tup_read");
//                int idxTupFetch = rs.getInt("idx_tup_fetch");
//                int idxTupPct = rs.getInt("idx_tup_pct");
//                logger.info( "relName:  " + relName);
//                logger.info( "seqTupRead:  " + seqTupRead);
//                logger.info( "idxTupFetch:  " + idxTupFetch);
//                logger.info( "idxTupPct:  " + idxTupPct);
//            }
//            rs.close();
//            stmt.close();
//
//            stmt = c.createStatement();
//            rs = stmt.executeQuery( "select client_addr, usename, datname, clock_timestamp() - xact_start as xact_age, clock_timestamp() - query_start as query_age, query from pg_stat_activity order by xact_start, query_start;" );
//            while ( rs.next() ) {
//                String clientAddr = rs.getString("client_addr");
//                String usename  = rs.getString("usename");
//                String datname = rs.getString("datname");
//                Time xactAge = rs.getTime("xact_age");
//                Time queryAge = rs.getTime("query_age");
//                String query = rs.getString("query");
//                logger.info( "clientAddr:  " + clientAddr);
//                logger.info( "usename:  " + usename);
//                logger.info( "datname:  " + datname);
//                logger.info( "xactAge:  " + xactAge);
//                logger.info( "queryAge:  " + queryAge);
//                logger.info( "query:  " + query);
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            logger.error( e.getClass().getName()+": "+ e.getMessage() );
//            System.exit(0);
//        }
//        logger.warn("Operation done successfully");
////        return Arrays.asList("");
//    }
}
