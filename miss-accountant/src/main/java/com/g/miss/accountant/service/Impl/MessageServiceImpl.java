package com.g.miss.accountant.service.Impl;

import com.g.miss.accountant.Template.MenuTemplate;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.service.MessageService;
import com.g.miss.accountant.util.StringUtils;
import com.g.miss.accountant.vo.AccountVO;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.singletonList;

/**
 * @author G
 * @description 訊息處理
 * @date 2023/6/14 5:56 PM
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Autowired
    private PublicFundServiceImpl publicFundService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private DebtServiceImpl debtService;

    @Override
    public void handleTextContent(MessageEvent<TextMessageContent> event) {
        String prefix = "";
        String infix = "";
        String suffix = null;
        String text = event.getMessage().getText().trim();
        Integer suffixInt = null;

        if (!(event.getSource() instanceof GroupSource)) return; // 不是群組直接返回

        // 切割出字元
        if (text.length() >= 2) {
            prefix = text.substring(0, 1);
            infix = text.substring(1, 2);
            if (text.length() >= 3) suffix = text.substring(2);
            if (suffix != null && StringUtils.isNumeric(suffix)) suffixInt = Integer.parseInt(suffix);
        } else
            return;

        // 判斷指令
        if (suffixInt != null) {
            if ("$".equals(prefix)) {
                final String groupId = ((GroupSource) event.getSource()).getGroupId();

                switch (infix) {
                    case "+": // +
                        this.replyText(event.getReplyToken(), publicFundService.updateBalance(groupId, suffixInt));
                        break;
                    case "-": // -
                        this.replyText(event.getReplyToken(), publicFundService.updateBalance(groupId, -suffixInt));
                        break;
                }
            }
        }

        if ("會計小姐".equals(text) || "會計".equals(text)) {
            final String groupId = ((GroupSource) event.getSource()).getGroupId();
            this.reply(event.getReplyToken(), new MenuTemplate().get(publicFundService.updateBalance(groupId, 0), groupId));
        }

        if ("我婆".equals(text) || "老婆".equals(text))
            this.replyText(event.getReplyToken(), "噁男");
    }

    @Override
    public void handlePostBack(PostbackEvent event) {
        final String userId = event.getSource().getUserId();
        final String groupId = ((GroupSource) event.getSource()).getGroupId();
        String action = event.getPostbackContent().getData();
        FlexMessage flexMessage;

        switch (action) { // 對應到前端按鈕上的功能
            case "help": // 說明
                this.replyText(event.getReplyToken(), Constants.HELP_MESSAGE);
                break;
            case "setAccount": // 啟用帳號
                lineMessagingClient.getGroupMemberProfile(groupId, userId).whenComplete((profile, throwable) -> {
                    String name = profile.getDisplayName();
                    AccountVO accountVO = new AccountVO(groupId, userId, name);
                    this.replyText(event.getReplyToken(), name + accountService.updateInfo(accountVO));
                });
                break;
            case "debtReset": // 清除賬本
                this.replyText(event.getReplyToken(), debtService.deleteGroupDebt(groupId));
                break;
            case "debtCheck": // 查賬
                flexMessage = accountService.getGroupAmountFlexMessage(groupId);
                if (flexMessage == null) this.replyText(event.getReplyToken(), Constants.NONE_DATA_MESSAGE);
                else this.reply(event.getReplyToken(), flexMessage);
                break;
            case "debtAllot": // 分賬
                flexMessage = accountService.getAllotFlexMessage(groupId);
                if (flexMessage == null) this.replyText(event.getReplyToken(), Constants.NONE_DATA_MESSAGE);
                else this.reply(event.getReplyToken(), flexMessage);
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
