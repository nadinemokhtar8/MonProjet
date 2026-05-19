import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Genre } from '../model/genre.model';
import { BookService } from '../services/book.service';
import { UpdateGenreComponent } from '../update-genre/update-genre.component';

@Component({
  selector: 'app-liste-genres',
  standalone: true,
  imports: [CommonModule, UpdateGenreComponent],
  templateUrl: './liste-genres.component.html',
  styleUrl: './liste-genres.component.css'
})
export class ListeGenresComponent implements OnInit {
  genres!: Genre[];
  updatedGenre: Genre = { idGenre: 0, nomGenre: '', descriptionGenre: '' };
  ajout = true;

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.genres = this.bookService.genresLocal;
  }

  genreUpdated(genre: Genre) {
    if (this.ajout) {
      this.bookService.ajouterGenre(genre);
    } else {
      this.bookService.updateGenre(genre);
    }
    this.updatedGenre = { idGenre: 0, nomGenre: '', descriptionGenre: '' };
    this.ajout = true;
    this.genres = [...this.bookService.genresLocal];
  }

  updateGenre(genre: Genre) {
    this.updatedGenre = { ...genre };
    this.ajout = false;
  }
}
