package pl.agh.student.ask.cache;

import io.vavr.Tuple2;
import io.vavr.Tuple4;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

public class DbInfoCache {

    public static final List<Tuple2<String, Time>> longQueriesList = new LinkedList<>();
    public static final String longQueriesListLabelTitle = "long queries";
    public static final io.vavr.collection.List<String> longQueriesListLabelTableHead = io.vavr.collection.List.of("query","time");

    public static final List<String> dataBaseSizeList = new LinkedList<>();
    public static final String dataBaseSizeListLabelTitle = "DataBase size";

    public static final List<Tuple4<String, Integer, Integer, Double>> indexesAccessList = new LinkedList<>();
    public static final String indexesAccessListLabelTitle = "indexes access";
    public static final io.vavr.collection.List<String> indexesAccessListLabelTableHead = io.vavr.collection.List.of("relname","seq_tup_read","idx_tup_fetch","idx_tup_pct");

    public static final List<Tuple2<Integer, Integer>> commitedAndRolledBackTransactionsList = new LinkedList<>();
    public static final String commitedAndRolledBackTransactionsListLabelTitle = "commited and rolled back transactions";
    public static final io.vavr.collection.List<String> commitedAndRolledBackTransactionsListLabelTableHead = io.vavr.collection.List.of("commited","trollbacked");

    public static final List<Tuple4<String, Double, Integer, Integer>> IOTimingList = new LinkedList<>();
    public static final String IOTimingListLabelTitle = "IO timing";
    public static final io.vavr.collection.List<String> IOTimingListLabelTableHead = io.vavr.collection.List.of("relname","hit_pct","heap_blks_hit","heap_blks_read");

}
