/**
 *
 */
package com.bbieat.screen.util;

import org.apache.commons.lang.StringUtils;

/**
 * 0-15分钟<br>
 * 1-小时<br>
 * 2-日<br>
 * 3-月<br>
 * 4-周<br>
 * 5-旬<br>
 * 6-季
 *
 * @author armording
 */
public enum CircleTypeEnum {

	MINUTE(0), HOUR(1), DAY(2), MONTH(3), WEEK(6), TENDAYS(4), QUARTER(5);

    private final int value;

    CircleTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Short getShortValue() {
        Integer obj = value;
        return obj.shortValue();
    }

    /**
     * 通过传入的字符串匹配枚举，传入值
     *
     * @param value
     * @return
     */
    public static CircleTypeEnum getEnmuByValue(int value) {
        for (CircleTypeEnum lowCommType : CircleTypeEnum.values()) {
            if (value == lowCommType.getValue()) {
                return lowCommType;
            }
        }
        return null;
    }

    /**
     * 通过传入的字符串匹配枚举,传入名字
     *
     * @param name
     * @return
     */
    public static CircleTypeEnum getEnmuByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (CircleTypeEnum lowCommType : CircleTypeEnum.values()) {
            if (StringUtils.equals(name, lowCommType.toString())) {
                return lowCommType;
            }
        }
        return null;
    }
}
