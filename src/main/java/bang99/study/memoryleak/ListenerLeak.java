package bang99.study.memoryleak;

import java.util.ArrayList;
import java.util.List;

public class ListenerLeak {

    // 모든 리스너에 대한 참조를 보유하는 이벤트 소스
    private final EventSource eventSource = new EventSource();

    /**
     * 리스너를 등록하고 해제하지 않음으로써 메모리 누수 유발
     */
    public void createLeak(int count) {
        for (int i = 0; i < count; i++) {
            // 새 리스너 생성
            EventListener listener = new EventListener(i);

            // 리스너 등록
            eventSource.addEventListener(listener);
        }
    }

    /**
     * 간단한 이벤트 코드
     */
    private static class EventSource {
        private final List<EventListener> listeners = new ArrayList<>();

        public void addEventListener(EventListener listener) {
            listeners.add(listener);
        }

        public void removeEventListener(EventListener listener) {
            listeners.remove(listener);
        }

        public int getListenerCount() {
            return listeners.size();
        }
    }

    /**
     * 메모리 소비를 만드는 리스너
     */
    private static class EventListener {
        // 각 리스너는 1MB 페이로드를 가짐
        private final byte[] payload = new byte[1024 * 1024];
        private final int id;

        public EventListener(int id) {
            this.id = id;
            // 페이로드를 일부 데이터로 초기화
            for (int i = 0; i < payload.length; i++) {
                payload[i] = (byte) (i % 256);
            }
        }
    }
}
