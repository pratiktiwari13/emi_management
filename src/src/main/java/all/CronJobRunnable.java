package all;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class CronJobRunnable implements Runnable {

    @Autowired
    NotificationsRepository notificationsRepository;

    @Override
    public void run() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.beginTransaction();
        System.out.print("Entering");

        while(true){

            List<Object[]> list = currentSession.createNativeQuery("Select id,msg,email from notifications where is_processed = 1").list();


            if(list!=null) {
                for (Object[] listItem:list) {
                    currentSession.createNativeQuery("Update notifications set is_processed = 1 where id = "+listItem[0]).executeUpdate();
                    String email = (String)listItem[2];
                    String msg = (String)listItem[1];
                    Misc.mail(email,msg);
                }
            }

        }
    }
}