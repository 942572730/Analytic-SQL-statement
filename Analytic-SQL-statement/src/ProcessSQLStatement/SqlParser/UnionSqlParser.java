package ProcessSQLStatement.SqlParser;

import ProcessSQLStatement.SQLObject;
import ProcessSQLStatement.SingleSqlParserFactory;
import ProcessSQLStatement.SqlParser.SqlParserAbstract.BaseSingleSqlParser;
import ProcessSQLStatement.SqlSegment.SegmentRegExps;
import ProcessSQLStatement.SqlSegment.SqlSegment;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CH on 2017/1/19.
 */
public class UnionSqlParser extends BaseSingleSqlParser {
    public UnionSqlParser(String originalSql, String processedSql){
        super(originalSql, processedSql);
    }
    @Override
    protected void initializeSegments() {
        for(String regExp : SegmentRegExps.unionRegExps){//UpdateSqlParser的正则表达式初始化
            segments.add(new SqlSegment(regExp));
        }
    }

    @Override
    public void Deal(String start, String body, String end) {
        if(body != null && body.equals("union all")){
            this.sqlObjectObj.setTypeName("union all");
            ArrayList<SQLObject> next = new ArrayList<>();
            next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql.split("union all")[0]+";").getSQLObj());
            next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql.split("union all")[1]).getSQLObj());
            this.sqlObjectObj.setNext(next);
        }else if(body != null && body.equals("union")){
            this.sqlObjectObj.setTypeName("union");
            ArrayList<SQLObject> next = new ArrayList<>();
            next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql.split("union")[0]+";").getSQLObj());
            next.add(SingleSqlParserFactory.generateParser(originalSql,processedSql.split("union")[1]).getSQLObj());
            this.sqlObjectObj.setNext(next);
        }
    }
}
