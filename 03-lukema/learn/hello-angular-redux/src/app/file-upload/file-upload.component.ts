import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { FileUploadService } from './file-upload.service';

@Component({
  standalone: false,
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
})
export class FileUploadComponent {
  constructor(private readonly fileUploadService: FileUploadService) {}

  @ViewChild('fileUpload', { static: false }) fileUpload!: ElementRef;

  files: File[] = [];
  inProgress = false;
  progress = 0;

  private uploadFiles() {
    this.fileUpload.nativeElement.value = '';
    this.inProgress = true;

    const formData: FormData = new FormData();
    this.files.forEach((item, i) => {
      formData.append('files[' + i + ']', item, item.name);
    });

    this.fileUploadService
      .upload(formData)
      .pipe(
        map((event) => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              this.progress = Math.round(
                (event.loaded * 100) / (event.total ? event.total : 1)
              );
              return { type: event.type, progress: this.progress };
            case HttpEventType.Response:
              return { type: event.type, body: event.body };
            default:
              return { type: event.type };
          }
        }),
        catchError((_error: HttpErrorResponse) => {
          this.inProgress = false;
          return of('upload failed.');
        })
      )
      .subscribe((event: any) => {
        if (typeof event === 'object') {
          console.log(event.body);
        }
      });
  }

  private uploadFilesOne() {
    this.fileUpload.nativeElement.value = '';
    this.files.forEach((file) => {
      this.uploadFileOne(file);
    });
  }

  uploadFileOne(file: File): any {
    this.inProgress = true;

    const formData: FormData = new FormData();
    formData.append('files', file, file.name);

    this.fileUploadService
      .upload(formData)
      .pipe(
        map((event) => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              this.progress = Math.round(
                (event.loaded * 100) / (event.total ? event.total : 1)
              );
              break;
            case HttpEventType.Response:
              break;
            default:
              break;
            }

            return event;
        }),
        catchError((_error: HttpErrorResponse) => {
          this.inProgress = false;
          return of(`${file.name} upload failed.`);
        })
      )
      .subscribe((event: any) => {
        if (typeof event === 'object') {
          console.log(event.body);
        }
      });
  }

  upload(): void {
    this.files = [];

    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (const element of fileUpload.files) {
        const file = element;
        this.files.push(file);
      }
      this.uploadFiles();
    };
    fileUpload.click();
  }

  download() {
    window.open('https://localhost:8443/spring/pdf/sample.pdf', 'downloadTab');
  }
}
