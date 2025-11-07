import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

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

  login() {
    const credencials = {
      email: this.email,
      password: this.password
    };

    this.authService.login(credencials).subscribe({
      next: (res: any) => {
        this.authService.saveTokens(res.accessToken, res.refreshToken);

        console.log('CAPTURA DE TOKENS - ' + res.accessToken);

        this.authService.initTokenMonitoring;

        const decodedToken: any = jwtDecode(res.accessToken);
        const roles: string[] = decodedToken.roles || [];
        
        console.log('CAPTURA DE ROL - ' + roles);

        if (roles.includes('ROLE_ADMIN')) {
          this.router.navigateByUrl('/admin');
        } else if (roles.includes('ROLE_ALUMNO')) {
          this.router.navigateByUrl('/alumno');
        } else if (roles.includes('ROLE_DOCENTE')) {
          this.router.navigateByUrl('/docente');
        } else {
          this.router.navigateByUrl('/')
        }
      },
      error: (error) => {
        console.error('Login fallido', error);
        alert('Email o contraseña inválidos')
      }
    });
  }
}
