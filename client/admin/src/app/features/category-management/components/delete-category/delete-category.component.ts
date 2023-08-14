import {
  Component,
  EventEmitter,
  Inject,
  OnDestroy,
  OnInit,
} from '@angular/core';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-delete-category',
  templateUrl: './delete-category.component.html',
  styleUrls: ['./delete-category.component.css'],
})
export class DeleteCategoryComponent implements OnDestroy {
  deleteCateogrySubscription!: Subscription;
  errorData: any;
  constructor(
    private eventEmitter: EventEmitterService,
    private categoryService: CategoryService,
    private dialogRef: MatDialogRef<DeleteCategoryComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { categoryId: number },
    public toast: ToastService
  ) {}
  ngOnDestroy(): void {
    this.dialogRef.close();
  }

  get categoryId(): number {
    return this.data.categoryId;
  }

  onCancel() {
    this.dialogRef.close();
  }

  onDelete() {
    this.deleteCateogrySubscription = this.categoryService
      .deleteCategory(this.categoryId)
      .subscribe({
        next: (result: any) => {
          this.toast.showDeleteToast(' Category Deleted Successfully', 'close');
          this.dialogRef.close({ success: true });
          this.eventEmitter.onDeleteEvent();
        },
        error: (error: any) => {
          this.errorData = error.error.errorMessage;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          this.toast.showErrorToast(this.errorData, 'close');
        },
      });
  }
}
