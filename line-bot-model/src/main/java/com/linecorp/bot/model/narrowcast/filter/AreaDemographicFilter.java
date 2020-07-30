/*
 * Copyright 2020 LINE Corporation
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
 *
 */

package com.linecorp.bot.model.narrowcast.filter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import com.linecorp.bot.model.narrowcast.filter.AreaDemographicFilter.AreaDemographicFilterBuilder;

import lombok.Builder;
import lombok.Value;

/**
 * Send messages to users in the specified region.
 */
@Value
@Builder
@JsonTypeName("area")
@JsonDeserialize(builder = AreaDemographicFilterBuilder.class)
public class AreaDemographicFilter implements DemographicFilter {
    private static final String type = "area";

    List<AreaCode> oneOf;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AreaDemographicFilterBuilder {
        // Filled by lombok
    }

    public enum AreaCode {
        @JsonProperty("jp_01")
        JP_HOKKAIDO("jp_01"),
        @JsonProperty("jp_02")
        JP_AOMORI("jp_02"),
        @JsonProperty("jp_03")
        JP_IWATE("jp_03"),
        @JsonProperty("jp_04")
        JP_MIYAGI("jp_04"),
        @JsonProperty("jp_05")
        JP_AKITA("jp_05"),
        @JsonProperty("jp_06")
        JP_YAMAGATA("jp_06"),
        @JsonProperty("jp_07")
        JP_FUKUSHIMA("jp_07"),
        @JsonProperty("jp_08")
        JP_IBARAKI("jp_08"),
        @JsonProperty("jp_09")
        JP_TOCHIGI("jp_09"),
        @JsonProperty("jp_10")
        JP_GUNMA("jp_10"),
        @JsonProperty("jp_11")
        JP_SAITAMA("jp_11"),
        @JsonProperty("jp_12")
        JP_CHIBA("jp_12"),
        @JsonProperty("jp_13")
        JP_TOKYO("jp_13"),
        @JsonProperty("jp_14")
        JP_KANAGAWA("jp_14"),
        @JsonProperty("jp_15")
        JP_NIIGATA("jp_15"),
        @JsonProperty("jp_16")
        JP_TOYAMA("jp_16"),
        @JsonProperty("jp_17")
        JP_ISHIKAWA("jp_17"),
        @JsonProperty("jp_18")
        JP_FUKUI("jp_18"),
        @JsonProperty("jp_19")
        JP_YAMANASHI("jp_19"),
        @JsonProperty("jp_20")
        JP_NAGANO("jp_20"),
        @JsonProperty("jp_21")
        JP_GIFU("jp_21"),
        @JsonProperty("jp_22")
        JP_SHIZUOKA("jp_22"),
        @JsonProperty("jp_23")
        JP_AICHI("jp_23"),
        @JsonProperty("jp_24")
        JP_MIE("jp_24"),
        @JsonProperty("jp_25")
        JP_SHIGA("jp_25"),
        @JsonProperty("jp_26")
        JP_KYOTO("jp_26"),
        @JsonProperty("jp_27")
        JP_OSAKA("jp_27"),
        @JsonProperty("jp_28")
        JP_HYOUGO("jp_28"),
        @JsonProperty("jp_29")
        JP_NARA("jp_29"),
        @JsonProperty("jp_30")
        JP_WAKAYAMA("jp_30"),
        @JsonProperty("jp_31")
        JP_TOTTORI("jp_31"),
        @JsonProperty("jp_32")
        JP_SHIMANE("jp_32"),
        @JsonProperty("jp_33")
        JP_OKAYAMA("jp_33"),
        @JsonProperty("jp_34")
        JP_HIROSHIMA("jp_34"),
        @JsonProperty("jp_35")
        JP_YAMAGUCHI("jp_35"),
        @JsonProperty("jp_36")
        JP_TOKUSHIMA("jp_36"),
        @JsonProperty("jp_37")
        JP_KAGAWA("jp_37"),
        @JsonProperty("jp_38")
        JP_EHIME("jp_38"),
        @JsonProperty("jp_39")
        JP_KOUCHI("jp_39"),
        @JsonProperty("jp_40")
        JP_FUKUOKA("jp_40"),
        @JsonProperty("jp_41")
        JP_SAGA("jp_41"),
        @JsonProperty("jp_42")
        JP_NAGASAKI("jp_42"),
        @JsonProperty("jp_43")
        JP_KUMAMOTO("jp_43"),
        @JsonProperty("jp_44")
        JP_OITA("jp_44"),
        @JsonProperty("jp_45")
        JP_MIYAZAKI("jp_45"),
        @JsonProperty("jp_46")
        JP_KAGOSHIMA("jp_46"),
        @JsonProperty("jp_47")
        JP_OKINAWA("jp_47"),
        @JsonProperty("tw_01")
        TW_TAIPEI_CITY("tw_01"),
        @JsonProperty("tw_02")
        TW_NEW_TAIPEI_CITY("tw_02"),
        @JsonProperty("tw_03")
        TW_TAOYUAN_CITY("tw_03"),
        @JsonProperty("tw_04")
        TW_TAICHUNG_CITY("tw_04"),
        @JsonProperty("tw_05")
        TW_TAINAN_CITY("tw_05"),
        @JsonProperty("tw_06")
        TW_KAOHSIUNG_CITY("tw_06"),
        @JsonProperty("tw_07")
        TW_KEELUNG_CITY("tw_07"),
        @JsonProperty("tw_08")
        TW_HSINCHU_CITY("tw_08"),
        @JsonProperty("tw_09")
        TW_CHIAYI_CITY("tw_09"),
        @JsonProperty("tw_10")
        TW_HSINCHU_COUNTY("tw_10"),
        @JsonProperty("tw_11")
        TW_MIAOLI_COUNTY("tw_11"),
        @JsonProperty("tw_12")
        TW_CHANGHUA_COUNTY("tw_12"),
        @JsonProperty("tw_13")
        TW_NANTOU_COUNTY("tw_13"),
        @JsonProperty("tw_14")
        TW_YUNLIN_COUNTY("tw_14"),
        @JsonProperty("tw_15")
        TW_CHIAYI_COUNTY("tw_15"),
        @JsonProperty("tw_16")
        TW_PINGTUNG_COUNTY("tw_16"),
        @JsonProperty("tw_17")
        TW_YILAN_COUNTY("tw_17"),
        @JsonProperty("tw_18")
        TW_HUALIEN_COUNTY("tw_18"),
        @JsonProperty("tw_19")
        TW_TAITUNG_COUNTY("tw_19"),
        @JsonProperty("tw_20")
        TW_PENGHU_COUNTY("tw_20"),
        @JsonProperty("tw_21")
        TW_KINMEN_COUNTY("tw_21"),
        @JsonProperty("tw_22")
        TW_LIENCHIANG_COUNTY("tw_22"),
        @JsonProperty("th_01")
        TH_BANGKOK("th_01"),
        @JsonProperty("th_02")
        TH_PATTAYA("th_02"),
        @JsonProperty("th_03")
        TH_NORTHERN("th_03"),
        @JsonProperty("th_04")
        TH_CENTRAL("th_04"),
        @JsonProperty("th_05")
        TH_SOUTHERN("th_05"),
        @JsonProperty("th_06")
        TH_EASTERN("th_06"),
        @JsonProperty("th_07")
        TH_NORTHEASTERN("th_07"),
        @JsonProperty("th_08")
        TH_WESTERN("th_08"),
        @JsonProperty("id_01")
        ID_BALI("id_01"),
        @JsonProperty("id_02")
        ID_BANDUNG("id_02"),
        @JsonProperty("id_03")
        ID_BANJARMASIN("id_03"),
        @JsonProperty("id_04")
        ID_JABODETABEK("id_04"),
        @JsonProperty("id_06")
        ID_MAKASSAR("id_06"),
        @JsonProperty("id_07")
        ID_MEDAN("id_07"),
        @JsonProperty("id_08")
        ID_PALEMBANG("id_08"),
        @JsonProperty("id_09")
        ID_SAMARINDA("id_09"),
        @JsonProperty("id_10")
        ID_SEMARANG("id_10"),
        @JsonProperty("id_11")
        ID_SURABAYA("id_11"),
        @JsonProperty("id_12")
        ID_YOGYAKARTA("id_12"),
        @JsonProperty("id_05")
        ID_LAINNYA("id_05");

        private final String code;

        AreaCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
