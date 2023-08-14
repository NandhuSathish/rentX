import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AlertComponent } from '../components/dialogs/alert/alert.component';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  constructor(public dialog: MatDialog) {}

  openAlert(message: string) {
  
    
    const alertRef = this.dialog.open(AlertComponent,{data:{message:message}});
    return alertRef.afterClosed();
  }
}
