package co.edu.uniandes.csw.bookstore.api;

import co.edu.uniandes.csw.bookstore.entities.ViajeroEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import java.util.List;

public interface IBookLogic {

    public List<BookEntity> getBooks();

    public BookEntity getBook(Long id) throws BusinessLogicException;

    public BookEntity createBook(BookEntity entity) throws BusinessLogicException;

    public BookEntity updateBook(BookEntity entity) throws BusinessLogicException;

    public void deleteBook(Long id);

    public List<ViajeroEntity> getAuthors(Long bookId);

    public ViajeroEntity getAuthor(Long bookId, Long authorId);

    public ViajeroEntity addAuthor(Long authorId, Long bookId) throws BusinessLogicException;

    public void removeAuthor(Long authorId, Long bookId);

    public List<ViajeroEntity> replaceAuthors(List<ViajeroEntity> authors, Long bookId) throws BusinessLogicException;

}
