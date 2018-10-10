package all;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class Misc {

    public static void mail(JavaMailSender sender,String email,String msg){
        if(sender == null){
            System.out.println("Yes");
        }
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(msg);
            helper.setSubject("Mail From Mailing API"); //later extend the feature by adding bank details i.e something like "Mail from abc bank"
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }
}
