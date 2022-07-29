package App.Rest.Controler;



import App.JPA.Entities.Author;
import App.Rest.Model.AuthorModel;
import App.Rest.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import App.Rest.ExceptionHandler.CustomRestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AuthorControler  {


    private final AuthorService authorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorControler.class);

    @Autowired
    public AuthorControler(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(value = "/Authors/Create")
    public ResponseEntity<?> create(@RequestBody AuthorModel authorModel) throws CustomRestException {

        LOGGER.trace("This is TRACE");
        LOGGER.debug("This is DEBUG");
        LOGGER.info("This is INFO");
        LOGGER.warn("This is WARN");
        LOGGER.error("This is ERROR");

        System.out.println( "Author:" + authorModel.toString());

        authorService.create(new Author(authorModel.getName()));

        if (authorModel.getName().equals("Test Error")) {
            throw new CustomRestException(999, "Test Error End Point /Authors/Create");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/Authors/GetList")
    public ResponseEntity<Iterable<Author>> read() {

        Iterable<Author> authors = authorService.readAll();

        System.out.println( "Get authors");

        return authors != null
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
