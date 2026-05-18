import { Injectable } from '@angular/core';

export class RentProperty {
  constructor(public id: string,
    public summary: string,
    public details: string,
    public dateCreated: Date,
    public dateUpdated: Date) { }
}

@Injectable()
export class RentPropertyService {

}
