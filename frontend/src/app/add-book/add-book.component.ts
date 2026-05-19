import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Book } from '../model/book.model';
import { Genre } from '../model/genre.model';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-add-book',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './add-book.component.html',
  styleUrl: './add-book.component.css'
})
export class AddBookComponent implements OnInit {
  newBook = new Book();
  genres!: Genre[];
  newIdGenre!: number;
  uploadedImage!: File;
  imagePath: any;
  err = '';

  constructor(private bookService: BookService, private router: Router) {}

  ngOnInit(): void {
    this.bookService.listeGenres().subscribe(genres => {
      this.genres = genres;
    });
  }

  onImageUpload(event: any) {
    this.uploadedImage = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(this.uploadedImage);
    reader.onload = () => { this.imagePath = reader.result; };
  }

  addBook() {
    this.newBook.genre = this.genres.find(genre => genre.idGenre == this.newIdGenre)!;
    this.bookService.ajouterBook(this.newBook).subscribe(book => {
      if (this.uploadedImage) {
        this.bookService.uploadImageFS(this.uploadedImage, this.uploadedImage.name, book.idBook).subscribe({
          next: () => this.router.navigate(['books']),
          error: () => {
            this.err = 'Livre ajoute, mais image non enregistree.';
          }
        });
        return;
      }
      this.router.navigate(['books']);
    }, () => {
      this.err = 'Impossible d\'ajouter le livre.';
    });
  }
}
