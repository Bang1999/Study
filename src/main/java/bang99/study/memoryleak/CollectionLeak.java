package bang99.study.memoryleak;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionLeak {

    // 메모리에 계속 남아있도록 정적 컬렉션 선언
    private static final List<byte[]> leakyList = new ArrayList<>();
    private static final Map<String, byte[]> leakyMap = new HashMap<>();
    private static final Set<byte[]> leakySet = new HashSet<>();
    private static final Queue<byte[]> leakyQueue = new LinkedList<>();

    /**
     * 큰 객체를 컬렉션에 추가하고 제거하지 않음으로써 메모리 누수 유발
     */
    public void createLeak(int count) {
        for (int i = 0; i < count; i++) {
            // 1MB 바이트 배열 생성
            byte[] largeObject = new byte[1024 * 1024];

            // 컬렉션에 추가
            leakyList.add(largeObject);
            leakyMap.put("key-" + System.nanoTime(), largeObject);
            leakySet.add(largeObject);
            leakyQueue.add(largeObject);
        }
    }
}
