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

import com.g.miss.accountant.bean.Record;
import com.g.miss.accountant.constants.Constants;
import com.g.miss.accountant.util.DateUtils;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class AdvanceTemplate {

    public FlexMessage get(List<Record> recordList, String name) {
        final Bubble receipt = Bubble.builder()
                .body(createBody(recordList, name))
                .size(Bubble.BubbleSize.KILO)
                .build();
        return new FlexMessage("<3", receipt);
    }


    private Box createBody(List<Record> recordList, String name) {
        final Text title = Text.builder()
                .text(name + "的記帳紀錄")
                .weight(Text.TextWeight.BOLD)
                .size(FlexFontSize.SM)
                .color(Constants.COLOR_GREEN)
                .build();
        final Separator separator = Separator.builder().build();
        final Box receipt = createReceiptBox(recordList);

        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .paddingAll("15px")
                .contents(asList(title, separator, receipt))
                .build();
    }

    private Box createReceiptBox(List<Record> recordList) {
        List<FlexComponent> list = new ArrayList<>();

        for (Record record : recordList) {
            String amount;
            if (record.getAmount() < 0) amount = "" + record.getAmount();
            else if (record.getAmount() == 0) amount = "=0";
            else amount = "+" + record.getAmount();

            final Text name = Text.builder()
                    .text(DateUtils.format(record.getCreatedTime(), DateUtils.DATE_FORMAT_MM_DD_HH_MM))
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .build();
            final Text amountText = Text.builder()
                    .text(amount)
                    .size(FlexFontSize.SM)
                    .color(Constants.COLOR_BLACK)
                    .align(FlexAlign.END)
                    .build();
            final Box box = Box.builder()
                    .layout(FlexLayout.HORIZONTAL)
                    .contents(asList(name, amountText))
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
}
