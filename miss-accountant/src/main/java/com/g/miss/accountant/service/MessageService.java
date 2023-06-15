package com.g.miss.accountant.service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;

/**
 * @author G
 * @description 訊息處理
 * @date 2023/6/14 5:26 PM
 */
public interface MessageService {

    void handleTextContent(MessageEvent<TextMessageContent> event);

    void handlePostBack(PostbackEvent event);
}
