package com.learn.other;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 146 - LRU Least Recently Used Cache
 * 
 * Medium
 * 
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * Implement the LRUCache class:
 *     LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 *     int get(int key) Return the value of the key if the key exists, otherwise return -1.
 *     void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache.
 *     If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * 
 * The functions get and put must each run in "O(1)" average time complexity.
 * 
 * Example 1:
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 * 
 * Constraints:
 *     1 <= capacity <= 3000
 *     0 <= key <= 104
 *     0 <= value <= 105
 *     At most 2 * 105 calls will be made to get and put.
 */
@Log4j2
public class LeastRecentUsedCache {

    public static void main(String[] args) {

        /*
        ["LRUCache","put","put","get","put","get","put","get","get","get"]
        [[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
        Output
        [null, null, null, 1, null, -1, null, -1, 3, 4]
        */
        //LeastRecentUsedCache leastRecentUsedCache = new LeastRecentUsedCache();
        LRUCacheLukeLinkedHashMap cache = new LRUCacheLukeLinkedHashMap(2);

        int val = cache.get(2);
        Assertions.assertEquals(-1, val);

        cache.put(1, 1);

        cache.put(2, 2);

        val = cache.get(1);
        Assertions.assertEquals(1, val);

        cache.put(3, 3);

        val = cache.get(2);
        Assertions.assertEquals(-1, val);

        cache.put(4, 4);
        val = cache.get(1);
        Assertions.assertEquals(-1, val);

        val = cache.get(3);
        Assertions.assertEquals(3, val);
        val = cache.get(4);
        Assertions.assertEquals(4, val);

        log.debug("val: {}", cache.get(1));

        /**
         * ["LRUCache","put","put","get","put","put","get"]
         * [[2],[2,1],[2,2],[2],[1,1],[4,1],[-1]]
         */
        // LRUCacheLukeDoubleLinkedList cache = new LRUCacheLukeDoubleLinkedList(2);
        /*
        LRUCacheLukeLinkedHashMap cache = new LRUCacheLukeLinkedHashMap(2);
        cache.put(2, 1);
        cache.put(2, 2);
        
        int val = cache.get(2);
        Assertions.assertEquals(2, val);
        
        cache.put(1, 1);
        cache.put(4, 1);
        
        val = cache.get(2);
        Assertions.assertEquals(-1, val);
        */

        log.debug(() -> "Test complete.");
    }
}


/**
 * Luke - LinkedHashMap
 * 
 * N.B. "map = new LinkedHashMap<>(capacity, 0.75F, true)":
 * (1) "F" is required after 0.75
 * (2) "true" is required so that "get()" is also counted for refresh access list
 * (3) "map.getOrDefault(key, -1);" - get familiar with this new method
 * 
 * Runtime: 80 ms, faster than 69.16% of Java online submissions for LRU Cache.
 * Memory Usage: 124.7 MB, less than 67.37% of Java online submissions for LRU Cache.
 * 
 * Time: O(1)
 * Space: O(capacity)
 */
class LRUCacheLukeLinkedHashMap {

    private final Map<Integer, Integer> map;

    public LRUCacheLukeLinkedHashMap(int capacity) {
        map = new LinkedHashMap<>(capacity, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return map.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        map.put(key, value);
    }

}


/**
 * Luke
 * 
 * Time Limit Exceeded
 * 
 * Runtime: 103 ms, faster than 41.33% of Java online submissions for LRU Cache.
 * Memory Usage: 123.8 MB, less than 82.88% of Java online submissions for LRU Cache.
 * 
 * Time: O(1)
 * Space: O(capacity)
 */
class LRUCacheLukeDoubleLinkedList {

    private int capacity = 0;

    private int size = 0;

    final private Map<Integer, DLinkedNode> map;

    public LRUCacheLukeDoubleLinkedList(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);

        head = new DLinkedNode();
        tail = new DLinkedNode();

        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DLinkedNode node = map.get(key);

        if (node == null) {
            return -1;
        } else {
            /**
             * Move to tail if it is not at the end
             */
            if (node.next != tail) {
                removeNode(node);
                addToEnd(node);
            }

            return node.value;
        }
    }

    public void put(int key, int value) {
        DLinkedNode node = map.get(key);
        if (node == null) {
            /**
             * Add to tail
             */
            node = new DLinkedNode();
            node.key = key;
            node.value = value;
            /**
             * Add to end
             */
            addToEnd(node);

            map.put(key, node);

            size++;

            /**
             * Adjust size
             */
            if (size > capacity) {
                map.remove(head.next.key);
                removeNode(head.next);
                size--;
            }
        } else {
            /**
             * Move to tail if it is not at the end
             */
            if (node.next != tail) {
                removeNode(node);
                addToEnd(node);
            }

            /**
             * Update value
             */
            node.value = value;
        }
    }

    DLinkedNode head;
    DLinkedNode tail;

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode pre;
        DLinkedNode next;
    }

    void addToEnd(DLinkedNode node) {
        tail.pre.next = node;
        node.pre = tail.pre;
        node.next = tail;
        tail.pre = node;
    }

    void removeNode(DLinkedNode node) {
        node.next.pre = node.pre;
        node.pre.next = node.next;
    }
}


/**
 * LC - "Double Linked List + Map"
 */
class LRUCacheLc {

    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
    }

    private void addNode(DLinkedNode node) {
        /**
         * Always add the new node right after head.
         */
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        /**
         * Remove an existing node from the linked list.
         */
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;

        prev.next = next;
        next.prev = prev;
    }

    private void moveToHead(DLinkedNode node) {
        /**
         * Move certain node in between to the head.
         */
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        /**
         * Pop the current tail.
         */
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    private Map<Integer, DLinkedNode> map = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkedNode head;
    private DLinkedNode tail;

    public LRUCacheLc(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        // head.prev = null;

        tail = new DLinkedNode();
        // tail.next = null;

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = map.get(key);
        if (node == null)
            return -1;

        // move the accessed node to the head;
        moveToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = map.get(key);

        if (node == null) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            map.put(key, newNode);
            addNode(newNode);

            ++size;

            if (size > capacity) {
                // pop the tail
                DLinkedNode tail = popTail();
                map.remove(tail.key);
                --size;
            }
        } else {
            // update the value.
            node.value = value;
            moveToHead(node);
        }
    }
}


/**
 * LC - LinkedHashMap
 */
class LRUCacheLcLinkedHashMap
    extends LinkedHashMap<Integer, Integer> {

    private int capacity;

    public LRUCacheLcLinkedHashMap(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
