package com.nadine.books.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nadine.books.entities.Book;
import com.nadine.books.entities.Image;
import com.nadine.books.repos.BookRepository;
import com.nadine.books.repos.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {

        Image img = new Image();

        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setImage(file.getBytes());

        return imageRepository.save(img);
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {

        final Optional<Image> dbImage = imageRepository.findById(id);

        Image img = new Image();

        img.setIdImage(dbImage.get().getIdImage());
        img.setName(dbImage.get().getName());
        img.setType(dbImage.get().getType());
        img.setImage(dbImage.get().getImage());

        return img;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {

        final Optional<Image> dbImage = imageRepository.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image uplaodImageProd(MultipartFile file, Long idBook) throws IOException {

        Book b = new Book();
        b.setIdBook(idBook);

        Image img = new Image();

        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setImage(file.getBytes());
        img.setBook(b);

        return imageRepository.save(img);
    }

    @Override
    public List<Image> getImagesParBook(Long bookId) {

        Book b = bookRepository.findById(bookId).get();

        return b.getImages();
    }
}