package com.nadine.books.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadine.books.dto.BookDTO;
import com.nadine.books.entities.Book;
import com.nadine.books.service.BookService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BookRESTController {

    @Autowired
    BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/all")
    public List<BookDTO> getAllBooksSecured() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/getbyid/{id}")
    public BookDTO getBookByIdSecured(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO) {
        return bookService.saveBook(bookDTO);
    }

    @PostMapping("/addbook")
    public BookDTO addBook(@RequestBody BookDTO bookDTO) {
        return bookService.saveBook(bookDTO);
    }

    @PutMapping
    public BookDTO updateBook(@RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @PutMapping("/updatebook")
    public BookDTO updateBookSecured(@RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
    }

    @DeleteMapping("/delbook/{id}")
    public void deleteBookSecured(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/booksgenre/{idGenre}")
    public List<Book> getBooksByGenreId(@PathVariable("idGenre") Long idGenre) {
        return bookService.findByGenreIdGenre(idGenre);
    }

    @GetMapping("/booksByName/{nom}")
    public List<Book> findByNomBookContains(@PathVariable("nom") String nom) {
        return bookService.findByNomBookContains(nom);
    }
}
