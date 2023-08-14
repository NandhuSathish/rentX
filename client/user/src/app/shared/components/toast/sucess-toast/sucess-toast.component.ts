import { Component, Inject, OnInit } from '@angular/core';
import { MAT_SNACK_BAR_DATA, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-sucess-toast',
  templateUrl: './sucess-toast.component.html',
  styleUrls: ['./sucess-toast.component.css'],
})
export class SucessToastComponent implements OnInit {
  constructor(
    private _snackBar: MatSnackBar,
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) {}

  ngOnInit(): void {}
  closeSnackBar() {
    this._snackBar.dismiss();
  }
}
