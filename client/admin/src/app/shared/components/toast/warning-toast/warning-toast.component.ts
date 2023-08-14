import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-warning-toast',
  templateUrl: './warning-toast.component.html',
  styleUrls: ['./warning-toast.component.css']
})
export class WarningToastComponent implements OnInit {
  constructor(
    private _snackBar: MatSnackBar,
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) {}

  ngOnInit(): void {}
  closeSnackBar() {
    this._snackBar.dismiss();
  }

}
