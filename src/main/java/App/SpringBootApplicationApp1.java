package App;

import App.JPA.Business.AuthorsManager;
import App.JPA.Entities.Author;
import App.JPA.Entities.Book;
import App.JPA.Persistence.Repositories.AuthorRepository;
import App.JPA.Persistence.Repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;



@SpringBootApplication
public class SpringBootApplicationApp1 {

	public static void main(String[] args) throws ClassNotFoundException {

		ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplicationApp1.class, args);




		//--- выводим все зарегистрированные бины
		for (int n=0;n<=context.getBeanDefinitionCount()-1;n++) {
			//System.out.println( "!!!:" + context.getBeanDefinitionNames()[n]);
		}


		AuthorsManager authorsManager = context.getBean("authorsManager", AuthorsManager.class);

		Stream<Author> authorStream = authorsManager.getAllAuthors();
		//System.out.println( "authorStream.count():" + authorStream.count());

		authorStream.forEach(Element->System.out.println(Element.getName()));
		//String[] stringArray = authorStream.toArray(size -> new String[size]);
		//Arrays.stream(stringArray).forEach(System.out::println);

		/*
		AuthorRepository authorRepository = context.getBean("authorRepository", AuthorRepository.class);
		Author author1 = new Author("Бунин");
		Author author2 = new Author("исаев");
		authorRepository.save(author1);

		BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);
		List<Author> authors = Arrays.asList(author1, author2);
		Book book1 = new Book(1100000, "Фантастика 2", authors);
		bookRepository.save(book1);
		 */

		/*
		Iterable<Author> authors = authorRepository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Author author : authors) {
			System.out.println(author);
		}
		System.out.println();
		*/


	}



}
