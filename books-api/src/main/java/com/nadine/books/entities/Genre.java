package com.nadine.books.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenre;
    private String nomGenre;
    public String getNomGenre() {
		return nomGenre;
	}

	public void setNomGenre(String nomGenre) {
		this.nomGenre = nomGenre;
	}

	public String getDescriptionGenre() {
		return descriptionGenre;
	}

	public void setDescriptionGenre(String descriptionGenre) {
		this.descriptionGenre = descriptionGenre;
	}

	private String descriptionGenre;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private List<Book> books;
	public Long getIdGenre() {
		return idGenre;
	}

	public void setIdGenre(Long idGenre) {
		this.idGenre = idGenre;
	}
}
