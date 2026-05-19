package com.nadine.books.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadine.books.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
