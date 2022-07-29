package App.Rest.ExceptionHandler;

public class ErrorResponse
{
    private int status;
    private String message;

    private String customParam;

    /*
    public ErrorResponse()
    {
        super();
    }
     */


    public ErrorResponse(int status, String message, String customParam)
    {
        //super();
        this.status = status;
        this.message = message;
        this.customParam = customParam;
    }

    public String getCustomParam() {
        return customParam;
    }

    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    @Override
    public String toString()
    {
        return "ErrorResponse [status=" + status + ", message=" + message + "]";
    }
}