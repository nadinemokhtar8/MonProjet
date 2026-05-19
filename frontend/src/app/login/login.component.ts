import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { User } from '../model/user.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user = new User();
  err = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLoggedin() {
    this.authService.login(this.user).subscribe({
      next: (data) => {
        const jwToken = data.headers.get('Authorization');
        if (!jwToken) {
          this.err = 'Token de connexion introuvable.';
          return;
        }
        this.authService.saveToken(jwToken);
        this.err = '';
        this.router.navigate(['/books']);
      },
      error: () => {
        this.err = 'Login ou mot de passe errones.';
      }
    });
  }
}
