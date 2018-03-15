package sandbox.standalone;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import java.util.concurrent.TimeUnit;

public class Server {

    public static void main(String[] args) throws Exception {
        GreenMail greenMail = new GreenMail(
                new ServerSetup[]{
                        new ServerSetup(3025, "0.0.0.0", ServerSetup.PROTOCOL_SMTP),
                        new ServerSetup(3110, "0.0.0.0", ServerSetup.PROTOCOL_POP3)}
        );
        try (AutoCloseable ac = greenMail::stop) {
            greenMail.setUser("xxx@example.com", "xxx", "yyy");
            greenMail.start();

            TimeUnit.SECONDS.sleep(30);
        }
    }
}
