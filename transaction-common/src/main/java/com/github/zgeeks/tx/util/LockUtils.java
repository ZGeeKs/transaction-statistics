package com.github.zgeeks.tx.util;

import java.util.concurrent.locks.Lock;

public final class LockUtils {

    public static <T> T runSafely(Lock lock, Callable<T> consumer) {
        lock.lock();
        try {
            return consumer.run();
        } finally {
            lock.unlock();
        }
    }

    public interface Callable<T> {
        T run();
    }

    private LockUtils() {}
}
