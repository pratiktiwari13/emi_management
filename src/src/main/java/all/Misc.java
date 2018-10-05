package all;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class Misc {

    public static String encode(String id,String type,int installment){
        if(type.equals(Types.EMI_REMINDER)){
            getReminderRepresentation(id,installment).toString();
        }
        else if(type.equals(Types.TIME_UP)){
            return getTimeUpRepresentation(id).toString();
        }
        return null;
    }

    private static EmiReminderEventTemp getReminderRepresentation(String id, int installment){
        return new EmiReminderEventTemp(id,installment);
    }

    private static TimeUpEventTemp getTimeUpRepresentation(String id){
        return new TimeUpEventTemp(id);
    }

    private static String findType(String encodedMsg){
        if(encodedMsg.contains(Types.EMI_REMINDER)){
            return Types.EMI_REMINDER;
        }
        else if(encodedMsg.contains(Types.TIME_UP)){
            return Types.TIME_UP;
        }
        return null;
    }

    public static GenericEventMeta decode(String encodedString){

        if(findType(encodedString).equals(Types.EMI_REMINDER)){

            EmiReminderEventTemp obj = new EmiReminderEventTemp();
            String[] data = encodedString.split(",");
            String[] subData;
            subData = data[0].split(":");
            obj.setUserId(subData[1]);

            subData = data[1].split(":");
            obj.setInstallment(Integer.parseInt(subData[1]));

            return obj;
        }
        else if(findType(encodedString).equals(Types.TIME_UP)){
            TimeUpEventTemp obj = new TimeUpEventTemp();
            String[] data = encodedString.split(",");
            String[] subData;
            subData = data[1].split(":");
            obj.setUserId(subData[1]);

            return obj;
        }
        return null;
    }
    public static void mail(String email,JavaMailSender sender){
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText("Emi Due");
            helper.setSubject("Mail From Mailing API"); //later extend the feature by adding bank details i.e something like "Mail from abc bank"
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }
}
