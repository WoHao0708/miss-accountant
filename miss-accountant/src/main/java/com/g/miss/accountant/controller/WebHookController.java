/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.g.miss.accountant.controller;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.g.miss.accountant.Template.MenuTemplate;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.service.AccountService;
import com.g.miss.accountant.service.DebtService;
import com.g.miss.accountant.service.PublicFundService;
import com.g.miss.accountant.util.StringUtils;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.FlexMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
public class WebHookController {
    @Autowired
    private LineMessagingClient lineMessagingClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PublicFundService publicFundService;
    @Autowired
    private DebtService debtService;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        log.info("Got text message from replyToken:{}: text:{} emojis:{}", event.getReplyToken(), event.getMessage().getText(), event.getMessage().getEmojis());
        handleTextContent(event);
    }

    @EventMapping
    public void handlePostBackEvent(PostbackEvent event) {
        handlePostBack(event);
    }

    @EventMapping
    public void handleOtherEvent(Event event) {
        log.info("Received message(Ignored): {}", event);
    }

    private void handlePostBack(PostbackEvent event) {
        final String userId = event.getSource().getUserId();
        final String groupId = ((GroupSource) event.getSource()).getGroupId();
        String action = event.getPostbackContent().getData();
        FlexMessage flexMessage;

        switch (action) {
            case "help":
                this.replyText(event.getReplyToken(), Constants.HELP_MESSAGE);
                break;
            case "setAccount":
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    accountService.setAccount(groupId, userId, name);
                    this.replyText(event.getReplyToken(), name + Constants.SET_ACCOUNT_SUCCESS);
                });
                break;
            case "debtReset":
                this.replyText(event.getReplyToken(), debtService.deleteGroupDebt(groupId));
                break;
            case "debtCheck":
                flexMessage = accountService.checkGroupAmount(groupId);
                if (flexMessage == null) this.replyText(event.getReplyToken(), Constants.NONE_DATA_MESSAGE);
                else this.reply(event.getReplyToken(), flexMessage);
                break;
            case "debtAllot":
                flexMessage = accountService.allotMoney(groupId);
                if (flexMessage == null) this.replyText(event.getReplyToken(), Constants.NONE_DATA_MESSAGE);
                else this.reply(event.getReplyToken(), flexMessage);
                break;
        }
    }

    private void handleTextContent(MessageEvent<TextMessageContent> event) {
        String prefix = "";
        String infix = "";
        String suffix = null;
        String text = event.getMessage().getText().trim();
        Integer suffixInt = null;


        if (text.length() >= 2) {
            prefix = text.substring(0, 1);
            infix = text.substring(1, 2);
            if (text.length() >= 3) suffix = text.substring(2);
            if (suffix != null && StringUtils.isNumeric(suffix)) suffixInt = Integer.parseInt(suffix);
        } else
            return;

        if (!(event.getSource() instanceof GroupSource)) return;
        if (suffixInt != null) {
            if ("$".equals(prefix)) switchPublicFund(event, infix, suffixInt);
        }

        if ("會計小姐".equals(text) || "會計".equals(text) || "阿君".equals(text)) {
            final String groupId = ((GroupSource) event.getSource()).getGroupId();
            this.reply(event.getReplyToken(), new MenuTemplate().get(publicFundService.addOrUpdatePublicFund(groupId, 0), groupId));
        }

        if ("婆".equals(text) || "老婆".equals(text))
            this.replyText(event.getReplyToken(), "噁男");

    }

    private void switchPublicFund(MessageEvent<TextMessageContent> event, String infix, Integer suffix) {
        final String groupId = ((GroupSource) event.getSource()).getGroupId();

        switch (infix) {
            case "+": // +
                this.replyText(event.getReplyToken(), publicFundService.addOrUpdatePublicFund(groupId, suffix));
                break;
            case "-": // -
                this.replyText(event.getReplyToken(), publicFundService.addOrUpdatePublicFund(groupId, -suffix));
                break;
        }
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        reply(replyToken, messages, false);
    }

    private void reply(@NonNull String replyToken,
                       @NonNull List<Message> messages,
                       boolean notificationDisabled) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }
}
