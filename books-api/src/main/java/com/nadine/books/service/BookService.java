package com.nadine.books.service;

import java.util.List;

import com.nadine.books.dto.BookDTO;
import com.nadine.books.entities.Book;
import com.nadine.books.entities.Genre;

public interface BookService {
    BookDTO saveBook(BookDTO b);
    BookDTO updateBook(BookDTO b);
    void deleteBook(Book b);
    void deleteBookById(Long id);
    BookDTO getBook(Long id);
    List<BookDTO> getAllBooks();
    List<Book> findByNomBook(String nom);
    List<Book> findByNomBookContains(String nom);
    List<Book> findByNomPrix(String nom, Double prix);
    List<Book> findByGenre(Genre genre);
    List<Book> findByGenreIdGenre(Long id);
    List<Book> findByOrderByNomBookAsc();
    List<Book> trierBooksNomsPrix();
    BookDTO convertEntityToDto(Book book);
    Book convertDtoToEntity(BookDTO bookDto);
}
