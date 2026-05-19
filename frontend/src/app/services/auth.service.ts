import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { User } from '../model/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  users: User[] = [
    { username: 'admin', password: '123', roles: ['ADMIN'], email: 'admin@nadinebooks.com' },
    { username: 'nadine', password: '123', roles: ['USER'], email: 'nadine@nadinebooks.com' }
  ];
  public loggedUser!: string;
  public isloggedIn = false;
  public roles!: string[];
  apiURL = 'http://localhost:8081/users';
  token!: string;
  private helper = new JwtHelperService();
  private registredUser = new User();

  constructor(private router: Router, private http: HttpClient) {}

  login(user: User) {
    return this.http.post<User>(this.apiURL + '/login', user, { observe: 'response' });
  }

  saveToken(jwt: string) {
    localStorage.setItem('jwt', jwt);
    this.token = jwt;
    this.decodeJWT();
  }

  loadToken() {
    const jwt = localStorage.getItem('jwt');
    if (!jwt) {
      this.clearSession();
      return;
    }

    this.token = jwt;

    if (this.helper.isTokenExpired(jwt)) {
      this.clearSession();
      return;
    }

    this.decodeJWT();
  }

  getToken(): string | undefined {
    return this.token;
  }

  decodeJWT() {
    if (!this.token) {
      return;
    }
    const decodedToken = this.helper.decodeToken(this.token);
    this.roles = decodedToken.roles ?? [];
    this.loggedUser = decodedToken.sub;
    this.isloggedIn = true;
  }

  isAdmin(): Boolean {
    if (!this.roles) {
      return false;
    }
    return this.roles.indexOf('ADMIN') >= 0;
  }

  logout() {
    this.clearSession();
    this.router.navigate(['/login']);
  }

  isTokenExpired(): Boolean {
    if (!this.token) {
      return true;
    }
    return this.helper.isTokenExpired(this.token);
  }

  registerUser(user: User) {
    return this.http.post<User>(this.apiURL + '/register', user, { observe: 'response' });
  }

  verifyEmail(code: string): Observable<User> {
    return this.http.post<User>(this.apiURL + '/verifyEmail?code=' + code, {});
  }

  setRegistredUser(user: User) {
    this.registredUser = user;
  }

  getRegistredUser(): User {
    return this.registredUser;
  }

  private clearSession() {
    this.loggedUser = undefined!;
    this.roles = [];
    this.token = undefined!;
    this.isloggedIn = false;
    localStorage.removeItem('jwt');
  }
}
