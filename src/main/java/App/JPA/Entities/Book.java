package App.JPA.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, unique = true)
  private String name;

  /*
  @ManyToOne
  @JoinColumn(name = "author")
  private Author author;
   */

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          })
  @JoinTable(name = "books_authors_intersect",
          joinColumns = { @JoinColumn(name = "book_id") },
          inverseJoinColumns = { @JoinColumn(name = "author_id") })
  private List<Author> authors;

  protected Book() {
    // for JPA
  }

  public Book(int id, String name, List<Author> authors) {
    this.id = id;
    this.name = name;
    this.authors = authors;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Author> getAuthor() {
    return authors;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", authors=" + authors.toString() +
        '}';
  }
}
