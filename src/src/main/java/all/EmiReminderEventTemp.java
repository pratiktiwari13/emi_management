package all;

public class EmiReminderEventTemp extends GenericEventMeta{
    private int installment;
    private String userId;


    public EmiReminderEventTemp(){}

    public EmiReminderEventTemp(String userId, int installment){
        this.userId = userId;
        this.installment = installment;
        setEventType(Types.EMI_REMINDER);
    }

    public void setInstallment(int installment){
        this.installment = installment;
    }

    public int getInstallment(){
        return installment;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    @Override
    public String toString(){
        return String.format(Types.EMI_REMINDER,userId,installment);
    }
}
