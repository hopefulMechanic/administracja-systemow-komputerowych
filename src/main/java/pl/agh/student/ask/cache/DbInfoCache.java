package pl.agh.student.ask.cache;

import io.vavr.Tuple2;
import io.vavr.Tuple4;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class DbInfoCache {

    public static final List<List<Tuple2<String, Timestamp>>> longQueriesList = new LinkedList<>();
    public static final String longQueriesListLabel = "\n\tlong queries:\n  time:\t\tquery:";

    public static final List<String> dataBaseSizeList = new LinkedList<>();
    public static final String dataBaseSizeListLabel = "\n DataBase size:";

    public static final List<List<Tuple4<String, Integer, Integer, Double>>> indexesAccessList = new LinkedList<>();
    public static final String indexesAccessListLabel = "\n\tindexes access:\n  relname:\t\tseq_tup_read:\t\tidx_tup_fetch:\t\tidx_tup_pct:";

    public static final List<List<Tuple2<Integer, Integer>>> commitedAndRolledBackTransactionsList = new LinkedList<>();
    public static final String commitedAndRolledBackTransactionsListLabel = "\n\tcommited and rolled back transactions\n  commited:\t\trollbacked:";

    public static final List<List<Tuple4<String, Double, Integer, Integer>>> IOTimingList = new LinkedList<>();
    public static final String IOTimingListLabel = "\n\tIO timing:\n\trelname:\t\thit_pct:\t\theap_blks_hit:\t\theap_blks_read:";

}
