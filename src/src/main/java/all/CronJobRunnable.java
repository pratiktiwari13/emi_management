package all;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class CronJobRunnable implements Runnable {

    @Autowired
    NotificationsRepository notificationsRepository;

    @Autowired
    private JavaMailSender sender;

    @Override
    public void run() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        System.out.print("Entering");

        while(true){

            List<Object[]> list = currentSession.createNativeQuery("Select id,msg from notifications where is_processed = 0").list();


            if(list!=null) {
                for (Object[] listItem:list) {
                    System.out.println("Msg = "+listItem[1]);
                    currentSession.createNativeQuery("Update notifications set is_processed = 1 where id = "+listItem[0]).executeUpdate();
                    String email = (String)currentSession.createNativeQuery("Select email from emi where id="+listItem[0]).list().get(0);
                    Misc.mail(email,sender);
                }
            }

        }
    }
}