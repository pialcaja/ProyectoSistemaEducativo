import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string ='';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    const credencials = {
      email: this.email,
      password: this.password
    };

    this.authService.login(credencials).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        alert('Login exitoso')
      },
      error: (error) => {
        console.error('Login fallido', error);
        alert('Email o contraseña inválidos')
      }
    });
  }
}
