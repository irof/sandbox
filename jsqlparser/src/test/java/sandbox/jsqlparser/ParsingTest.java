package sandbox.jsqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParsingTest {

    @ParameterizedTest
    @MethodSource
    void select(String sql, List<String> tableNames) throws Exception {
        try (InputStream inputStream = this.getClass().getResourceAsStream(sql)) {
            Statement statement = CCJSqlParserUtil.parse(inputStream);

            Select select = Select.class.cast(statement);

            List<String> tableList = new TablesNamesFinder().getTableList(select);
            assertThat(tableList)
                    .hasSameElementsAs(tableNames);
        }
    }

    static Stream<Arguments> select() {
        return Stream.of(
                Arguments.of("/select.sql", Arrays.asList("hoge")),
                Arguments.of("/select-join.sql", Arrays.asList("hoge", "fuga")),
                Arguments.of("/select-subquery.sql", Arrays.asList("hoge", "fuga")),
                Arguments.of("/select-japanese.sql", Arrays.asList("とあるテーブル"))
        );
    }

    @Test
    void postgres_sequence() throws Exception {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/select-postgres-sequence.sql")) {
            Statement statement = CCJSqlParserUtil.parse(inputStream);

            Select select = Select.class.cast(statement);

            class SequenceFinder extends SelectVisitorAdapter {
                String sequenceExpression;

                @Override
                public void visit(PlainSelect plainSelect) {
                    List<SelectItem> selectItems = plainSelect.getSelectItems();
                    SelectItem selectItem = selectItems.get(0);

                    SelectItemVisitor expressionVisitor = new ExpressionVisitorAdapter() {
                        @Override
                        public void visit(Function function) {
                            sequenceExpression = function.toString();
                            super.visit(function);
                        }
                    };
                    selectItem.accept(expressionVisitor);
                }
            }

            SequenceFinder sequenceFinder = new SequenceFinder();
            select.getSelectBody().accept(sequenceFinder);

            assertThat(sequenceFinder.sequenceExpression)
                    .isEqualTo("nextval('hoge_seq')");
        }
    }

    @ParameterizedTest
    @MethodSource
    void insert(String sql, String insertTableName, List<String> allTableNames) throws Exception {
        try (InputStream inputStream = this.getClass().getResourceAsStream(sql)) {
            Statement statement = CCJSqlParserUtil.parse(inputStream);

            Insert insert = Insert.class.cast(statement);

            assertThat(insert.getTable().getName()).isEqualTo(insertTableName);

            List<String> tableList = new TablesNamesFinder().getTableList(insert);
            assertThat(tableList)
                    .hasSameElementsAs(allTableNames);
        }
    }

    static Stream<Arguments> insert() {
        return Stream.of(
                Arguments.of("/insert.sql", "hoge", Arrays.asList("hoge")),
                Arguments.of("/insert-select.sql", "hoge", Arrays.asList("hoge", "fuga"))
        );
    }

    @Test
    void SQLではないものを読み込むと例外になる() throws Exception {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/invalid.sql")) {

            assertThatThrownBy(() -> CCJSqlParserUtil.parse(inputStream))
                    .isInstanceOf(JSQLParserException.class);
        }
    }
}
