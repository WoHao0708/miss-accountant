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

import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.service.AccountService;
import com.g.miss.accountant.service.RecordService;
import com.g.miss.accountant.util.StringUtils;
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
public class AccountantController {
    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Autowired
    private AccountService accountInfoService;
    @Autowired
    private RecordService recordService;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    @EventMapping
    public void handleOtherEvent(Event event) {
        log.info("Received message(Ignored): {}", event);
    }


    private void handleTextContent(String replyToken, Event event, TextMessageContent content) throws Exception {
        final String text = content.getText();
        String prefix = "";
        String infix = "";
        String suffix = "";
        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text, content.getEmojis());

        if (text.length() >= 2) {
            prefix = text.substring(0, 1);
            infix = text.substring(1, 2);
            if (text.length() >= 3) suffix = text.substring(2);
        } else
            return;

        if (!(event.getSource() instanceof GroupSource)) return;

        if ("#".equals(prefix)) switchCommand(replyToken, event, infix, suffix);

        if ("$".equals(prefix)) switchAccount(replyToken, event, infix, suffix);

        if ("/".equals(prefix)) switchAdvance(replyToken, event, infix, suffix);
    }

    private void switchCommand(String replyToken, Event event, String infix, String suffix) {
        final String userId = event.getSource().getUserId();
        final String groupId = ((GroupSource) event.getSource()).getGroupId();
        switch (infix) {
            case "p":
                if (userId != null) {
                    lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                        if (throwable != null) {
                            this.replyText(replyToken, throwable.getMessage());
                            return;
                        }

                        String msg = "GroupId:" + ((GroupSource) event.getSource()).getGroupId() + "\n" +
                                "UserId:" + userId + "\n" + "Display name: " + profile.getDisplayName();

                        this.replyText(replyToken, msg);
                    });
                } else
                    this.replyText(replyToken, "Bot can't use profile API without user ID");
                break;
            case "h":
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    this.replyText(replyToken, Constants.helpMessage);
                });
                break;
            case "1":
                if ("23".equals(suffix))
                    lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                        this.replyText(replyToken, Constants.helloMessage);
                    });
                break;


        }
    }

    private void switchAccount(String replyToken, Event event, String infix, String suffixStr) {

        final String userId = event.getSource().getUserId();
        final String groupId = ((GroupSource) event.getSource()).getGroupId();
        final int suffix;
        if (StringUtils.isEmpty(suffixStr) || !StringUtils.isNumeric(suffixStr)) suffix = 0;
        else suffix = Integer.parseInt(suffixStr);

        switch (infix) {
            case "+": // +
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    int result = accountInfoService.AddOrUpdateAmount(userId, groupId, name, suffix);
                    this.replyText(replyToken, name + ": " + result);
                });
                break;
            case "-": // -
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    int result = accountInfoService.AddOrUpdateAmount(userId, groupId, name, -suffix);
                    this.replyText(replyToken, name + ": " + result);
                });
                break;
            case "$": // $ Set amount = 0.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    accountInfoService.setAmountToZero(userId, groupId, name);
                    this.replyText(replyToken, name + ": 0");
                });
                break;
            case "c": // c Check group amount.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String result = accountInfoService.checkGroupAmount(groupId);
                    this.replyText(replyToken, result);
                });
                break;
            case "r": // r Get record.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String result = recordService.getRecordBy(userId, groupId, suffix);
                    this.replyText(replyToken, result);
                });
                break;
        }
    }

    private void switchAdvance(String replyToken, Event event, String infix, String suffixStr) {

        final String userId = event.getSource().getUserId();
        final String groupId = ((GroupSource) event.getSource()).getGroupId();
        final int suffix;
        if (StringUtils.isEmpty(suffixStr) || !StringUtils.isNumeric(suffixStr)) suffix = 0;
        else suffix = Integer.parseInt(suffixStr);

        switch (infix) {
            case "$": // $ + advance
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    int result = accountInfoService.AddOrUpdateAdvance(userId, groupId, name, suffix);
                    this.replyText(replyToken, name + "預支: " + result);
                });
                break;
            case "/": // / Set advance = 0.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    accountInfoService.setAdvanceToZero(userId, groupId, name);
                    this.replyText(replyToken, name + "預支: 0");
                });
                break;
            case "c": // c Check group advance.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String result = accountInfoService.checkGroupAdvance(groupId);
                    this.replyText(replyToken, result);
                });
                break;
            case "s": // r Set group user own isadvance.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    String result = accountInfoService.setIsAdvance(userId, groupId, name, suffix);
                    this.replyText(replyToken, result);
                });
                break;
            case "a": // r Set group all user isadvance.
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String result = accountInfoService.setAllUserIsAdvance(groupId, suffix);
                    this.replyText(replyToken, result);
                });
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
