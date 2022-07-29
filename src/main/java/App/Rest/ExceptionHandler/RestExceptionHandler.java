package App.Rest.ExceptionHandler;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//--- https://www.javainterviewpoint.com/spring-boot-exception-handling/

@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(value = { CustomRestException.class })
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse badRequest(CustomRestException ex)
    {
        return new ErrorResponse(ex.getNumber(), ex.getMessage());
    }

}