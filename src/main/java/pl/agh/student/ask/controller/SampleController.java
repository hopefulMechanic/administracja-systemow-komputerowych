package pl.agh.student.ask.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.student.ask.cache.DbInfoCache;

@RestController
public class SampleController {

    @RequestMapping("/")
    String home() {
        String data = String.join("\n", DbInfoCache.longQueriesListLabel, DbInfoCache.longQueriesList.get(0).toString(),
                DbInfoCache.dataBaseSizeListLabel, DbInfoCache.dataBaseSizeList.get(0).toString(),
                DbInfoCache.indexesAccessListLabel, DbInfoCache.indexesAccessList.get(0).toString(),
                DbInfoCache.commitedAndRolledBackTransactionsListLabel, DbInfoCache.commitedAndRolledBackTransactionsList.get(0).toString(),
                DbInfoCache.IOTimingListLabel, String.join("\n", DbInfoCache.IOTimingList.get(0).toString()));
        return data;
    }
}
