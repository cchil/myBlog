package com.cchilei.blog.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author
 * @Create 2018-05-25 17:23
 */
public class MyAtomicInteger {

    private static final class Get {
        private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    }

    public static AtomicInteger getInstance() {
        return Get.ATOMIC_INTEGER;
    }

    public synchronized static void sub() {
        if (Get.ATOMIC_INTEGER.intValue() < 0) {
            return;
        }
        Get.ATOMIC_INTEGER.decrementAndGet();
    }

    public synchronized static void plus() {
        Get.ATOMIC_INTEGER.incrementAndGet();
    }
}
