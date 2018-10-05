package all;

public abstract class GenericEventMeta {
    public String eventType;

    protected void setEventType(String eventType){
        this.eventType = eventType;
    }

    public String getEventType(){
        return eventType;
    }
}
