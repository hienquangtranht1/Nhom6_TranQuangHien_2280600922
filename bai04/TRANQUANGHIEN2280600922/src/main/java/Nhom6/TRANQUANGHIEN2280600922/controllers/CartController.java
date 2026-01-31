package Nhom6.TRANQUANGHIEN2280600922.controllers;

import Nhom6.TRANQUANGHIEN2280600922.daos.Cart;
import Nhom6.TRANQUANGHIEN2280600922.daos.Item;
import Nhom6.TRANQUANGHIEN2280600922.entities.Book;
import Nhom6.TRANQUANGHIEN2280600922.services.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final BookService bookService;

    public static final String CART_SESSION_KEY = "cart";

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());
        return "book/cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(HttpSession session, @PathVariable Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
            if (cart == null) {
                cart = new Cart();
                session.setAttribute(CART_SESSION_KEY, cart);
            }
            cart.addItems(new Item(book.getId(), book.getTitle(), book.getPrice(), 1));
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(HttpSession session, @PathVariable Long id) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart != null) {
            cart.removeItems(id);
        }
        return "redirect:/cart";
    }

    @GetMapping("/update/{id}/{quantity}")
    public String updateCart(HttpSession session, @PathVariable Long id, @PathVariable int quantity) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart != null) {
            cart.updateItems(id, quantity);
        }
        return "book/cart"; 
    }
    
    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
        return "redirect:/cart";
    }
}