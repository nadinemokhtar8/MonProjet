import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../model/user.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent implements OnInit {
  user: User | undefined;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.user = this.authService.getRegistredUser();
  }
}
