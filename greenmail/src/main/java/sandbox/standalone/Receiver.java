package sandbox.standalone;

import javax.mail.*;
import java.util.Properties;

public class Receiver {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.pop3.host", "127.0.0.1");
        props.setProperty("mail.pop3.port", Integer.toString(3110));
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xxx", "yyy");
            }
        });

        session.setDebug(true);

        try (Store store = session.getStore("pop3")) {
            store.connect();
            try (Folder folder = store.getFolder("INBOX")) {
                folder.open(Folder.READ_ONLY);

                for (Message message : folder.getMessages()) {
                    System.out.println(message.getSubject());
                }
            }
        }
    }
}
