import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../model/book.model';
import { Genre } from '../model/genre.model';
import { Image } from '../model/image.model';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-update-book',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './update-book.component.html',
  styleUrl: './update-book.component.css'
})
export class UpdateBookComponent implements OnInit {
  currentBook = new Book();
  genres!: Genre[];
  updatedGenreId!: number;
  myImage!: string;
  uploadedImage!: File;
  isImageUpdated = false;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private bookService: BookService) {}

  ngOnInit(): void {
    this.bookService.listeGenres().subscribe(genres => {
      this.genres = genres;
    });
    this.bookService.consulterBook(this.activatedRoute.snapshot.params['id']).subscribe(book => {
      this.currentBook = book;
      this.updatedGenreId = this.currentBook.genre?.idGenre;
      this.myImage = 'http://localhost:8088/books/api/image/loadfromFS/' + this.currentBook.idBook;
      if (!this.currentBook.images) {
        this.currentBook.images = [];
      }
    });
  }

  onImageUpload(event: any) {
    if (event.target.files && event.target.files.length) {
      this.uploadedImage = event.target.files[0];
      this.isImageUpdated = true;
      const reader = new FileReader();
      reader.readAsDataURL(this.uploadedImage);
      reader.onload = () => { this.myImage = reader.result as string; };
    }
  }

  onAddImageBook() {
    this.bookService.uploadImageBook(this.uploadedImage, this.uploadedImage.name, this.currentBook.idBook)
      .subscribe((img: Image) => {
        this.currentBook.images.push(img);
      });
  }

  supprimerImage(img: Image) {
    const conf = confirm('Etes-vous sûr ?');
    if (conf) {
      this.bookService.supprimerImage(img.idImage).subscribe(() => {
        const index = this.currentBook.images.indexOf(img, 0);
        if (index > -1) {
          this.currentBook.images.splice(index, 1);
        }
      });
    }
  }

  updateBook() {
    this.currentBook.genre = this.genres.find(genre => genre.idGenre == this.updatedGenreId)!;
    this.bookService.updateBook(this.currentBook).subscribe(() => {
      if (this.isImageUpdated) {
        this.bookService.uploadImageFS(this.uploadedImage, this.uploadedImage.name, this.currentBook.idBook).subscribe(() => {
          this.router.navigate(['books']);
        });
        return;
      }
      this.router.navigate(['books']);
    });
  }
}
