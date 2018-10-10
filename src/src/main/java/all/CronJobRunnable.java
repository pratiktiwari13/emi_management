package all;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class CronJobRunnable implements Runnable,ApplicationListener {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JavaMailSender sender;

    @Override
    public void run() {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session currentSession = sessionFactory.getCurrentSession();
            currentSession.beginTransaction();
            System.out.print("Entering");

            while (true) {

                List<Object[]> list = currentSession.createNativeQuery("Select id,msg,email from notifications where is_processed = 0").list();


                if (list != null) {
                    for (Object[] listItem : list) {
                        currentSession.createNativeQuery("Update notifications set is_processed = 1 where id = " + listItem[0]).executeUpdate();
                        String email = (String) listItem[2];
                        String msg = (String) listItem[1];
                        System.out.println("Email is" + email + ", and Message is " + msg);
                        Misc.mail(sender, email, msg);
                        System.out.println("Sent");
                    }
                }

            }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("Instantiated");
        if (applicationEvent instanceof ContextRefreshedEvent) {
            if(sender==null){
                applicationContext = ((ContextRefreshedEvent) applicationEvent).getApplicationContext();
                sender = applicationContext.getBean(JavaMailSender.class);
                if(sender == null && applicationContext == null){
                    System.out.println("Somethings wrong");
                }
                System.out.println("Sender Instantiated");
            }
        }
    }
}