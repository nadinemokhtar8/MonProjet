package com.nadine.books.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadine.books.entities.Genre;
import com.nadine.books.repos.GenreRepository;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin("*")
public class GenreRESTController {

    @Autowired
    GenreRepository genreRepository;

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
