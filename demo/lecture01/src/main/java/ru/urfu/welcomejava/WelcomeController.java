package ru.urfu.welcomejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import ru.urfu.welcomejava.db.MessageDB;
import ru.urfu.welcomejava.model.Message;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

    @Autowired
    private MessageDB messages;

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome(){
        return "Welcome!";
    }

    @GetMapping("/")
    public String index(@RequestParam(name="name", defaultValue = "nobody") String name, Map<String, Object> model){
        Date date = new Date();
        model.put("time", date);
        model.put("message", "Welcome " + name);
        return "index";
    }

    @GetMapping("/message")
    public String message(Map<String, Object> model){
        model.put("message", new Message());
        return "message";
    }

    @PostMapping("/message")
    public RedirectView sendMessage(@ModelAttribute Message message, ModelMap model){
        messages.add(message);
        return new RedirectView("/messages");
    }

    @GetMapping("/messages")
    public String listMessage(Map<String, Object> model){
        model.put("messages", messages.list());
        return "list";
    }
}
