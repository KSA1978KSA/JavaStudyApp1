package App.Rest.Service;

import App.JPA.Entities.Author;
import App.JPA.Persistence.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AuthorService implements IAuthorServiceCRUD {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void create(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Iterable<Author> readAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author read(int id) {
        return null;
    }

    @Override
    public boolean update(Author Author, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
