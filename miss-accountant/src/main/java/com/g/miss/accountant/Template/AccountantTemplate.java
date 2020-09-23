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

import com.g.miss.accountant.bean.AccountInfo;
import com.g.miss.accountant.constants.Constants;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class AccountantTemplate {

    public FlexMessage get(List<AccountInfo> accountInfoList) {
        final Bubble receipt = Bubble.builder()
                .body(createBody(accountInfoList))
                .size(Bubble.BubbleSize.KILO)
                .build();
        return new FlexMessage("<3", receipt);
    }


    private Box createBody(List<AccountInfo> accountInfoList) {
        final Text title = Text.builder()
                .text("記帳檢查結果")
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_GREEN)
                .build();
        final Separator separator = Separator.builder().build();
        final Box receipt = createReceiptBox(accountInfoList);

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("15px")
                .contents(asList(title, separator, receipt))
                .build();
    }

    private Box createReceiptBox(List<AccountInfo> accountInfoList) {
        List<FlexComponent> list = new ArrayList<>();
        int total = 0;


        for (AccountInfo account : accountInfoList) {
            final Text name = Text.builder()
                    .text(account.getName())
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .build();
            final Text amount = Text.builder()
                    .text(String.valueOf("$" + account.getAmount()))
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .align(FlexAlign.END)
                    .build();
            final Box box = Box.builder()
                    .layout(FlexLayout.HORIZONTAL)
                    .contents(asList(name, amount))
                    .build();
            total += account.getAmount();
            list.add(box);
        }

        final Separator separator = Separator.builder().build();

        final Text title = Text.builder()
                .text("總計")
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .build();
        final Text text = Text.builder()
                .text("$" + Integer.toString(total))
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .align(FlexAlign.END)
                .build();
        final Box box = Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .margin(FlexMarginSize.XXL)
                .contents(asList(title, text))
                .build();
        list.add(separator);
        list.add(box);

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .margin(FlexMarginSize.XXL)
                .contents(list)
                .build();
    }
}
