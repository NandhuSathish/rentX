import {
  Component,
  ElementRef,
  NgZone,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';
import { CategoryService } from '../../services/category.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { Subscription } from 'rxjs';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { firebaseStorage } from 'src/app/shared/config/firebase.config';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css'],
})
export class AddCategoryComponent implements OnInit, OnDestroy {
  formSubmitted = false;
  pattern = '^[a-zA-Z0-9]+(?:[ ][a-zA-Z0-9]+)*$';
  minLength = 3;
  maxLength = 20;
  coverImageURL: any;
  showErrorMessage = false;
  errorData: any;
  epochCurrentTime: any;
  addCategoryForm!: FormGroup;
  coverValidator=false;
  selectedCover: any;
  optionalImageValidator: any;
  base64Images: string[] = [];
  coverImageUrl: any;
  thumbnailImageUrl!: string;
  coverImage:any;

  constructor(
    private toastService: ToastService,
    private eventEmitter: EventEmitterService,
    private categoryService: CategoryService,
    private route: Router,
    private dialog: MatDialog,
    private ngxService: NgxUiLoaderService,
    public toast: ToastService,
    private _ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.epochCurrentTime = Math.floor(Date.now() / 1000);
    this.coverValidator = true;
    this.addCategoryForm = new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.pattern(this.pattern),
        Validators.minLength(this.minLength),
        Validators.maxLength(this.maxLength),
      ]),
      coverImage: new FormControl(''),
    });
  }
  handleInputChange() {
    this.showErrorMessage = false; // reset flag on input change
  }
  closeDialog() {
    this.addCategoryForm.reset(); // Reset the form
    this.formSubmitted = false;
    this.dialog.closeAll();
  }
  onSubmit() {
    this.formSubmitted = true;
    if (this.addCategoryForm.valid) {
      this.categoryService.addCategory(this.addCategoryForm.value).subscribe({
        next: (result: any) => {
          this._ngZone.run(() => {
            this.dialog.closeAll();

            this.toast.showSucessToast('Category Added Successfully', 'close');   

            this.eventEmitter.onSaveEvent();
          });
        },
        error: (error: any) => {
          this.showErrorMessage = true;

          this.errorData = error.error.errorMessage;
          this.errorData =
            this.errorData.charAt(0).toUpperCase() +
            this.errorData.slice(1).toLowerCase();
          alert(this.errorData);
        },
      });
    }
  }
  ngOnDestroy() {
    this.dialog.closeAll();
  }

  coverSelect(event: any) {
    const file = event.target.files[0];
    this.coverImage=event.target.files[0];
    if(file==null){
      this.coverValidator=false;
    }else{
      this.coverValidator=true;
   
    // Validate image size
    if (!this.validateImageSize(file)) {
      this.coverValidator = false;
      this.toastService.showErrorToast(
        'Image size should not exceed 2MB.',
        'close'
      );
      this.clearImageInput(event);
      return;
    }

    // Validate image format
    if (!this.validateImageFormat(file)) {
      this.coverValidator = false;
      this.toastService.showErrorToast(
        'Image should be in PNG or JPG format.',
        'close'
      );
      this.clearImageInput(event);
      return;
    }

    // Validate image ratio
    this.validateImageRatio(file)
      .then((isValidRatio) => {
        if (!isValidRatio) {
          this.coverValidator = false;
          this.toastService.showErrorToast(
            'Image exceeds the maximum allowed resolution.',
            'close'
          );
          this.clearImageInput(event);
        } else {
          this.coverValidator = true;
          this.selectedCover = event;
        }
      })
      .catch(() => {
        this.coverValidator = false;
        this.toastService.showErrorToast(
          'Error occurred while validating the image ratio.',
          'close'
        );
        this.clearImageInput(event);
      });
  }
}
  onCoverCompress() {
    if(this.coverImage==null)
    {
      this.coverValidator=false;
    }else{
      this.coverValidator=true;
    
    if (this.addCategoryForm.valid) {

      const file: File = this.selectedCover.target.files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const img = new Image();
        img.src = e.target.result;

        img.onload = () => {
          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');

          const maxWidth = 800; // Set the maximum width for the compressed image
          const maxHeight = 600; // Set the maximum height for the compressed image

          let width = img.width;
          let height = img.height;

          // Calculate the new width and height while maintaining the aspect ratio
          if (width > height) {
            if (width > maxWidth) {
              height *= maxWidth / width;
              width = maxWidth;
            }
          } else {
            if (height > maxHeight) {
              width *= maxHeight / height;
              height = maxHeight;
            }
          }

          // Set the canvas dimensions to the new width and height
          canvas.width = width;
          canvas.height = height;

          // Draw the image on the canvas with the new dimensions
          ctx?.drawImage(img, 0, 0, width, height);

          // Get the compressed image data as a Blob
          canvas.toBlob(
            (blob) => {
              if (blob) {
                const compressedFileName = `compressedCategory_${this.epochCurrentTime}`;
                // Create a reference for the compressed image in Firebase Storage
                const compressedFileRef = firebaseStorage
                  .ref()
                  .child(`category-images/${compressedFileName}`);
                const compressedUploadTask = compressedFileRef.put(blob);

                compressedUploadTask
                  .then((compressedSnapshot) => {
                    // Compressed image upload successful
                    compressedSnapshot.ref
                      .getDownloadURL()
                      .then((downloadURL) => {
                        this.thumbnailImageUrl = downloadURL;
                        this.addCategoryForm.controls['coverImage'].setValue(
                          this.thumbnailImageUrl
                        );

                        console.log('URL = ' + this.thumbnailImageUrl);
                        this.onSubmit();
                      });
                  })
                  .catch((compressedError) => {
                    // Compressed image upload failed
                    console.error(
                      'Compressed image upload error:',
                      compressedError
                    );
                  });
              }
            },
            'image/jpeg',
            1.0
          );
        };
      };

      reader.readAsDataURL(file);
    }
  }
  }

  validateImageSize(file: File): boolean {
    const maxSizeInBytes = 2 * 1024 * 1024; // 2MB
    return file.size <= maxSizeInBytes;
  }
  clearImageInput(event: any) {
    event.target.value = null;
  }
  validateImageFormat(file: File): boolean {
    const allowedFormats = ['image/png', 'image/jpeg'];
    return allowedFormats.includes(file.type);
  }

  validateImageRatio(file: File): Promise<boolean> {
    return new Promise((resolve, reject) => {
      const img = new Image();
      img.onload = () => {
        const width = img.width;
        const height = img.height;
        const maxWidth = 1280;
        const maxHeight = 1920;
        resolve(width <= maxWidth && height <= maxHeight);
      };
      img.onerror = () => {
        reject();
      };
      img.src = URL.createObjectURL(file);
    });
  }

}
