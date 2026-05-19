package com.nadine.books;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.nadine.books.entities.Book;
import com.nadine.books.entities.Genre;
import com.nadine.books.repos.BookRepository;
import com.nadine.books.repos.GenreRepository;

@SpringBootApplication
public class BooksApiApplication implements CommandLineRunner {

    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BooksApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        repositoryRestConfiguration.exposeIdsFor(Book.class);
        Genre roman;
        Genre science;

        if (genreRepository.count() == 0) {
            roman = new Genre();
            roman.setNomGenre("Roman");
            roman.setDescriptionGenre("Livres roman");
            roman = genreRepository.save(roman);

            science = new Genre();
            science.setNomGenre("Science");
            science.setDescriptionGenre("Livres science");
            science = genreRepository.save(science);
        } else {
            roman = genreRepository.findAll().stream()
                    .filter(genre -> "Roman".equalsIgnoreCase(genre.getNomGenre()))
                    .findFirst()
                    .orElse(null);
            science = genreRepository.findAll().stream()
                    .filter(genre -> "Science".equalsIgnoreCase(genre.getNomGenre()))
                    .findFirst()
                    .orElse(null);
        }

        if (bookRepository.count() == 0 && roman != null && science != null) {
            bookRepository.save(createBook("Le Petit Prince", 39.90, roman));
            bookRepository.save(createBook("Les Miserables", 55.00, roman));
            bookRepository.save(createBook("Breves reponses aux grandes questions", 48.50, science));
            bookRepository.save(createBook("Une histoire du temps", 44.90, science));
        }
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private Book createBook(String nom, double prix, Genre genre) {
        Book book = new Book();
        book.setNomBook(nom);
        book.setPrixBook(prix);
        book.setDateCreation(new Date());
        book.setGenre(genre);
        return book;
    }
}
