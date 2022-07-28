package App.Rest.Service;

import App.JPA.Entities.Author;
import java.util.List;

public interface IAuthorServiceCRUD {
    /**
     * Создает нового автора
     * @param author - автор для создания
     */
    void create(Author author);

    /**
     * Возвращает список всех имеющихся авторов
     * @return список авторов
     */
    Iterable<Author> readAll();

    /**
     * Возвращает автора по его ID
     * @param id - ID автора
     * @return - объект автора с заданным ID
     */
    Author read(int id);

    /**
     * Обновляет автора с заданным ID,
     * в соответствии с переданным автором
     * @param author - автор в соответсвии с которым нужно обновить данные
     * @param id - id автора которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Author author, int id);

    /**
     * Удаляет автора с заданным ID
     * @param id - id автора, которого нужно удалить
     * @return - true если автор был удален, иначе false
     */
    boolean delete(int id);
}
