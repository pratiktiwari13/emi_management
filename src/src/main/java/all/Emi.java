package all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Emi {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer emi;
    private Integer period;
    private Integer total;
    private Date startDate;
    private Date endDate;
    private Integer remainder;
    private Boolean isPaid;
    private String email;

    public void setEmail(String email){ this.email = email;}
    public String getEmail(){return email;}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getEmi(){
        return emi;
    }
    public void setEmi(Integer emi){
        this.emi = emi;
    }
    public Integer getPeriod(){
        return period;
    }
    public void setPeriod(Integer period){
        this.period = period;
    }
    public Integer getTotal(){
        return total;
    }
    public void setTotal(Integer total){
        this.total = total;
    }
    public Date getStartDate(){
        return startDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public Integer getRemainder(){
        return remainder;
    }
    public void setRemainder(Integer remainder){
        this.remainder = remainder;
    }
    public Boolean getIsPaid(){
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid){
        this.isPaid = isPaid;
    }
}
