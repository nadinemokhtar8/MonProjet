package com.nadine.books.restcontrollers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nadine.books.dto.BookDTO;
import com.nadine.books.entities.Image;
import com.nadine.books.service.BookService;
import com.nadine.books.service.ImageService;

@RestController
@RequestMapping("/api/image")
@CrossOrigin("*")
public class ImageRestController {

    @Autowired
    ImageService imageService;

    @Autowired
    BookService bookService;

    @PostMapping("/upload")
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }

    @GetMapping("/get/info/{id}")
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        return imageService.getImage(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
    }

    @PostMapping("/uplaodImageBook/{idBook}")
    public Image uploadMultiImages(@RequestParam("image") MultipartFile file,
                                   @PathVariable("idBook") Long idBook) throws IOException {
        return imageService.uplaodImageProd(file, idBook);
    }

    @RequestMapping(value = "/getImagesBook/{idBook}", method = RequestMethod.GET)
    public List<Image> getImagesBook(@PathVariable("idBook") Long idBook) {
        return imageService.getImagesParBook(idBook);
    }

    @RequestMapping(value = "/uploadFS/{id}", method = RequestMethod.POST)
    public void uploadImageFS(@RequestParam("image") MultipartFile file, @PathVariable("id") Long id)
            throws IOException {
        BookDTO b = bookService.getBook(id);
        String originalFilename = file.getOriginalFilename();
        String extension = ".jpg";

        if (originalFilename != null && originalFilename.lastIndexOf('.') != -1) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }

        b.setImagePath(id + extension);
        Path imagesDir = Paths.get(System.getProperty("user.home"), "images");
        Files.createDirectories(imagesDir);
        Files.write(imagesDir.resolve(b.getImagePath()), file.getBytes());
        bookService.updateBook(b);
    }

    @RequestMapping(value = "/loadfromFS/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageFS(@PathVariable("id") Long id) throws IOException {
        BookDTO b = bookService.getBook(id);
        Path imagePath = Paths.get(System.getProperty("user.home"), "images", b.getImagePath());
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(Files.readAllBytes(imagePath));
    }
}
