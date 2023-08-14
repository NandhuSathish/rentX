import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventEmitterService {
  constructor() {}
  private subject = new Subject<any>();

  onSaveEvent() {
    this.subject.next(1);
  }
  getonSaveEvent(): Observable<any> {
    return this.subject.asObservable();
  }
  onDeleteEvent() {
    this.subject.next(1);
  }
  getonDeleteEvent(): Observable<any> {
    return this.subject.asObservable();
  }
  onEditEvent() {
    this.subject.next(1);
  }

  getonEditEvent(): Observable<any> {
    return this.subject.asObservable();
  }
}
