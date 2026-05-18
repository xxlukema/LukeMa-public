import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileUtilService {

  async readFileAsDataUrlAsync(file: File): Promise<any> {

    console.debug('--------------- file:', file);

    const resultBase64 = await new Promise((resolve) => {
      const fileReader = new FileReader();
      fileReader.onload = (event: any) => resolve(event.target.result);
      fileReader.readAsDataURL(file);
    });

    return resultBase64;
  }

  readreadFileAsDataURL_Reference(file: File) : void {
    const reader = new FileReader();
    reader.onload = (event: any) => {
      // this.imageLocalUrls.push(event.target.result);
    };
    reader.readAsDataURL(file);
  }

  getFileIcon(name: string) {
    let clazz = '';
    if (name) {
      name = name.toLowerCase();
      const fields = name.split('.');
      let suffix = '';
      if (fields.length > 1) {
        suffix = fields[fields.length - 1];
      }

      if (suffix) {
        switch (suffix) {
          case 'pdf':
            clazz = 'fa-file-pdf';
            break;
          case 'csv':
          case 'xls':
          case 'xlsx':
            clazz = 'fa-file-excel';
            break;
          case 'jpg':
          case 'jpeg':
          case 'png':
            clazz = 'fa-file-image';
            break;
          case 'doc':
          case 'docx':
            clazz = 'fa-file-word';
            break;
          case 'txt':
            clazz = 'fa-file-text';
            break;
          case 'zip':
          case 'jar':
          case 'war':
          case 'rar':
            clazz = 'fa-file-archive';
            break;
          case 'ppt':
          case 'pptx':
            clazz = 'fa-file-powerpoint';
            break;
          case 'mp3':
          case 'wav':
            clazz = 'fa-file-audio';
            break;
          case 'mp4':
          case 'avi':
          case 'mov':
          case 'mpg':
            clazz = 'fa-file-video';
            break;
          default:
            clazz = 'fa-file';
            break;
        }
      }
    }
    if (clazz) {
      return clazz;
    } else {
      return 'fa-file';
    }
  }
}
