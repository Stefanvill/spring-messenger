package se.iths.stefan.springmessenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.stefan.springmessenger.model.Email;
import se.iths.stefan.springmessenger.model.Product;
import se.iths.stefan.springmessenger.service.MessageService;

import java.util.List;

@Controller
@RequestMapping("/email")
public class EmailController {

    private final MessageService messageService;

    public EmailController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String showForm(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);

        List<Product> productList = List.of(
                new Product(1L, "Snus", 50),
                new Product(2L, "Mus", 100)
        );

        double computedTotal = productList.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        model.addAttribute("products", productList);
        model.addAttribute("totalPrice", computedTotal);

        return "form";
    }

    @PostMapping
    public String mail(@ModelAttribute Email email, RedirectAttributes redirectAttributes) {
        messageService.send(email);
        redirectAttributes.addFlashAttribute("success", "Mail skickat");
        return "redirect:/email";
    }
}