package Nhom6.TRANQUANGHIEN2280600922.services;

import Nhom6.TRANQUANGHIEN2280600922.entities.Book;
import Nhom6.TRANQUANGHIEN2280600922.repositories.IBookRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional 
public class BookService {

    private final IBookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooks(Integer pageNo, Integer pageSize, String sortBy) {
        return bookRepository.findAll(
                PageRequest.of(
                        pageNo != null ? pageNo : 0, 
                        pageSize != null ? pageSize : 20, 
                        Sort.by(sortBy != null ? sortBy : "id")
                )
        ).getContent();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(@NotNull Book book) {
        Book existingBook = bookRepository.findById(book.getId()).orElse(null);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPrice(book.getPrice());
            existingBook.setCategory(book.getCategory());
            bookRepository.save(existingBook);
        }
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBook(String keyword) {
        return bookRepository.searchBook(keyword);
    }
}