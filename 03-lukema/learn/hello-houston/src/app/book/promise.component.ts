import { Component, OnInit } from '@angular/core';

import { Book, BookService } from './book.service';


@Component({
  selector: 'app-promise',
  templateUrl: './promise.component.html',
  styleUrls: ['promise.component.css']
})

export class PromiseComponent implements OnInit {
  books: Book[];
  errorMessage: String;
  bookName: String;
  book = new Book();

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.fetchBooks();
  }

  fetchBooks(): void {
    this.bookService.getBooksWithPromise()
      .then(books => this.books = books,
        error => this.errorMessage = <any>error);
  }

  addBook(): void {
    this.bookService.addBookWithPromise(this.book)
      .then(book => {
        this.fetchBooks();
        this.reset();
        this.bookName = book.name;
      },
        error => this.errorMessage = <any>error);
  }

  private reset() {
    this.book.id = null;
    this.book.name = null;
    this.errorMessage = null;
    this.bookName = null;
  }
}
