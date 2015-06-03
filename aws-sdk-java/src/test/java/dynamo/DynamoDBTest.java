package dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class DynamoDBTest {

    @Test
    public void listTables() throws Exception {
        AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient();
        ListTablesResult tables = dynamoDB.listTables();
        assertThat(tables.getTableNames(), is(notNullValue()));
    }
}
