package App.JPA.Persistence.Repositories;

import App.JPA.Entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
