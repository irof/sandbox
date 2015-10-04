package jdbc;

import org.hsqldb.Server;

/**
 * @author irof
 */
public class HSQLDBServer {
    public static void main(String[] args) {
        Server.main(new String[]{"-database", ".hsqldb/jdbcjobstore"});
    }
}
