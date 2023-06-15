package com.g.miss.accountant.handler;

import com.g.miss.accountant.service.Impl.MessageServiceImpl;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author G
 * @description 處理訊息hook
 * @date 2023/6/8 12:19 PM
 */
@Slf4j
@LineMessageHandler
public class WebHookHandler {
    @Autowired
    private MessageServiceImpl messageService;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        log.info("Got text message from replyToken:{}: text:{} emojis:{}", event.getReplyToken(), event.getMessage().getText(), event.getMessage().getEmojis());
        messageService.handleTextContent(event);
    }

    @EventMapping
    public void handlePostBackEvent(PostbackEvent event) {
        messageService.handlePostBack(event);
    }

    @EventMapping
    public void handleOtherEvent(Event event) {
        log.info("Received message(Ignored): {}", event);
    }
}
