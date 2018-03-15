package sandbox.standalone;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.util.Properties;

public class Sender {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", InetAddress.getLocalHost().getHostAddress());
        props.setProperty("mail.smtp.port", Integer.toString(3025));
        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("foo@example.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("xxx@example.com"));
        message.setSubject("ほげ");
        message.setText("ふが");
        Transport.send(message);
    }
}
