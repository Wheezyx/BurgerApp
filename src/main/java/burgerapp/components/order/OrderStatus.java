package burgerapp.components.order;

public enum OrderStatus
{
    DELIVERED,
    COMPLETE,
    IN_PROGRESS,
    NEW,
    ;
    
    public static OrderStatus nextStatus(OrderStatus status)
    {
        if(status == NEW)
        {
            return IN_PROGRESS;
        }
        else if(status == IN_PROGRESS)
        {
            return COMPLETE;
        }
        else if(status == COMPLETE)
        {
            return DELIVERED;
        }
        else
        {
            throw new IllegalArgumentException("No status");
        }
    }
}
