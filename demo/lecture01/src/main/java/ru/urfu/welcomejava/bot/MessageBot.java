package ru.urfu.welcomejava.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.welcomejava.db.MessageDB;
import ru.urfu.welcomejava.model.Message;

@Component
public class MessageBot extends TelegramLongPollingBot {
    private Logger logger = LoggerFactory.getLogger(MessageBot.class);

    @Autowired
    private MessageDB messages;
    @Value("${telegrambot.token:#{null}}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        org.telegram.telegrambots.meta.api.objects.Message tMessage = update.getMessage();
        if (update.hasMessage() && tMessage.hasText()) {

            Message inMessage = new Message();
            inMessage.setMessage(tMessage.getText());
            inMessage.setName(tMessage.getChat().getUserName());

            messages.add(inMessage);

            StringBuilder sb = new StringBuilder(tMessage.getText());


            SendMessage message = new SendMessage()
                    .setChatId(tMessage.getChatId())
                    .setText(sb.reverse().toString());
            try {
                execute(message); 
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "WelcomeBot";
    }

    @Override
    public String getBotToken() {
        if (token == null || token.isEmpty()){
            String message = "\n\nempty bot token, edit src/main/resources/application.properties, see https://core.telegram.org/bots#3-how-do-i-create-a-bot\n\n";
            logger.error(message);
            throw new RuntimeException(message);
        }
        return token;
    }
}
