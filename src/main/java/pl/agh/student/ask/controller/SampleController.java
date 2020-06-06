package pl.agh.student.ask.controller;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.student.ask.cache.DbInfoCache;

@RestController
public class SampleController {

    @Value("${spring.force.refresh}")
    private String refresh;

    @RequestMapping("/")
    String home() {
        String data = "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" +
                createLinks() +
                "<div style=\"margin-top: 50px\">" + String.join(" </div><div style=\"margin-top: 50px\">",
                getLongQueries(),
                getDatabaseSize(),
                getIndexAccess(),
                getcommitedAndRolledBackTransactions(),
                getIOTiming())
                + "</div>";
        return data;
    }

    @RequestMapping("/longQueries")
    String longQueries() {
        return "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" + createLinks() + getLongQueries();
    }

    @RequestMapping("/databaseSize")
    String databaseSize() {
        return "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" + createLinks() + getDatabaseSize();
    }

    @RequestMapping("/indexAccess")
    String indexAccess() {
        return "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" + createLinks() + getIndexAccess();
    }

    @RequestMapping("/commitedAndRolledBackTransactions")
    String commitedAndRolledBackTransactions() {
        return "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" + createLinks() + getcommitedAndRolledBackTransactions();
    }

    @RequestMapping("/iOTiming")
    String iOTiming() {
        return "<meta http-equiv=\"refresh\" content=\"" + refresh + "\" >" + createLinks() + getIOTiming();
    }

    private String createLinks() {
        return "<a style=\"text-decoration:none; color:red\" href=\"http://localhost:8080/\"> all </a>" +
                "<a style=\"text-decoration:none; color:blue\" href=\"http://localhost:8080/longQueries\"> longQueries </a>" +
                "<a style=\"text-decoration:none; color:green\" href=\"http://localhost:8080/databaseSize\"> databaseSize </a>" +
                "<a style=\"text-decoration:none; color:orange\" href=\"http://localhost:8080/indexAccess\"> index access </a>" +
                "<a style=\"text-decoration:none; color:gray\" href=\"http://localhost:8080/commitedAndRolledBackTransactions\"> commitedAndRolledBackTransactions </a>" +
                "<a style=\"text-decoration:none; color:black\" href=\"http://localhost:8080/iOTiming\"> iOTiming </a>";
    }

    private String getLongQueries() {
        List<Tuple2<Utils.TableRow, String>> headRow = List.fill(DbInfoCache.longQueriesListLabelTableHead.size(), Utils.TableRow.HEAD).zip(DbInfoCache.longQueriesListLabelTableHead);
        String tableHead = Utils.createTableRow(headRow);
        List<Tuple2<Utils.TableRow, String>> dataRow = List.fill(DbInfoCache.longQueriesListLabelTableHead.size(), Utils.TableRow.DATA).zip(List.of(DbInfoCache.longQueriesList.get(0)._1(), DbInfoCache.longQueriesList.get(0)._2().toString()));
        String tableData = Utils.createTableRow(dataRow);
        return createMetricTitle(DbInfoCache.longQueriesListLabelTitle)
                + Utils.createTable(Utils.createTablePart(Utils.TablePart.HEAD, tableHead) + Utils.createTablePart(Utils.TablePart.BODY, tableData));
    }

    private String getDatabaseSize() {
        List<Tuple2<Utils.TableRow, String>> dataRow = List.of(Utils.TableRow.DATA).zip(List.of(DbInfoCache.dataBaseSizeList.get(0)));
        String tableData = Utils.createTableRow(dataRow);
        return createMetricTitle(DbInfoCache.dataBaseSizeListLabelTitle)
                + Utils.createTable(Utils.createTablePart(Utils.TablePart.BODY, tableData));
    }

    private String getIndexAccess() {
        List<Tuple2<Utils.TableRow, String>> headRow = List.fill(DbInfoCache.indexesAccessListLabelTableHead.size(), Utils.TableRow.HEAD).zip(DbInfoCache.indexesAccessListLabelTableHead);
        String tableHead = Utils.createTableRow(headRow);
        List<Tuple2<Utils.TableRow, String>> dataRow = List.fill(DbInfoCache.indexesAccessListLabelTableHead.size(), Utils.TableRow.DATA)
                .zip(List.of(DbInfoCache.indexesAccessList.get(0)._1().toString(),
                        DbInfoCache.indexesAccessList.get(0)._2().toString(),
                        DbInfoCache.indexesAccessList.get(0)._3().toString(),
                        DbInfoCache.indexesAccessList.get(0)._4().toString()));
        String tableData = Utils.createTableRow(dataRow);
        return createMetricTitle(DbInfoCache.indexesAccessListLabelTitle)
                + Utils.createTable(Utils.createTablePart(Utils.TablePart.HEAD, tableHead) + Utils.createTablePart(Utils.TablePart.BODY, tableData));
    }

    private String getcommitedAndRolledBackTransactions() {
        List<Tuple2<Utils.TableRow, String>> headRow = List.fill(DbInfoCache.commitedAndRolledBackTransactionsListLabelTableHead.size(), Utils.TableRow.HEAD).zip(DbInfoCache.commitedAndRolledBackTransactionsListLabelTableHead);
        String tableHead = Utils.createTableRow(headRow);
        List<Tuple2<Utils.TableRow, String>> dataRow = List.fill(DbInfoCache.commitedAndRolledBackTransactionsListLabelTableHead.size(), Utils.TableRow.DATA).zip(List.of(DbInfoCache.commitedAndRolledBackTransactionsList.get(0)._1().toString(), DbInfoCache.commitedAndRolledBackTransactionsList.get(0)._2().toString()));
        String tableData = Utils.createTableRow(dataRow);
        return createMetricTitle(DbInfoCache.commitedAndRolledBackTransactionsListLabelTitle)
                + Utils.createTable(Utils.createTablePart(Utils.TablePart.HEAD, tableHead) + Utils.createTablePart(Utils.TablePart.BODY, tableData));
    }

    private String getIOTiming() {
        List<Tuple2<Utils.TableRow, String>> headRow = List.fill(DbInfoCache.IOTimingListLabelTableHead.size(), Utils.TableRow.HEAD).zip(DbInfoCache.IOTimingListLabelTableHead);
        String tableHead = Utils.createTableRow(headRow);
        List<Tuple2<Utils.TableRow, String>> dataRow = List.fill(DbInfoCache.IOTimingListLabelTableHead.size(), Utils.TableRow.DATA)
                .zip(List.of(DbInfoCache.IOTimingList.get(0)._1().toString(),
                        DbInfoCache.IOTimingList.get(0)._2().toString(),
                        DbInfoCache.IOTimingList.get(0)._3().toString(),
                        DbInfoCache.IOTimingList.get(0)._4().toString()));
        String tableData = Utils.createTableRow(dataRow);
        return createMetricTitle(DbInfoCache.IOTimingListLabelTitle)
                + Utils.createTable(Utils.createTablePart(Utils.TablePart.HEAD, tableHead) + Utils.createTablePart(Utils.TablePart.BODY, tableData));
    }

    private String createMetricTitle(String metricName) {
        return Utils.openTag("div") + metricName + Utils.closeTag("div");
    }
}
