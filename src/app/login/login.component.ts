import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginApiService } from '../services/login-api.service';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';

@Component({
  standalone: true,
  selector: 'login',
  imports: [ReactiveFormsModule, CommonModule, ToastModule, InputTextModule, ButtonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService]
})

export class LoginComponent  implements OnInit{
  title = 'Lab Management System';
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private loginService : LoginApiService
    , private router: Router,
    private messageService: MessageService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  onSubmit(): void {
    debugger;
    console.log("LO")
    const { email, password } = this.loginForm.value;
    this.loginService.login(email, password).subscribe({
      next: (response) => {
        console.log('Login success:', response);
        this.messageService.add({
          severity: 'success',
          summary: 'Login Successful',
          detail: 'Welcome back!',
          life: 3000
        });
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['/dashboard', response.roleName]);
      },
      error: (err) => {
        console.error('Login failed', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Login Failed',
          detail: 'Invalid email or password',
          life: 3000
        });
      }
    });
  
  }
}
