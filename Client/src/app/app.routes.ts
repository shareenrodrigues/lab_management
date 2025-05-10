import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TestComponent } from './test/test.component';
import { MainLayoutComponent } from './dashboard/main-layout.component';
import { AppointmentsComponent } from './appointment/appointment.component';
import { ResultComponent } from './results/results.component';

export const routes: Routes = [
    { path: '', title: 'Lab Management System', component: LoginComponent,},
    {
      path: '',
      component: MainLayoutComponent,
      children: [
        { path: 'dashboard/:role', component: DashboardComponent },
        { path: 'tests/:role', component: TestComponent },
        { path: 'appointments/:role', component: AppointmentsComponent },
        { path: 'results/:role', component: ResultComponent }
      ]
    }
];
