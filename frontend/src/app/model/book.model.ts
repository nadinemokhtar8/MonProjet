import { Genre } from './genre.model';
import { Image } from './image.model';

export class Book {
  idBook!: number;
  nomBook!: string;
  prixBook!: number;
  dateCreation!: Date;
  genre!: Genre;
  nomGenre!: string;
  images!: Image[];
  imagePath!: string;
  imageStr!: string;
}
