import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Genre } from '../model/genre.model';

@Component({
  selector: 'app-update-genre',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './update-genre.component.html',
  styleUrl: './update-genre.component.css'
})
export class UpdateGenreComponent {
  @Input()
  genre!: Genre;

  @Input()
  ajout!: boolean;

  @Output()
  genreUpdated = new EventEmitter<Genre>();

  saveGenre() {
    this.genreUpdated.emit(this.genre);
  }
}
