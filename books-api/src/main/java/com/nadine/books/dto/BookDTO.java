package com.nadine.books.dto;

import java.util.Date;
import java.util.List;

import com.nadine.books.entities.Genre;
import com.nadine.books.entities.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long idBook;
    private String nomBook;
    private Double prixBook;
    private Date dateCreation;
    private Genre genre;
    private String nomGenre;
    private List<Image> images;
    private String imagePath;
	public Long getIdBook() {
		return idBook;
	}
	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}
	public String getNomBook() {
		return nomBook;
	}
	public void setNomBook(String nomBook) {
		this.nomBook = nomBook;
	}
	public Double getPrixBook() {
		return prixBook;
	}
	public void setPrixBook(Double prixBook) {
		this.prixBook = prixBook;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public String getNomGenre() {
		return nomGenre;
	}
	public void setNomGenre(String nomGenre) {
		this.nomGenre = nomGenre;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
