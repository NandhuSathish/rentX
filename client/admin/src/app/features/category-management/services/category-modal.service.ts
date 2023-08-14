import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddCategoryComponent } from '../components/add-category/add-category.component';
import { AddSubcategoryComponent } from '../components/add-subcategory/add-subcategory.component';
import { DeleteCategoryComponent } from '../components/delete-category/delete-category.component';
import { DeleteSubcategoryComponent } from '../components/delete-subcategory/delete-subcategory.component';
import { EditCategoryComponent } from '../components/edit-category/edit-category.component';
import { EditSubcategoryComponent } from '../components/edit-subcategory/edit-subcategory.component';

@Injectable({
  providedIn: 'root',
})
export class CategoryModalService {
  constructor(public dialog: MatDialog) {}

  addCategory() {
    const alertRef = this.dialog.open(AddCategoryComponent, {
      panelClass: 'my-dialog-container',
    });
    return alertRef.afterClosed();
  }
  addSubCategory(id: any) {
    const alertRef = this.dialog.open(AddSubcategoryComponent, {
      panelClass: 'my-dialog-container',
      data: {
        categoryId: id,
      },
    });
    return alertRef.afterClosed();
  }

  deleteCategory(id: number) {
    const alertRef = this.dialog.open(DeleteCategoryComponent, {
      panelClass: 'my-dialog-container',
      data: {
        categoryId: id,
      },
    });
    return alertRef.afterClosed();
  }

  deleteSubCategory(id: any) {
    const alertRef = this.dialog.open(DeleteSubcategoryComponent, {
      panelClass: 'my-dialog-container',
      data: {
        subCategoryId: id,
      },
    });
    return alertRef.afterClosed();
  }

  editCategory(id: number) {
    const alertRef = this.dialog.open(EditCategoryComponent, {
      panelClass: 'my-dialog-container',
      data: {
        categoryId: id,
      },
    });
    return alertRef.afterClosed();
  }
  editSubCategory(id: any) {
    const alertRef = this.dialog.open(EditSubcategoryComponent, {
      panelClass: 'my-dialog-container',
      data: {
        subCategoryId: id,
      },
    });
    return alertRef.afterClosed();
  }
}
