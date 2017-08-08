package com.springboot.tools;

import java.util.UUID;

/**
 * Created by liuyongg on 1/8/2017.
 */
public class UUIDTool {
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
