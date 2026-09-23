package com.owlbuy.owlbuy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateSn {
    public static String generateSn(Integer memberId) {
        // 1. 年月日時分秒 (14碼)
        String dateTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 2. 會員 ID 補齊至 6 位 (例如 memberId=123 -> "000123")
        String memberIdStr = String.format("%06d", memberId % 1000000);

        // 3. 2 位隨機數 (10 ~ 99)
        int randomNum = ThreadLocalRandom.current().nextInt(10, 100);

        return dateTimeStr + memberIdStr + randomNum;
    }
}
