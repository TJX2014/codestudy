package lockfree;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class LockFreeLinkedList<E> {

    private static class Node<E> {
        E element;
        volatile Node<E> next;
        Node(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    private AtomicStampedReference<Node<E>> headRef = null;

    public LockFreeLinkedList() {
        this.headRef = new AtomicStampedReference<>(null, 0);
    }

    public boolean isEmpty() {
        return this.headRef.getReference() == null;
    }

    public void clear() {
        this.headRef.set(null, headRef.getStamp() + 1);
    }

    public E peekFirst() {
        return isEmpty() ? null: this.headRef.getReference().element;
    }

    public long count() {
        long count = 0;
        Node<E> currentNode = this.headRef.getReference();
        while(currentNode != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    public void add(E element) {
        if (null == element) {
            throw new NullPointerException("The element is null");
        }
        Node<E> previousNode;
        int previousStamp;
        Node<E> newNode;
        do {
            previousNode = this.headRef.getReference();
            previousStamp = this.headRef.getStamp();
            newNode = new Node<>(element);
            newNode.next = previousNode;
        } while (
            !this.headRef.compareAndSet(
                previousNode, newNode, previousStamp, previousStamp+1));
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<E> currentNode;
        int currentStamp;
        Node<E> nextNode;
        do {
            currentNode = this.headRef.getReference();
            currentStamp = this.headRef.getStamp();
            if (currentNode == null) {
                break;
            }
            nextNode = currentNode.next;
        } while (!this.headRef.compareAndSet(currentNode, nextNode, currentStamp, currentStamp+1));
        return currentNode == null ? null: currentNode.element;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int iteration=0; iteration<100; iteration++) {
            LockFreeLinkedList<Integer> list = new LockFreeLinkedList<>();
            final ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>();
            final AtomicInteger factory = new AtomicInteger();
            final AtomicInteger deleteCount = new AtomicInteger();

            final CountDownLatch latch = new CountDownLatch(10);
            final int MAX_CAPACITY = 1_000_000;

            for (int i=0; i< 10; i++) {
                new Thread(() -> {
                    while(true) {
                        int data = factory.getAndIncrement();
                        if (data < MAX_CAPACITY) {
                            list.add(data);
                            if (data%2 ==0) {
                                list.removeFirst();
                                deleteCount.getAndIncrement();
                            }
                        } else {
                            break;
                        }
                    }
                    latch.countDown();
                }).start();
            }
            latch.await();

            assert list.count() == (MAX_CAPACITY - deleteCount.get());

            while(!list.isEmpty()) {
                set.add(list.removeFirst());
            }

            assert list.count() == (MAX_CAPACITY - deleteCount.get());

            System.out.printf("The iteration %d passes testing\n", iteration+1);
        }
    }
}
