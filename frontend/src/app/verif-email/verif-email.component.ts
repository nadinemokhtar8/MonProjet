import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-verif-email',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './verif-email.component.html',
  styleUrl: './verif-email.component.css'
})
export class VerifEmailComponent {
  code!: string;

  constructor(private authService: AuthService, private router: Router) {}

  onValidateEmail() {
    this.authService.verifyEmail(this.code).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}
