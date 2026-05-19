import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Book } from '../model/book.model';
import { Genre } from '../model/genre.model';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-recherche-par-genre',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './recherche-par-genre.component.html',
  styleUrl: './recherche-par-genre.component.css'
})
export class RechercheParGenreComponent implements OnInit {
  books: Book[] = [];
  IdGenre: number | null = null;
  genres: Genre[] = [];
  err = '';

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.bookService.listeGenres().subscribe({
      next: (genres) => {
        this.genres = genres;
        if (genres.length > 0) {
          this.IdGenre = genres[0].idGenre;
          this.onChange();
        }
      },
      error: () => {
        this.err = 'Impossible de charger les genres.';
      }
    });
  }

  onChange() {
    if (this.IdGenre == null) {
      this.books = [];
      return;
    }

    this.bookService.rechercherParGenre(Number(this.IdGenre)).subscribe({
      next: (books) => {
        this.books = books;
        this.err = '';
      },
      error: () => {
        this.err = 'Impossible de charger les livres pour ce genre.';
      }
    });
  }
}
