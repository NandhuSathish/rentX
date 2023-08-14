import { Component, Inject, OnInit } from '@angular/core';
import { MAT_SNACK_BAR_DATA, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-error-toast',
  templateUrl: './error-toast.component.html',
  styleUrls: ['./error-toast.component.css'],
})
export class ErrorToastComponent implements OnInit {
  constructor(
    private _snackBar: MatSnackBar,
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) {}

  ngOnInit(): void {}
  closeSnackBar() {
    this._snackBar.dismiss();
  }
}
