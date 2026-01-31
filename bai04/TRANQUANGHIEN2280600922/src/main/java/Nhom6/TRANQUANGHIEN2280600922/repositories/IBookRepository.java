package Nhom6.TRANQUANGHIEN2280600922.repositories;

import Nhom6.TRANQUANGHIEN2280600922.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
}