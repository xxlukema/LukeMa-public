import { Component, OnDestroy, OnInit } from '@angular/core';
import { Message, MessageService } from 'primeng/api';

@Component({
  selector: 'app-primeng',
  templateUrl: './my-primeng.component.html',
  styleUrls: ['./my-primeng.component.scss']
})
export class MyPrimengComponent implements OnInit, OnDestroy {

  /**
   * MessageService alternative does not require a value binding to an array.
   *
   * If use MessageService, This array
   *
   *    msgs: Message[] = [];  // --- Not needed if use MessageService.
   *    <p-messages [(value)]="msgs"></p-messages>  // --- '[(value)]="msgs"' is not needed if use MessageService.
   *
   * value binding is not needed.
   */
  msgs: Message[] = [];   // --- Not needed if use MessageService.

  constructor(private messageService: MessageService) { }

  addSingleWithMessageService() {
    this.messageService.add({ severity: 'success', summary: 'Service Message', detail: 'Via MessageService' });
  }

  addMultipleWithMessageService() {
    this.messageService.addAll([{ severity: 'success', summary: 'Service Message', detail: 'Via MessageService' },
    { severity: 'info', summary: 'Info Message', detail: 'Via MessageService' }]);
  }

  clearMessageService() {
    this.messageService.clear();
  }

  addOneWithMessageArray() {
    this.msgs.push({ severity: 'info', summary: 'Info Message', detail: 'PrimeNG rocks' });
  }

  clearMessageArray() {
    this.msgs = [];
  }

  /**
   * Called whenever entering the page/template.
   */
  ngOnInit(): void {
    console.log('MyPrimengComponent ngOnInit() called.');
  }

  /**
   * Called whenever leaving the page/template.
   */
  ngOnDestroy(): void {
    console.log('MyPrimengComponent ngOnDestroy() called.');
    /**
     * Unsbuscribe from Observable channels here.
     */
  }

  click1() {
    console.log('MyPrimengComponent click1() called.');
  }

  click2() {
    console.log('MyPrimengComponent click2() called.');
  }
}
