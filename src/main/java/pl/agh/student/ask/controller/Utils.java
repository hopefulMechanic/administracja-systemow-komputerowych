package pl.agh.student.ask.controller;

import io.vavr.Tuple2;
import io.vavr.collection.List;

public class Utils {

    private static final String TABLE = "table";
    private static final String TABLE_ROW = "tr";

    public static String createTable(String content) {
        String myTableTitle = TABLE + " border=\"2\"";
        return openTag(myTableTitle) + content + closeTag(TABLE);
    }

    public static String createTablePart(TablePart tp, String content) {
        return openTag(tp.tag) + content + closeTag(tp.tag);
    }

    public static String createTableRow(List<Tuple2<TableRow, String>> rows) {
        StringBuilder sb = new StringBuilder(openTag(TABLE_ROW));
        rows.forEach(row -> sb.append(openTag(row._1().tag)).append(row._2()).append(closeTag(row._1().tag)));
        sb.append(closeTag(TABLE_ROW));
        return sb.toString();
    }

    public static String openTag(String tag) {
        return "<" + tag + ">";
    }
    public static String closeTag(String tag) {
        return "</" + tag + ">";
    }

    public enum TableRow {
        HEAD("th bgcolor=\"#efefef\""),
        DATA("td");

        String tag;

        private TableRow(String tag){
            this.tag = tag;
        }
    }

    public enum TablePart {
        HEAD("thead"),
        BODY("tbody"),
        FOOT("tfoot");

        String tag;

        private TablePart(String tag) {
            this.tag = tag;
        }
    }
}
