package hr.njakopcic.bibliotheca.controller;

import hr.njakopcic.bibliotheca.dto.ApiResponse;
import hr.njakopcic.bibliotheca.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBooks() {
        return new ResponseEntity<>(new ApiResponse(bookService.getAllBooks()), HttpStatus.OK);
    }
}
