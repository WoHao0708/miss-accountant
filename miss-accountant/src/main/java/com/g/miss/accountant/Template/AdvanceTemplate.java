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
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AdvanceTemplate {

    public FlexMessage get(List<AccountInfo> accountInfoList, Integer number) {
        final Bubble receipt = Bubble.builder()
                .body(createBody(accountInfoList, number))
                .footer(createButton())
                .size(Bubble.BubbleSize.KILO)
                .build();
        return new FlexMessage("<3", receipt);
    }


    private Box createBody(List<AccountInfo> accountInfoList, Integer number) {
        final Text title = Text.builder()
                .text("分帳檢查結果")
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_GREEN)
                .build();
        final Separator separator = Separator.builder().build();
        final Box receipt = createReceiptBox(accountInfoList, number);

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("15px")
                .contents(asList(title, separator, receipt))
                .build();
    }

    private Box createButton() {
        final Button callAction = Button.builder()
                .style(Button.ButtonStyle.PRIMARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new PostbackAction("分帳檢查", "advanceCheck"))
                .build();
        final Button websiteAction = Button.builder()
                .style(Button.ButtonStyle.SECONDARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new PostbackAction("重置", "advanceReset"))
                .build();
        return Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .height("40px")
                .contents(asList(callAction, websiteAction))
                .build();
    }

    private Box createReceiptBox(List<AccountInfo> accountInfoList, Integer number) {
        List<FlexComponent> list = new ArrayList<>();
        int total = 0;
        int money = 0;

        for (AccountInfo account : accountInfoList) {
            final Text name = Text.builder()
                    .text(account.getName() + "預支")
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .build();
            final Text amount = Text.builder()
                    .text("$" + account.getAdvance())
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .align(FlexAlign.END)
                    .build();
            final Box box = Box.builder()
                    .layout(FlexLayout.HORIZONTAL)
                    .contents(asList(name, amount))
                    .build();
            total += account.getAdvance();
            list.add(box);
        }

        money = total / number;

        final Separator separator = Separator.builder().build();
        final Separator separatorWhite = Separator.builder().margin(FlexMarginSize.XXL).color(Constants.COLOR_WHITE).build();

        final Text title1 = Text.builder()
                .text("總支出")
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .build();
        final Text text1 = Text.builder()
                .text("$" + total)
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .align(FlexAlign.END)
                .build();
        final Box box1 = Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .margin(FlexMarginSize.XXL)
                .contents(asList(title1, text1))
                .build();

        final Text title2 = Text.builder()
                .text("人數")
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .build();
        final Text text2 = Text.builder()
                .text("" + number)
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .align(FlexAlign.END)
                .build();
        final Box box2 = Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .contents(asList(title2, text2))
                .build();

        final Text title3 = Text.builder()
                .text("結果")
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .build();
        final Text text3 = Text.builder()
                .text("$" + money + "/人")
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_BLACK)
                .align(FlexAlign.END)
                .build();
        final Box box3 = Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .contents(asList(title3, text3))
                .build();

        list.add(separator);
        list.add(box1);
        list.add(box2);
        list.add(box3);
        list.add(separator);
        list.add(separatorWhite);

        for (AccountInfo account : accountInfoList) {
            final Text name = Text.builder()
                    .text(account.getName())
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .build();
            final Text amount = Text.builder()
                    .text("$" + (account.getAdvance() - money))
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

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .margin(FlexMarginSize.XXL)
                .contents(list)
                .build();
    }
}
