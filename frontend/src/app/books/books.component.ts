import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Book } from '../model/book.model';
import { BookService } from '../services/book.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-books',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './books.component.html',
  styleUrl: './books.component.css'
})
export class BooksComponent implements OnInit {
  books: Book[] = [];
  err = '';
  apiurl = 'http://localhost:8088/books/api';
  readonly placeholderImage =
    'data:image/svg+xml;utf8,' +
    encodeURIComponent(
      `<svg xmlns="http://www.w3.org/2000/svg" width="160" height="90" viewBox="0 0 160 90">
        <rect width="160" height="90" fill="#f1f3f5"/>
        <text x="80" y="50" text-anchor="middle" font-family="Arial" font-size="12" fill="#6c757d">
          No image
        </text>
      </svg>`
    );

  constructor(private bookService: BookService, public authService: AuthService) {}

  ngOnInit(): void {
    this.chargerBooks();
  }

  chargerBooks() {
    this.bookService.listeBook().subscribe({
      next: (books) => {
        this.books = books;
        this.err = '';
        this.books.forEach((book) => {
          if (book.imagePath) {
            book.imageStr = this.apiurl + '/image/loadfromFS/' + book.idBook;
          } else if (book.images && book.images.length > 0) {
            book.imageStr = this.apiurl + '/image/get/' + book.images[0].idImage;
          } else {
            book.imageStr = this.placeholderImage;
          }
        });
      },
      error: () => {
        this.err = 'Impossible de charger les livres.';
        this.books = [];
      }
    });
  }

  supprimerBook(b: Book) {
    const conf = confirm('Etes-vous sûr ?');
    if (conf) {
      this.bookService.supprimerBook(b.idBook).subscribe(() => {
        this.chargerBooks();
      });
    }
  }
}
