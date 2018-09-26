package ru.urfu.welcomejava.db;

import org.springframework.stereotype.Component;
import ru.urfu.welcomejava.model.Message;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDB {

    private List<Message> messages = new ArrayList<>();

    public boolean add(Message message) {
        return messages.add(message);
    }

    public List<Message> list(){
        return messages;
    }

}
