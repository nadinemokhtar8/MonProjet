import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../model/book.model';
import { Genre } from '../model/genre.model';
import { Image } from '../model/image.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BookService {
  apiURL = 'http://localhost:8088/books/api';
  genresLocal: Genre[] = [
    { idGenre: 1, nomGenre: 'Roman', descriptionGenre: '' },
    { idGenre: 2, nomGenre: 'Science', descriptionGenre: '' }
  ];

  constructor(private http: HttpClient) {}

  listeBook(): Observable<Book[]> {
    return this.http.get<Book[]>(this.apiURL);
  }

  ajouterBook(book: Book): Observable<Book> {
    return this.http.post<Book>(this.apiURL + '/addbook', book, httpOptions);
  }

  supprimerBook(id: number) {
    const url = `${this.apiURL}/delbook/${id}`;
    return this.http.delete(url, httpOptions);
  }

  consulterBook(id: number): Observable<Book> {
    const url = `${this.apiURL}/${id}`;
    return this.http.get<Book>(url);
  }

  updateBook(book: Book): Observable<Book> {
    return this.http.put<Book>(this.apiURL + '/updatebook', book, httpOptions);
  }

  listeGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.apiURL + '/genre');
  }

  rechercherParGenre(idGenre: number): Observable<Book[]> {
    const url = `${this.apiURL}/booksgenre/${idGenre}`;
    return this.http.get<Book[]>(url);
  }

  rechercherParNom(nom: string): Observable<Book[]> {
    const url = `${this.apiURL}/booksByName/${nom}`;
    return this.http.get<Book[]>(url);
  }

  uploadImage(file: File, filename: string): Observable<Image> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.apiURL}/image/upload`;
    return this.http.post<Image>(url, imageFormData);
  }

  loadImage(id: number): Observable<Image> {
    const url = `${this.apiURL}/image/get/info/${id}`;
    return this.http.get<Image>(url);
  }

  uploadImageBook(file: File, filename: string, idBook: number): Observable<any> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.apiURL}/image/uplaodImageBook/${idBook}`;
    return this.http.post(url, imageFormData);
  }

  supprimerImage(id: number) {
    const url = `${this.apiURL}/image/delete/${id}`;
    return this.http.delete(url, httpOptions);
  }

  uploadImageFS(file: File, filename: string, idBook: number): Observable<any> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.apiURL}/image/uploadFS/${idBook}`;
    return this.http.post(url, imageFormData);
  }

  ajouterGenre(genre: Genre) {
    this.genresLocal.push({ ...genre });
  }

  updateGenre(genre: Genre) {
    const index = this.genresLocal.findIndex((g) => g.idGenre === genre.idGenre);
    if (index > -1) {
      this.genresLocal[index] = { ...genre };
    }
  }
}
