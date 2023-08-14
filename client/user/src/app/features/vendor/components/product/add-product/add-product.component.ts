import { Component, NgZone, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  Validators,
  FormBuilder,
  FormArray,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/shared/services/toast.service';
import { EventEmitterService } from 'src/app/services/event-emitter.service';
import { StoreService } from '../../../services/store.service';
import { firebaseStorage } from 'src/app/shared/config/firebase.config';
import { NgxImageCompressService } from 'ngx-image-compress';
import { ProductService } from '../../../services/product.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
interface ImageData {
  original: string;
  compressed: string;
}
@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent implements OnInit {
  invalidImages: string[] = [];
  validFiles: File[] = [];
  productDetailsForm!: FormGroup;
  formSubmitted = false;
  coverImageURL: any;
  thumbnailImageUrl: any;
  formData: String[] = [];
  optionalImageValidator: any;
  stockRegex = new RegExp(/^(?!0)\d{1,6}$/);
  priceRegex = new RegExp(/^[0-9]{1,7}$/);
  coverValidator: any;
  descriptionRegex = new RegExp(
    /^(?!\s)[a-zA-Z0-9_\s!@#$%^&*()-+=~`{}[\]|:;"'<>,.?/]{3,50}(?<!\s)$/
  );
  roadRegex = new RegExp(
    /^(?!\s)[a-zA-Z0-9_\s!@#$%^&*()-+=~`{}[\]|:;"'<>,.?/]{3,20}(?<!\s)$/
  );
  selectedFiles: File[] = [];
  isDragOver = false;
  images: String[] = [];
  ImageUrl: String[] = [];
  coverImageUrl: ImageData[] = [];
  selectedCover: any;
  selectedCategoryId: any = null;
  base64Images: string[] = []; // Add this property
  showCross: boolean[] = []; // Array to track the visibility of cross mark
  productId: any;
  allCategory: any;
  allStore: any;
  categoryInit = false;
  epochCurrentTime: any;
  selectedSubcategory: any;

  constructor(
    private eventEmitter: EventEmitterService,
    private productService: ProductService,
    private toastService: ToastService,
    private _ngZone: NgZone,
    private route: Router,
    private formBuilder: FormBuilder,
    private imageCompress: NgxImageCompressService,
    private ngxService: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.epochCurrentTime = Math.floor(Date.now() / 1000); // Divide by 1000 to convert from milliseconds to seconds
    console.log(this.epochCurrentTime);
    this.coverValidator = true;
    this.optionalImageValidator = true;
    this.productService.getCategory().subscribe({
      next: (data: any) => {
        console.log('Category = ', data);
        this.allCategory = data;
        this.addSpecification();
      },
      error: () => {},
    });

    this.productService.listAllStores().subscribe({
      next: (data: any) => {
        console.log('Store =', data);
        this.allStore = data.result;
      },
      error: () => {},
    });

    // Initialize showCross array with false values
    this.showCross = Array(this.base64Images.length).fill(false);
    this.productDetailsForm = this.formBuilder.group({
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.roadRegex),
      ]),
      category: new FormControl('', [Validators.required]),
      subCategory: new FormControl('', [Validators.required]),
      store: new FormControl('', [Validators.required]),
      availableStock: new FormControl('', []),
      price: new FormControl('', [
        Validators.required,
        Validators.maxLength(20),
        Validators.pattern(this.stockRegex),
      ]),
      description: new FormControl('', [
        Validators.required,
        Validators.maxLength(50),
        Validators.pattern(this.descriptionRegex),
      ]),
      stock: new FormControl('', [
        Validators.required,
        Validators.maxLength(6),
        Validators.pattern(this.stockRegex),
      ]),
      specification: this.formBuilder.array([]),
    });
  }
  get specificationControls(): FormArray {
    return this.productDetailsForm.get('specification') as FormArray;
  }

  loadSubcategory() {
    this.productDetailsForm.get('subCategory')?.reset();
    this.productService.getSubcategory(this.selectedCategoryId).subscribe({
      next: (data: any) => {
        this.categoryInit = true;
        console.log(this.categoryInit);

        console.log('Sub category =', data);
        this.selectedSubcategory = data;
      },
      error: () => {},
    });
  }

  addSpecification(): void {
    if (this.specificationControls.length < 10) {
      const specificationGroup = this.formBuilder.group({
        key: [
          '',
          [
            Validators.required,
            Validators.maxLength(20),
            Validators.pattern(this.roadRegex),
          ],
        ],
        value: [
          '',
          [
            Validators.required,
            Validators.maxLength(20),
            Validators.pattern(this.roadRegex),
          ],
        ],
      });

      this.specificationControls.push(specificationGroup);
    }
  }

  removeSpecification(index: number): void {
    this.specificationControls.removeAt(index);
  }

  onproductDetailsSubmit() {
    console.log(this.productDetailsForm.value);
    const formValue = this.productDetailsForm.value;
    const coverImage = this.coverImageURL;
    const thumbnail = this.thumbnailImageUrl;

    // Convert the specification array into an object
    const specificationObject = formValue.specification.reduce(
      (acc: any, spec: any) => {
        acc[spec.key] = spec.value;
        return acc;
      },
      {}
    );

    const formData = {
      ...formValue,
      specification: specificationObject,
      coverImage,
      thumbnail,
    };

    this.formData.push(formData);
    console.log(this.formData[0]);
    this.formSubmitted = true;
    if (this.productDetailsForm.valid) {
      this.productService.addProductDetails(formData).subscribe({
        next: (data: any) => {
          this.uploadImages();
          this.productId = data.id;
        },
        error: (error: any) => {
          this.ngxService.stop();
          this._ngZone.run(() => {
            this.route.navigate(['/vendor/addProduct']);
          });
          this.toastService.showErrorToast(error.error.errorMessage, 'close');
        },
      });
    }
  }

  toggleImage(index: number, show: boolean) {
    this.showCross[index] = show;
  }

  onFilesSelected(event: any) {
    const files = Array.from(event.target.files) as File[];

    // if(this.selectedFiles!=this.validFiles)
    // { this.base64Images = [];}

    if (files.length > 4) {
      const errorMessage = 'You can only select up to 4 images.';
      this.toastService.showErrorToast(errorMessage, 'close');
      this.clearMultiImage(event);
      return;
    }

    this.selectedFiles = files;
    this.validateSelectedImages(event);
    console.log(this.selectedFiles);
  }

  clearMultiImage(event: any) {
    event.target.value = null;
  }

  validateSelectedImages(event: any) {
    const maxSizeInBytes = 2 * 1024 * 1024; // 2 MB
    const maxResolutionWidth = 1280;
    const maxResolutionHeight = 1920;
    const allowedExtensions = ['jpg', 'png'];

    const validFiles: File[] = [];
    const invalidImages: string[] = [];

    for (const file of this.selectedFiles) {
      const fileSize = file.size;
      const fileExtension: string | undefined = file.name.split('.').pop();
      const image = new Image();

      // Check file size
      if (fileSize > maxSizeInBytes) {
        invalidImages.push(file.name);
        // Show alert for images with invalid size
        // const errorMessage = `"${file.name}" exceeds the maximum allowed file size of 2MB.`;
        const errorMessage = `Images exceeds the maximum allowed size of 2MB.`;

        this.toastService.showErrorToast(errorMessage, 'close');
        this.clearMultiImage(event);

        continue;
      }

      // Check file extension
      if (
        !fileExtension ||
        !allowedExtensions.includes(fileExtension.toLowerCase())
      ) {
        invalidImages.push(file.name);
        // Show alert for images with invalid extension
        // const errorMessage = `"${file.name}" is not JPG /PNG format`;
        const errorMessage = `Selected images are not in JPG /PNG format`;
        this.toastService.showErrorToast(errorMessage, 'close');
        this.clearMultiImage(event);
        invalidImages.length = 0;
        continue;
      }

      // Check image resolution
      image.onload = () => {
        const width = image.width;
        const height = image.height;

        if (width > maxResolutionWidth || height > maxResolutionHeight) {
          invalidImages.push(file.name);
          // Show alert for images with invalid resolution
          const errorMessage =
            'Selected images have exceeded the maximum allowed resolution.';
          this.toastService.showErrorToast(errorMessage, 'close');
          this.clearMultiImage(event);
          invalidImages.length = 0;
        } else {
          validFiles.push(file);
        }

        // Load selected files after all validations are complete
        if (
          validFiles.length + invalidImages.length ===
          this.selectedFiles.length
        ) {
          this.selectedFiles = validFiles;
          this.loadSelectedFiles();
        }
      };

      image.src = URL.createObjectURL(file);
    }

    if (invalidImages.length > 1) {
      // Show error message or alert with the list of invalid images
      const errorMessage = `Selected images contains invalid images:\n${invalidImages.join(
        '\n'
      )}`;
      this.toastService.showErrorToast(errorMessage, 'close');
      this.clearMultiImage(event);
    }
  }

  private loadSelectedFiles() {
    this.base64Images = [];
    this.selectedFiles.forEach((file: File) => {
      this.optionalImageValidator = true;
      const reader = new FileReader();
      reader.onload = () => {
        this.base64Images.push(reader.result as string);
      };
      reader.readAsDataURL(file);
    });
  }

  removeImage(index: number) {
    this.base64Images.splice(index, 1);
    this.selectedFiles.splice(index, 1);
  }

  onDrop(event: any) {
    event.preventDefault();
    this.isDragOver = false;
    // Process the dropped files
    // You can access the dropped files using `event.dataTransfer.files`

    const files = Array.from(event.dataTransfer.files) as File[];
    this.selectedFiles = files;
    this.validateSelectedImages(event);
    console.log(this.selectedFiles);
  }

  onDragOver(event: any) {
    event.preventDefault();
    this.isDragOver = true;
  }

  onDragLeave(event: any) {
    event.preventDefault();
    this.isDragOver = false;
  }
  uploadImages() {
    console.log(this.productDetailsForm.value);
    if (this.selectedFiles.length > 0) {
      const totalFiles = this.selectedFiles.length;
      let uploadedCount = 0;
      const imagesObject: any = {};

      for (let i = 0; i < totalFiles; i++) {
        const file = this.selectedFiles[i];

        const fileRef = firebaseStorage
          .ref()
          .child(
            `product-images/${this.epochCurrentTime}${i},${this.productDetailsForm.value.name}`
          );
        const uploadTask = fileRef.put(file);

        uploadTask
          .then((snapshot) => {
            // Image upload successful
            uploadedCount++;
            const key = `image${uploadedCount}`;

            if (uploadedCount === totalFiles) {
              console.log('All images uploaded successfully.', i);
            }

            snapshot.ref.getDownloadURL().then((downloadURL) => {
              // Use the downloadURL for further processing or storing in your backend
              imagesObject[key] = downloadURL;
              // alert(downloadURL)
              // this.images.push(downloadURL)
              this.ImageUrl.push(imagesObject);
              const finalData = {
                productId: this.productId,
                ...imagesObject,
              };
              console.log('finalData:', finalData);
              this.images = finalData;
              if (this.ImageUrl.length == totalFiles) {
                this.addOptionalImages();
              }
            });
          })
          .catch((error) => {
            this.ngxService.stop();
            this.toastService.showErrorToast(
              'Failed to upload Image.',
              'close'
            );
            console.error('Image upload error:', error);
          });
      }
    }
  }

  addOptionalImages() {
    this.productService.sentImages(this.images).subscribe({
      next: (data: any) => {
        this._ngZone.run(() => {
          this.eventEmitter.onSaveEvent();
          this.route.navigate(['/vendor/products']);
        });
        this.toastService.showSucessToast('Product added sucessfully', 'close');
      },
      error: (error: any) => {
        this.ngxService.stop();
        this.toastService.showErrorToast(error.error.errorMessage, 'close');
      },
    });
  }

  coverSelect(event: any) {
    const file = event.target.files[0];
    console.log(this.productDetailsForm);
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
          console.log(this.productDetailsForm.value);
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

  validateImageSize(file: File): boolean {
    const maxSizeInBytes = 2 * 1024 * 1024; // 2MB
    return file.size <= maxSizeInBytes;
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

  clearImageInput(event: any) {
    event.target.value = null;
  }

  onCoverCompress() {
    this.formSubmitted = true;
    if (this.selectedCover == null) {
      this.coverValidator = false;
    }
    if (this.base64Images.length === 0) {
      this.optionalImageValidator = false;
    } else if (this.selectedCover != null && this.base64Images.length != 0) {
      if (this.productDetailsForm.valid) {
        this.ngxService.start();
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
                  // Create a reference for the original image in Firebase Storage
                  const coverFileName = `cover_${this.epochCurrentTime}`;
                  const originalFileRef = firebaseStorage
                    .ref()
                    .child(`product-images/${coverFileName}`);
                  const originalUploadTask = originalFileRef.put(file);

                  originalUploadTask
                    .then((originalSnapshot) => {
                      // Original image upload successful

                      const compressedFileName = `compressed_${this.epochCurrentTime}`;
                      // Create a reference for the compressed image in Firebase Storage
                      const compressedFileRef = firebaseStorage
                        .ref()
                        .child(`product-images/${compressedFileName}`);
                      const compressedUploadTask = compressedFileRef.put(blob);

                      compressedUploadTask
                        .then((compressedSnapshot) => {
                          // Compressed image upload successful
                          Promise.all([
                            originalSnapshot.ref.getDownloadURL(),
                            compressedSnapshot.ref.getDownloadURL(),
                          ]).then((downloadURLs) => {
                            this.coverImageURL = downloadURLs[0];
                            this.thumbnailImageUrl = downloadURLs[1];

                            console.log(this.coverImageUrl);
                            this.onproductDetailsSubmit();
                          });
                        })
                        .catch((compressedError) => {
                          // Compressed image upload failed
                          console.error(
                            'Compressed image upload error:',
                            compressedError
                          );
                        });
                    })
                    .catch((originalError) => {
                      // Original image upload failed
                      console.error(
                        'Original image upload error:',
                        originalError
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
}
