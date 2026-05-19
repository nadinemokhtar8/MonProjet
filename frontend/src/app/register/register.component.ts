import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/user.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  public user = new User();
  myForm!: FormGroup;
  err!: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.myForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    });
  }

  get formControls(): { [key: string]: AbstractControl } {
    return this.myForm.controls;
  }

  onRegister() {
    if (this.myForm.invalid) {
      return;
    }

    this.user = {
      username: this.myForm.get('username')?.value,
      email: this.myForm.get('email')?.value,
      password: this.myForm.get('password')?.value,
      roles: []
    };

    this.authService.registerUser(this.user).subscribe({
      next: () => {
        this.authService.setRegistredUser(this.user);
        this.err = '';
        this.toastr.success('veillez confirmer votre email', 'Confirmation');
        this.router.navigate(['/verifEmail']);
      },
      error: (err: any) => {
        this.err = err.error.message;
      }
    });
  }
}
