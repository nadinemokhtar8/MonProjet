import { Routes } from '@angular/router';
import { BooksComponent } from './books/books.component';
import { AddBookComponent } from './add-book/add-book.component';
import { UpdateBookComponent } from './update-book/update-book.component';
import { LoginComponent } from './login/login.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { RegisterComponent } from './register/register.component';
import { VerifEmailComponent } from './verif-email/verif-email.component';
import { RechercheParGenreComponent } from './recherche-par-genre/recherche-par-genre.component';
import { RechercheParNomComponent } from './recherche-par-nom/recherche-par-nom.component';
import { ListeGenresComponent } from './liste-genres/liste-genres.component';
import { bookGuard } from './services/book.guard';
import { UserProfileComponent } from './user-profile/user-profile.component';

export const routes: Routes = [
  { path: 'books', component: BooksComponent },
  { path: 'add-book', component: AddBookComponent, canActivate: [bookGuard] },
  { path: 'updateBook/:id', component: UpdateBookComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'verifEmail', component: VerifEmailComponent },
  { path: 'rechercheParGenre', component: RechercheParGenreComponent },
  { path: 'rechercheParNom', component: RechercheParNomComponent },
  { path: 'listeGenres', component: ListeGenresComponent },
  { path: 'app-forbidden', component: ForbiddenComponent },
  { path: 'profile', component: UserProfileComponent },
  { path: '', redirectTo: 'books', pathMatch: 'full' }
];
