package com.nadine.books.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;
    private String nomBook;
    private Double prixBook;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    public Long getIdBook() {
		return idBook;
	}



	public Book() {
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

	@ManyToOne
    private Genre genre;

    @OneToMany(mappedBy = "book")
    private List<Image> images;

    private String imagePath;
}
