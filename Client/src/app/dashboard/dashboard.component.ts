import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { CommonModule, NgFor } from '@angular/common';
import { TestComponent } from '../test/test.component';

import { ButtonModule } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'dashboard',
  standalone: true,
  imports: [ReactiveFormsModule, MenubarModule, CardModule, CommonModule, NgFor, ButtonModule, TestComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})

export class DashboardComponent implements OnInit {
  userRole:string | undefined;
  showClientRole: boolean = false;
  constructor(private router: Router, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.userRole = params.get('role')?.toString();
    });
    if(this.userRole === 'Client'){
      this.showClientRole = true;
    }
  }


  loadTests(): void {
    this.router.navigate(['/tests', this.userRole]);
  }

  loadAppointments() {
    this.router.navigate(['/appointments', this.userRole]);
  }

  loadResults(){
    this.router.navigate(['/results', this.userRole]);
  }

  logout() {
    this.router.navigate(['/']);
  }
  getLoggedInUserRole(): string {
    const userJson = localStorage.getItem('user');
    if (!userJson) return '';
    const user = JSON.parse(userJson);
    return user.roleName || '';
}

}
