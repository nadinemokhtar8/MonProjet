package com.nadine.books;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nadine.books.entities.Book;
import com.nadine.books.entities.Genre;
import com.nadine.books.repos.BookRepository;

@SpringBootTest
class BooksApiApplicationTests {

    @Autowired
    private BookRepository bookRepository;

   

    @Test
    public void testFindBook() {
        Book b = bookRepository.findById(1L).get();
        System.out.println(b);
    }

    @Test
    public void testUpdateBook() {
        Book b = bookRepository.findById(1L).get();
        b.setPrixBook(1000.0);
        bookRepository.save(b);
    }

    @Test
    public void testDeleteBook() {
        bookRepository.deleteById(1L);
    }

    @Test
    public void testListerTousBooks() {
        List<Book> books = bookRepository.findAll();
        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Test
    public void testFindByNomBook() {
        List<Book> books = bookRepository.findByNomBook("iphone X");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Test
    public void testFindByNomBookContains() {
        List<Book> books = bookRepository.findByNomBookContains("iphone");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Test
    public void testfindByNomPrix() {
        List<Book> books = bookRepository.findByNomPrix("iphone", 1000.0);
        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Test
    public void testfindByGenre() {
        Genre genre = new Genre();
        genre.setIdGenre(1L);
        List<Book> books = bookRepository.findByGenre(genre);
        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Test
    public void findByGenreIdGenre() {
        List<Book> books = bookRepository.findByGenreIdGenre(1L);
        for (Book b : books) {
            System.out.println(b);
        }
    }
}
