import { Component, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Login } from '../../models/login';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatIconModule
  ],

  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

    loginForm: FormGroup;  

  constructor(
    private toast: ToastrService,
    private service: AuthService,
    private router: Router) {
      this.loginForm = new FormGroup({
        email: new FormControl('', [Validators.required, Validators.email]),
        senha: new FormControl('', [Validators.required, Validators.minLength(3)])
      });
    }

  ngOnInit(): void { }

  logar() {
    if(this.loginForm.valid) {
      const creds: Login = this.loginForm.value;
      this.service.authenticate(creds).subscribe(
        resposta => {
          this.service.successfullLogin(resposta.headers.get('Authorization').substring(7));
          this.router.navigate([''], {
            queryParams: {},
            queryParamsHandling: 'merge'
          });
        },
        () => {
          this.toast.error('Usuário e/ou senha inválidos');
        }
      );
    }
  }  
}
