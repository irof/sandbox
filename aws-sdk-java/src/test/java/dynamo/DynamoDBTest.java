package dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.Tables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class DynamoDBTest {

    private AmazonDynamoDBClient dynamoDB;

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
    };

    @Before
    public void setup() throws Exception {
        dynamoDB = new AmazonDynamoDBClient();
    }

    @Test
    public void listTables() throws Exception {
        ListTablesResult tables = dynamoDB.listTables();
        assertThat(tables.getTableNames(), is(notNullValue()));
    }

    @Test
    public void tableCreateAndDelete() throws Exception {
        assertThat(Tables.doesTableExist(dynamoDB, "myTable"), is(false));
        System.out.println(stopwatch.runtime(TimeUnit.MILLISECONDS));

        CreateTableRequest tableRequest = new CreateTableRequest()
                .withTableName("myTable")
                .withKeySchema(new KeySchemaElement("myName", KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition("myName", ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        CreateTableResult createResult = dynamoDB.createTable(tableRequest);
        System.out.println(stopwatch.runtime(TimeUnit.MILLISECONDS));

        // 20秒くらいかかる
        Tables.awaitTableToBecomeActive(dynamoDB, "myTable");
        assertThat(Tables.doesTableExist(dynamoDB, "myTable"), is(true));
        System.out.println(stopwatch.runtime(TimeUnit.MILLISECONDS));

        DeleteTableResult deleteResult = dynamoDB.deleteTable("myTable");
        System.out.println(stopwatch.runtime(TimeUnit.MILLISECONDS));

        assertThat(Tables.doesTableExist(dynamoDB, "myTable"), is(false));
        System.out.println(stopwatch.runtime(TimeUnit.MILLISECONDS));
    }
}
