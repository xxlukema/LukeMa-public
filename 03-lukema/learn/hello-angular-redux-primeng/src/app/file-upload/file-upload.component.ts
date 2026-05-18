import { HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { FileUploadService } from './file-upload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss'],
})
export class FileUploadComponent implements OnInit {
  constructor(private fileUploadService: FileUploadService) {}

  @ViewChild('fileUpload', { static: false }) fileUpload!: ElementRef;

  files: File[] = [];
  inProgress = false;
  progress = 0;

  ngOnInit(): void {}

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
              return event;
            case HttpEventType.Response:
              return event;
            default:
              return event;
          }
        }),
        catchError((error: HttpErrorResponse) => {
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
              return event;
            case HttpEventType.Response:
              return event;
            default:
              return event;
          }
        }),
        catchError((error: HttpErrorResponse) => {
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
      for (let index = 0; index < fileUpload.files.length; index++) {
        const file = fileUpload.files[index];
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
