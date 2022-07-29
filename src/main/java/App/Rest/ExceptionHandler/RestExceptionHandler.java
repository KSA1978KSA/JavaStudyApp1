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
    @ExceptionHandler(value = { CustomRestException.class }) //--- на какой класс завязываем обработчик ошибок
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse badRequest(CustomRestException ex) //--- в качестве входных параметров - кастомый класс с Exception
    {
        return new ErrorResponse(ex.getNumber(), ex.getMessage(), "KSA Test Error");//--- возвращаем экземпляр класса с публичными геттерами, которые превратятся в JSON объект в Response
    }
}