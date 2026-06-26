package com.example.mediacity.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(Long bookId) {
        super("Le livre " + bookId + " est déjà emprunté");
    }
}
