import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  //api call to get all tests
  getAllTests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/tests`);
  }

  //api call to book an appointment
  bookAppointment(appointmentData: {
    patientName: string; age: number;
    height: number; weight: number;
    appointmentDate: string; appointmentTime: string;
    totalCost: number; userId: number; testIds: number[];
  }): Observable<any> {
    return this.http.post(`${this.baseUrl}/appointments/createAppointment`, appointmentData, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      withCredentials: false
    });
  }

  //api call to get all appointmnets
  getAllAppointments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments`);
  }

  //api call to get appointment for a client
  getAppointmentsByUser(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments/user/${userId}`);
  }

  //api call to cancel an appointment for client
  cancelAppointment(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/cancelAppointment/${id}`, {});
  }

  //api call to reject an appointment for staff
  rejectAppointment(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/rejectAppointment/${id}`, {});
  }

  //api call to delete tests from an appointment
  deleteTestFromAppointment(appointmentId: number, testId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/appointments/${appointmentId}/test/${testId}`);
  }

  //api call to reschedule appointment date and time
  rescheduleAppointment(appointmentId: number, payload: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/appointments/reschedule/${appointmentId}`, payload);
  }

  //api call to get appointment test Results for a user
  getAppointmentsWithTestsAndResults(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments/user/${userId}/details`);
  }

  //api call to get all appointment test results
  getAllAppointmentsWithTestsAndResults(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointments/allResults`);
  }

}
