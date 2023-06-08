/*
 * Copyright 2018 LINE Corporation
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

package com.g.miss.accountant.Template;

import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.entity.Account;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AccountTemplate {

    public FlexMessage get(List<Account> accountList) {
        final Bubble receipt = Bubble.builder()
                .body(createBody(accountList))
                .footer(createButton())
                .size(Bubble.BubbleSize.KILO)
                .build();
        return new FlexMessage("<3", receipt);
    }

    private Box createBody(List<Account> accountList) {
        final Text title = Text.builder()
                .text("群組結算")
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.LG)
                .color(Constants.COLOR_GREEN)
                .build();
        final Separator separator = Separator.builder().build();
        final Box receipt = createReceiptBox(accountList);

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingBottom(FlexPaddingSize.LG)
                .contents(asList(title, separator, receipt))
                .build();
    }

    private Box createReceiptBox(List<Account> accountList) {
        List<FlexComponent> list = new ArrayList<>();

        for (Account account : accountList) {
            final Text name = Text.builder()
                    .text(account.getName())
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .build();
            final Text amount = Text.builder()
                    .text("$" + account.getAmount())
                    .size(FlexFontSize.SM)
                    .weight(Text.TextWeight.BOLD)
                    .color(Constants.COLOR_BLACK)
                    .align(FlexAlign.END)
                    .build();
            final Box box = Box.builder()
                    .layout(FlexLayout.HORIZONTAL)
                    .contents(asList(name, amount))
                    .build();
            list.add(box);
        }

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .margin(FlexMarginSize.XXL)
                .contents(list)
                .build();
    }

    private Box createButton() {
        final Button callAction = Button.builder()
                .style(Button.ButtonStyle.PRIMARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new PostbackAction("重新結算", "debtCheck"))
                .build();
        final Button websiteAction = Button.builder()
                .style(Button.ButtonStyle.SECONDARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new PostbackAction("重置", "debtReset"))
                .build();
        return Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .height("45px")
                .contents(asList(callAction, websiteAction))
                .build();
    }
}
