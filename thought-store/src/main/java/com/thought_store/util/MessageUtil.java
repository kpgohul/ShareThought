package com.thought_store.util;

import com.thought_store.entity.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
public class MessageUtil {

    public void sortMessagesBySentAtAsc(List<Message> messages) {
        messages.sort(Comparator.comparing(Message::getSentAt, Comparator.nullsLast(LocalDateTime::compareTo)));
    }
}
