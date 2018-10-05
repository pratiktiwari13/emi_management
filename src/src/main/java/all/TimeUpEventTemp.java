package all;

public class TimeUpEventTemp extends GenericEventMeta{
    private String userId;

    public TimeUpEventTemp(){}

    public TimeUpEventTemp(String userId){
        this.userId = userId;
        setEventType(Types.TIME_UP);
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString(){
        return String.format(Types.TIME_UP,userId);
    }
}
