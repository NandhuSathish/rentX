import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
  selector: 'app-delete-toast',
  templateUrl: './delete-toast.component.html',
  styleUrls: ['./delete-toast.component.css']
})
export class DeleteToastComponent implements OnInit {

  constructor(
    private _snackBar: MatSnackBar,
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) {}

  ngOnInit(): void {}
  closeSnackBar() {
    this._snackBar.dismiss();
  }


}
