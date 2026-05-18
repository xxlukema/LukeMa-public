# Dropdown `mat-select` Default Select

Sample Code: `activedash\nms-ng-ws\projects\5g\src\app\components\editaux`

## In Template

N.B. `snmpVersions: DropdownOption[]` is array/list of `[{ id: 1, label: 'SNMP v1' }]`. There are two different ways to select default:

1. `[compareWith]="nmsService.compareObjects"` + `formControlName="snmpVersion" [(ngModel)]="snmpVersion"` + `<mat-option *ngFor="let item of snmpVersions" [value]="item.id">`
2. `[compareWith]="nmsService.compareId"` + `formControlName="snmpVersion" [(ngModel)]="snmpVersion"` + `<mat-option *ngFor="let item of snmpVersions" [value]="item">`

            <!-- activedash\nms-ng-ws\projects\5g\src\app\components\editaux\editaux.component.html -->
            <!-- nmsService.compareObjects -->
            <!-- EITHER -->
            <div fxLayout fxLayoutAlign="center center">
              <span fxFlex="35" class="label required">
                SNMP Version
              </span>
              <mat-form-field fxFlex floatLabel="none">
                <mat-select [compareWith]="nmsService.compareObjects" formControlName="snmpVersion" name="snmpVersion" [(ngModel)]="snmpVersion"
                  (selectionChange)="changeSnmpVer();selectionChange();">
                  <mat-option *ngFor="let item of snmpVersions" [value]="item.id">
                    {{item.label}}
                  </mat-option>
                </mat-select>
              </mat-form-field>
            </div>
            <!-- OR -->
            <!-- OR -->
            <!-- OR -->
            <div fxLayout fxLayoutAlign="center center">
              <span fxFlex="35" class="label required">
                SNMP Version
              </span>
              <mat-form-field fxFlex floatLabel="none">
                <mat-select [compareWith]="nmsService.compareId" formControlName="snmpVersion" name="snmpVersion" [(ngModel)]="snmpVersion"
                  (selectionChange)="changeSnmpVer();selectionChange();">
                  <mat-option *ngFor="let item of snmpVersions" [value]="item">
                    {{item.label}}
                  </mat-option>
                </mat-select>
              </mat-form-field>
            </div>

            <!-- activedash\nms-ng-ws\projects\5g\src\app\components\editaux\editaux.component.html -->
            <!-- nmsService.compareObjects -->
            <div fxLayout fxLayoutAlign="center center">
              <span fxFlex="35" class="label required">
                Authentication Protocol
              </span>
              <mat-form-field fxFlex floatLabel="none">
                <mat-select [compareWith]="nmsService.compareObjects" formControlName="snmpv3AuthProto" name="snmpv3AuthProto"
                  [(ngModel)]="snmpv3AuthProto" (selectionChange)="selectionChange()">
                  <mat-option *ngFor="let item of snmpv3AuthProtos" [value]="item">
                    {{item}}
                  </mat-option>
                </mat-select>
              </mat-form-field>
            </div>

            <!-- activedash\nms-ng-ws\projects\core\src\app\components\report-create\create-report.component.html -->
            <!-- nmsService.compareId -->
            <div fxLayout fxLayoutAlign="center center">
              <span fxFlex="20em" class="label required">
                Report Type
              </span>
              <mat-form-field fxFlex floatLabel="none">
                <mat-select [compareWith]="nmsService.compareId" formControlName="reportType" name="reportType" [(ngModel)]="reportType"
                  (selectionChange)="changeReportType()">
                  <mat-option *ngFor="let item of reportTypes" [value]="item">
                    {{item.reportTypeName}}
                  </mat-option>
                </mat-select>
              </mat-form-field>
            </div>

## In Component

    /** activedash\nms-ng-ws\projects\core\src\app\components\report-create\create-report.service.ts */
    export interface DropdownOption {
      id: number,
      label: string
    }

    /** activedash\nms-ng-ws\projects\5g\src\app\components\editaux\editaux.component.ts */
    snmpVersions: DropdownOption[] = [
      { id: 1, label: 'SNMP v1' },
      { id: 2, label: 'SNMP v2' },
      { id: 3, label: 'SNMP v3' }
    ];
  
    snmpv3AuthProtos = [
      'MD5',
      'SHA'
    ];
  
    /** activedash\nms-ng-ws\projects\core\src\app\components\report-create\create-report.component.ts */
    reportOptions: DropdownOption[] = [
      { id: 1, label: 'Non-Recurring Report' },
      { id: 2, label: 'Scheduled Report' },
      { id: 3, label: 'Triggered Report' }
    ];
