import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TableModule } from 'primeng/table';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { AppointmentService } from '../services/appointment.service';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { CalendarModule } from 'primeng/calendar';
import { ActivatedRoute } from '@angular/router';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'test-page',
  standalone: true,
  imports: [CommonModule, HttpClientModule, TableModule, ButtonModule,
    ToastModule, FormsModule, CardModule, CalendarModule, ProgressSpinnerModule],
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {
  tests: any[] = [];
  selectedTests: any[] = [];
  showBookingForm: boolean = false;
  loading: boolean = false;
  appointment = {
    patientName: '',
    age: 0,
    height: 0,
    weight: 0,
    appointmentDate: '',
    appointmentTime: '',
    totalCost: 0
  };
  minDate: Date = new Date();

  userRole: string | undefined;
  showClientRole: boolean = false;

  constructor(private http: HttpClient, private messageService: MessageService, private appointmentService: AppointmentService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loading = true;
    this.route.paramMap.subscribe(params => {
      this.userRole = params.get('role')?.toString();
    });
    if (this.userRole === 'Client') {
      this.showClientRole = true;
    }
    this.appointmentService.getAllTests().subscribe(data => {
      this.tests = data;
      this.loading = false;
    });
    this.minDate = new Date();
  }

  addToCart(): void {
    if (this.selectedTests.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'No Selection',
        detail: 'Please select at least one test.'
      });
      return;
    } else {
      this.showBookingForm = true;
    }
    localStorage.setItem('selectedTests', JSON.stringify(this.selectedTests));
  }

  confirmAppointment() {
    this.loading = true;
    const date = new Date(this.appointment.appointmentDate);
    const time = new Date(this.appointment.appointmentTime);

    const payload = {
      patientName: this.appointment.patientName,
      age: this.appointment.age,
      height: this.appointment.height,
      weight: this.appointment.weight,
      appointmentDate: date.toISOString().split('T')[0],
      appointmentTime: time.toTimeString().split(' ')[0],
      totalCost: this.totalCost,
      userId: this.getLoggedInUserId(),
      testIds: this.selectedTests.map(test => test.testId)
    };

    this.appointmentService.bookAppointment(payload).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Appointment booked successfully' });
        this.resetBooking();
        this.loading = false;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Booking failed' });
      }
    });
  }

  get totalCost(): number {
    return this.selectedTests.reduce((sum, t) => sum + parseFloat(t.price), 0);
  }

  resetBooking() {
    this.selectedTests = [];
    this.showBookingForm = false;
    this.appointment = { patientName: '', age: 0, height: 0, weight: 0, appointmentDate: '', appointmentTime: '', totalCost: 0 };
  }

  getLoggedInUserId(): number {
    const userJson = localStorage.getItem('user');
    if (!userJson) return 0; 
    const user = JSON.parse(userJson);
    return user.userId || user.id || 0;
  }

}
