package Nhom6.TRANQUANGHIEN2280600922.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Title must not be empty") 
    @Size(max = 50, min = 1, message = "Title must be less than 50 characters") 
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    @NotNull(message = "Price is required") // Không được để null
    @Min(value = 1, message = "Price must be greater than 0") // Giá phải > 0
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;
}