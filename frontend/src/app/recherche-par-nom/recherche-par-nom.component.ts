import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Book } from '../model/book.model';
import { BookService } from '../services/book.service';
import { SearchFilterPipe } from '../pipes/search-filter.pipe';

@Component({
  selector: 'app-recherche-par-nom',
  standalone: true,
  imports: [FormsModule, CommonModule, SearchFilterPipe],
  templateUrl: './recherche-par-nom.component.html',
  styleUrl: './recherche-par-nom.component.css'
})
export class RechercheParNomComponent implements OnInit {
  books: Book[] = [];
  allBooks: Book[] = [];
  nomBook = '';
  searchTerm = '';
  err = '';

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.bookService.listeBook().subscribe({
      next: (books) => {
        this.books = books;
        this.allBooks = books;
        this.err = '';
      },
      error: () => {
        this.err = 'Impossible de charger les livres.';
      }
    });
  }

  rechercherBooks() {
    if (!this.nomBook) {
      this.applyLocalFilter();
      return;
    }
    this.bookService.rechercherParNom(this.nomBook).subscribe({
      next: (books) => {
        this.books = books;
        this.err = '';
      },
      error: () => {
        this.err = 'Recherche par nom indisponible.';
      }
    });
  }

  onKeyUp(filterText: string) {
    this.searchTerm = filterText;
    this.applyLocalFilter();
  }

  private applyLocalFilter() {
    const filter = this.searchTerm.trim().toLowerCase();
    this.books = !filter
      ? [...this.allBooks]
      : this.allBooks.filter(item => item.nomBook.toLowerCase().includes(filter));
  }
}
