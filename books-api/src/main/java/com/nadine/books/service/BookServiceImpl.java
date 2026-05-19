package com.nadine.books.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadine.books.dto.BookDTO;
import com.nadine.books.entities.Book;
import com.nadine.books.entities.Genre;
import com.nadine.books.repos.BookRepository;
import com.nadine.books.repos.ImageRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BookDTO saveBook(BookDTO b) {
        return convertEntityToDto(bookRepository.save(convertDtoToEntity(b)));
    }

    @Override
    public BookDTO updateBook(BookDTO b) {
        Long oldBookImageId = null;
        if (getBook(b.getIdBook()).getImages() != null && !getBook(b.getIdBook()).getImages().isEmpty()) {
            oldBookImageId = getBook(b.getIdBook()).getImages().get(0).getIdImage();
        }

        Book book = bookRepository.save(convertDtoToEntity(b));

        Long newBookImageId = null;
        if (book.getImages() != null && !book.getImages().isEmpty()) {
            newBookImageId = book.getImages().get(0).getIdImage();
        }

        if (oldBookImageId != null && newBookImageId != null && !oldBookImageId.equals(newBookImageId)) {
            imageRepository.deleteById(oldBookImageId);
        }
        return convertEntityToDto(book);
    }

    @Override
    public void deleteBook(Book b) {
        bookRepository.delete(b);
    }

    @Override
    public void deleteBookById(Long id) {
        Book b = bookRepository.findById(id).orElse(null);
        if (b == null) {
            return;
        }
        try {
            if (b.getImagePath() != null) {
                Files.delete(Paths.get(System.getProperty("user.home") + "/images/" + b.getImagePath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO getBook(Long id) {
        return convertEntityToDto(bookRepository.findById(id).get());
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Book> findByNomBook(String nom) {
        return bookRepository.findByNomBook(nom);
    }

    @Override
    public List<Book> findByNomBookContains(String nom) {
        return bookRepository.findByNomBookContains(nom);
    }

    @Override
    public List<Book> findByNomPrix(String nom, Double prix) {
        return bookRepository.findByNomPrix(nom, prix);
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public List<Book> findByGenreIdGenre(Long id) {
        return bookRepository.findByGenreIdGenre(id);
    }

    @Override
    public List<Book> findByOrderByNomBookAsc() {
        return bookRepository.findByOrderByNomBookAsc();
    }

    @Override
    public List<Book> trierBooksNomsPrix() {
        return bookRepository.trierBooksNomsPrix();
    }

    @Override
    public BookDTO convertEntityToDto(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        if (book.getGenre() != null) {
            bookDTO.setNomGenre(book.getGenre().getNomGenre());
        }
        return bookDTO;
    }

    @Override
    public Book convertDtoToEntity(BookDTO bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
