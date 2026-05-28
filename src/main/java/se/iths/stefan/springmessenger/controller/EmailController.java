package se.iths.stefan.springmessenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.stefan.springmessenger.service.MessageService;

@Controller
@RequestMapping("/email")
public class EmailController {

    private final MessageService messageService;

    public EmailController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping
    public String mail(RedirectAttributes redirectAttributes, String message, String recipient) {
        messageService.send(message, recipient);
        redirectAttributes.addFlashAttribute("success", "Mail skickat");
        return "redirect:/email";
    }
}