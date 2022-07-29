package App.Rest.ExceptionHandler;


public class CustomRestException extends Exception{

    private int number;
    public int getNumber(){return number;}

    public CustomRestException(int num, String message){

        super(message);
        number=num;
    }
}