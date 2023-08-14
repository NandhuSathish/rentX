import { Component, OnInit } from '@angular/core';
import { firebaseStorage } from '../../config/firebase.config';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.css']
})
export class ImageUploadComponent implements OnInit {
 
  selectedFiles: File[] = [];
  isDragOver = false;
  images:String[]=[];
  base64Images: string[] = []; // Add this property
  showCross: boolean[] = []; // Array to track the visibility of cross mark

  ngOnInit(): void {
    // Initialize showCross array with false values
    this.showCross = Array(this.base64Images.length).fill(false);
  }
  constructor() { }
  // Toggle the visibility of cross mark on image hover
  toggleImage(index: number, show: boolean) {
    this.showCross[index] = show;
  }

  onFilesSelected(event: any) {
    this.selectedFiles = Array.from(event.target.files) as File[];
    this.loadSelectedFiles(); // Call the method to load base64 images
    console.log(this.selectedFiles)
  }
  private loadSelectedFiles() {
    // Reset the base64 images array
    this.base64Images = [];

    // Iterate over the selectedFiles array and load each file as base64
    this.selectedFiles.forEach((file: File) => {
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
    this.selectedFiles = Array.from(event.dataTransfer.files) as File[];
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
    if (this.selectedFiles.length > 0) {
      const totalFiles = this.selectedFiles.length;
      let uploadedCount = 0;

      for (let i = 0; i < totalFiles; i++) {
        const file = this.selectedFiles[i];
        const fileRef = firebaseStorage.ref().child(`product-images/${file.name}`);
        const uploadTask = fileRef.put(file);

        uploadTask.then(snapshot => {
          // Image upload successful
          uploadedCount++;

          if (uploadedCount === totalFiles) {
            console.log('All images uploaded successfully.');
          }

          snapshot.ref.getDownloadURL().then(downloadURL => {
            // Use the downloadURL for further processing or storing in your backend
alert(downloadURL)
           this.images.push(downloadURL)

          });
        }).catch(error => {
          // Image upload failed
          console.error('Image upload error:', error);
        });
      }
    }
  }
}
