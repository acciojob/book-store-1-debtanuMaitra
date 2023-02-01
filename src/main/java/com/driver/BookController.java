package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(id);
        bookList.add(book);
        this.id += 1;
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity getBookById(@PathVariable("id") int id) {
        for(Book book: bookList) {
            if(book.getId() == id) {
                return new ResponseEntity(book, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id") int id) {
        for(Book book: bookList) {
            if(book.getId() == id) {
                bookList.remove(book);
                return new ResponseEntity("The book is deleted", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }


    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks() {
        return new ResponseEntity(bookList, HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks() {
        bookList.clear();
        String response = "All books are deleted";
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam("auth") String auth) {
        List<Book> authorBook = new ArrayList<>();
        for(Book book: bookList) {
            if(book.getAuthor().equals(auth)) {
                authorBook.add(book);
            }
        }
        return new ResponseEntity(authorBook, HttpStatus.FOUND);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam("genre") String genre) {
        List<Book> genreBook = new ArrayList<>();
        for(Book book: bookList) {
            if(book.getGenre().equals(genre)) {
                genreBook.add(book);
            }
        }
        return new ResponseEntity(genreBook, HttpStatus.FOUND);
    }
}
