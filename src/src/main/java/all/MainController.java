package all;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
public class MainController {

    @Autowired
    EmiRepository emiRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Emi> getAllEmis() {
        return emiRepository.findAll();
    }

    @PostMapping(path="/add-emi") //WITH POST VARIABLES TOTAL,PERIOD AND EMAIL
    public ResponseEntity<String> addEmi(@RequestBody Emi emi){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session currentSession = sessionFactory.getCurrentSession();

        Transaction transaction = currentSession.beginTransaction();
        org.hibernate.query.NativeQuery template =null;

        if(emi.getTotal()!=0 && emi.getPeriod()!=0 &&emi.getEmail()!=null) {

            emi.setEmi(emi.getTotal()/emi.getPeriod());
            Date currentDate = new Date();
            emi.setStartDate(currentDate);

            int startMonth = currentDate.getMonth();
            int startDay = currentDate.getDay();
            int startYear = currentDate.getYear();

            int endMonth = currentDate.getMonth() + emi.getPeriod();
            int endYear = currentDate.getYear();
            int endDay = currentDate.getDay();

            if(endMonth > 12){
                endMonth = endMonth - 12;
                endYear++;
            }

            Date endDate = new Date(endYear,endMonth,endDay);

            emi.setEndDate(endDate);
            emi.setRemainder(emi.getTotal());
            emi.setIsPaid(false);

            emiRepository.saveAndFlush(emi);

            String encodedString = "";
            for(int i=1;i<emi.getPeriod();i++){
                encodedString = Misc.encode(""+emi.getId(),Types.EMI_REMINDER,i);

                Date eventEndDate = new Date();
                eventEndDate.setMonth(++startMonth);
                eventEndDate.setDate(startDay-2);
                eventEndDate.setYear(startMonth>12 ? startYear++ : startYear);

                String formattedDate = "'"+"2018"+"-"+eventEndDate.getMonth()+"-"+eventEndDate.getDay()+" 00:00:00.000000'";
                String query = "CREATE EVENT emi_events ON SCHEDULE AT "+formattedDate+" DO INSERT INTO notifications VALUES('"+"Time to pay Installment Number : "+i+"',0,"+emi.getEmail()+");";
                System.out.println(formattedDate);
                template = currentSession.createSQLQuery(query);

                template.executeUpdate();
            }
            encodedString = Misc.encode(""+emi.getId(),Types.TIME_UP,0);
            String formattedDate = "'"+"2018"+"-"+endDate.getMonth()+"-"+endDate.getDay()+" 00:00:00.000000'";
            String query = "CREATE EVENT time_up_events ON SCHEDULE AT "+formattedDate+" DO INSERT INTO notifications VALUES('"+"Time Up Bruv"+"',0,"+emi.getEmail()+");";
            template = currentSession.createNativeQuery(query);
            template.executeUpdate();

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(202).build();  //INVALID REQUEST BODY
    }
}