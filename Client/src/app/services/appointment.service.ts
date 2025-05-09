import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

 
  getAllTests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tests`);
  }

  bookAppointment(appointmentData: {
    patientName: string; age: number;
    height: number; weight: number;
    appointmentDate: string; appointmentTime: string;
    totalCost: number; userId: number; testIds: number[];}): Observable<any> {
    return this.http.post(`${this.baseUrl}/appointments/createAppointment`, appointmentData, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      withCredentials: false
    });
  }

  getAllAppointments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments`);
  }

  getAppointmentsByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments/user/${userId}`);
  }

  cancelAppointment(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/cancelAppointment/${id}`, {});
  }

  rejectAppointment(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/rejectAppointment/${id}`, {});
  }
  
  deleteTestFromAppointment(appointmentId: number, testId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/appointments/${appointmentId}/test/${testId}`);
  }
  
  rescheduleAppointment(appointmentId: number, payload: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/reschedule/${appointmentId}`, payload);
  }
  
}
