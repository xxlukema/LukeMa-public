import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { BookRoutingModule } from './book-routing.module';

import { BookComponent } from './book.component';
import { ObservableComponent } from './observable.component';
import { PromiseComponent } from './promise.component';
import { BookService } from './book.service';

import { AppHighlightDirective } from './app-highlight.directive';
import { AppHostListenerDirective } from './app-hostlistener.directive';
import { AppIfDirective } from './app-if.directive';
import { AppCapitalizePipe } from './app-capitalize.pipe';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    BookRoutingModule
  ],
  declarations: [
    BookComponent,
    ObservableComponent,
    PromiseComponent,
    AppHighlightDirective,
    AppHostListenerDirective,
    AppIfDirective,
    AppCapitalizePipe
  ],
  exports: [
    AppHighlightDirective,
    AppHostListenerDirective,
    AppIfDirective,
    AppCapitalizePipe
  ],
  providers: [
    BookService
  ]
})

export class BookModule { }
