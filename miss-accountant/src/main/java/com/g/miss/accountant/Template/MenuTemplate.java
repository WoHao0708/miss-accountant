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

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button.ButtonHeight;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import java.net.URI;

import static java.util.Arrays.asList;

public class MenuTemplate {
    public FlexMessage get(String publicFund, String groupId) {
        final Bubble account = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createAccountMenu(groupId))
                .build();
        final Bubble group = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createGroupMenu())
                .build();
        final Bubble other = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createOtherMenu(publicFund))
                .build();
        Carousel carousel = Carousel.builder()
                .contents(asList(account, group, other))
                .build();
        return new FlexMessage("<3", carousel);
    }

    private Box createAccountMenu(String groupId) {
        URI debtUri = null;
        URI debtRecordUri = null;
        try {
            debtUri = new URI("https://liff.line.me/1655817867-5ylLjNv4?groupId=" + groupId);
            debtRecordUri = new URI("https://liff.line.me/1655817867-XmmoMNpY?groupId=" + groupId);
        } catch (Exception ignored) {
        }

        final Button callAction = Button.builder()
                .style(ButtonStyle.PRIMARY)
                .height(ButtonHeight.SMALL)
                .action(new URIAction("記帳", debtUri, null))
                .build();
        final Button websiteAction = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
                .action(new URIAction("紀錄", debtRecordUri, null))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .contents(asList(callAction, websiteAction))
                .build();
    }

    private Box createGroupMenu() {

        final Button resultAction = Button.builder()
                .style(ButtonStyle.PRIMARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("群組結算", "debtCheck"))
                .build();
        final Button allotAction = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("群組分帳", "debtAllot"))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .contents(asList(resultAction, allotAction))
                .build();
    }

    private Box createOtherMenu(String publicFund) {
        final Text text = Text.builder()
                .text(publicFund)
                .align(FlexAlign.CENTER)
                .size(FlexFontSize.Md)
                .weight(Text.TextWeight.BOLD)
                .build();
        final Box box = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .height("44px")
                .paddingTop("10px")
                .contents(text)
                .build();
        final Button button2 = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("帳號啟用", "setAccount"))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .contents(asList(box, button2))
                .build();
    }
}
