package bang99.study.memoryleak;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memory-leak")
public class MemoryLeakController {

    private final CollectionLeak collectionLeak;
    private final ListenerLeak listenerLeak;
    private final ThreadLocalLeak threadLocalLeak;

    public MemoryLeakController() {
        this.collectionLeak = new CollectionLeak();
        this.listenerLeak = new ListenerLeak();
        this.threadLocalLeak = new ThreadLocalLeak();
    }

    /**
     * 객체를 컬렉션에 추가하고 제거하지 않음으로써 메모리 누수 유발
     */
    @GetMapping("/collection")
    public ResponseEntity<String> triggerCollectionLeak() {
        int count = 1000;
        collectionLeak.createLeak(count);
        return ResponseEntity.ok("Collection leak 유발하였습니다.");
    }

    /**
     * 리스너를 등록하고 해제하지 않음으로써 메모리 누수 유발
     */
    @GetMapping("/listener")
    public ResponseEntity<String> triggerListenerLeak() {
        int count = 1000;
        listenerLeak.createLeak(count);
        return ResponseEntity.ok("Listener leak 유발하였습니다.");
    }

    /**
     * ThreadLocal 값을 설정하고 제거하지 않음으로써 메모리 누수 유발
     */
    @GetMapping("/threadlocal")
    public ResponseEntity<String> triggerThreadLocalLeak() {
        int count = 1000;
        threadLocalLeak.createLeak(count);
        return ResponseEntity.ok("ThreadLocal leak 유발하였습니다.");
    }
}
