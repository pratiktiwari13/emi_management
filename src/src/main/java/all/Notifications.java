package all;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notifications{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String msg;

    private Boolean isProcessed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg= msg;
    }

    public void setIsProcessed(Boolean isProcessed){
        this.isProcessed = isProcessed;
    }

    public Boolean getIsProcessed() {
        return isProcessed;
    }
}
