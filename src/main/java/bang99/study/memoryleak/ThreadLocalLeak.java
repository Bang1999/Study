package bang99.study.memoryleak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalLeak {

    private final ThreadLocal<byte[]> leakyThreadLocal = new ThreadLocal<>();

    /**
     * 스레드 풀의 스레드에서 ThreadLocal 값을 설정하고 제거하지 않음으로써 메모리 누수를 유발
     */
    public void createLeak(int count) {
        // 고정 스레드 풀 생성
        int threadPoolSize = Math.min(10, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        // 스레드 풀에 작업 제출
        for (int i = 0; i < count; i++) {
            executor.submit(() -> {
                // ThreadLocal에 새로운 큰 객체 설정
                // 이것은 이 스레드의 기존 값을 대체함
                leakyThreadLocal.set(new byte[1024 * 1024]);

                // 약간의 작업 수행
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 올바른 방법 사용 후 -> remove()
                // leakyThreadLocal.remove();
                return null;
            });
        }

        // 실행자를 종료하고 모든 작업이 완료될 때까지 대기
        /*
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        */
    }
}
