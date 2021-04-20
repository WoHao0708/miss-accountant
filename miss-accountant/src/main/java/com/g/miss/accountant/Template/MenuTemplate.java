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
import org.apache.tomcat.websocket.server.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public class MenuTemplate {
    public FlexMessage get(String publicFund)  {
        final Bubble account = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createAccountMenu())
                .build();
        final Bubble advance = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createAdvanceMenu())
                .build();
        final Bubble other = Bubble.builder()
                .size(Bubble.BubbleSize.NANO)
                .body(createOtherMenu(publicFund))
                .build();
        Carousel carousel = Carousel.builder()
                .contents(asList(account, advance, other))
                .build();
        return new FlexMessage("<3", carousel);
    }

    private Box createAccountMenu() {
        final Button callAction = Button.builder()
                .style(ButtonStyle.PRIMARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("記帳檢查", "accountCheck"))
                .build();
        final Button websiteAction = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("記帳紀錄", "accountRecord"))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .contents(asList(callAction, websiteAction))
                .build();
    }

    private Box createAdvanceMenu() {
        final Button callAction = Button.builder()
                .style(ButtonStyle.PRIMARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("分帳檢查", "advanceCheck"))
                .build();
        final Button websiteAction = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
                .action(new PostbackAction("分帳開關", "advanceSwitch"))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .spacing(FlexMarginSize.SM)
                .contents(asList(callAction, websiteAction))
                .build();
    }

    private Box createOtherMenu(String publicFund) {
        URI uri = null;
        try {
            uri = new URI("https://liff.line.me/1655817867-5ylLjNv4");
        } catch (Exception ignored){

        }

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
        final Button button = Button.builder()
                .style(ButtonStyle.SECONDARY)
                .height(ButtonHeight.SMALL)
//                .action(new PostbackAction("說明", "help"))
                .action(new URIAction("link", uri, null))
                .build();
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("3px")
                .contents(asList(box, button))
                .build();
    }
}
