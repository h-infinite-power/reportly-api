package com.human.infinite.power.reportly.common;

import java.security.SecureRandom;

/**
 * 시계열 기반 고유 ID 생성기
 * 중복을 최소화하면서 시간 순서를 보장하는 ID를 생성합니다.
 */
public class IdGenerator {
    private static final long UTC_EPOCH_2020 = 1577836800000L;
    private static final int SHIFT_BIT = 23;
    private static final int BIT_23_VALUE = 0x800000;

    /**
     * 시계열 기반 고유 ID를 생성합니다.
     * 
     * @return 고유한 Long 타입 ID
     */
    public static Long generateId() {
        // 현재 시간에서 2020년 1월 1일을 뺀 밀리초
        long sub = System.currentTimeMillis() - UTC_EPOCH_2020;
        // 시간을 23비트 왼쪽으로 시프트하고 랜덤값 추가
        return (sub << SHIFT_BIT) + new SecureRandom().nextInt(BIT_23_VALUE);
    }
}
