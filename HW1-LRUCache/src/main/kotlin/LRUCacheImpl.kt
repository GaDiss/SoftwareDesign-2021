import java.lang.IllegalArgumentException

const val DEFAULT_MAX_SIZE = 8

/** Implementation of {@link LRUCache}. */
class LRUCacheImpl<K, V>(private val maxSize: Int = DEFAULT_MAX_SIZE) : LRUCache<K, V> {
    init {
        if (maxSize < 0) throw IllegalArgumentException("maxSize <= 0")
    }

    /** Node for linked dequeue */
    private inner class Node<K, V>(
        val key: K,
        var value: V?,
        var next: Node<K, V>? = null,
        var previous: Node<K, V>? = null
    )

    private val map = HashMap<K, Node<K, V>>()
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    override fun get(key: K): V? {
        return getNode(key)?.value
    }

    override fun put(key: K, value: V): V? {
        assert(map.size <= maxSize)
        val oldSize = map.size

        return getNode(key)?.let {
            val oldValue = it.value
            it.value = value

            assert(map.size == oldSize)
            oldValue
        } ?: run {
            val newNode = Node(key, value)
            map[key] = newNode
            pushFront(newNode)
            if (map.size > maxSize) {
                map.remove(dropTail().key)

                assert(map.size == maxSize)
            }

            assert(map.size == oldSize + 1 || map.size == maxSize)
            null
        }
    }

    override fun contains(key: K): Boolean {
        return getNode(key) != null
    }

    override fun size(): Int {
        assert(map.size <= maxSize)
        return map.size
    }

    /** Moves node to the head of the queue. */
    private fun moveToHead(node: Node<K, V>) {
        assert(head != null)
        assert(tail != null)
        assert(node.next != null || node == tail)
        assert(node.previous != null || node == head)

        val previous = node.previous
        val next = node.next
        next?.let { it.previous = previous } ?: run { tail = previous }
        previous?.let { it.next = next } ?: run { head = next }

        pushFront(node)
    }

    /** Sets given node as current head of the queue. */
    private fun pushFront(node: Node<K, V>) {
        node.previous = null
        head?.let {
            node.next = it
            it.previous = node
        } ?: run {
            tail = node
        }
        head = node
    }

    /** removes current tail from the queue. */
    private fun dropTail(): Node<K, V> {
        assert(tail != null)

        val oldTail = tail!!
        tail = oldTail.previous
        tail?.let { it.next = null } ?: run { head = null }
        return oldTail
    }

    /**
     * If map contains given key, returns Node with the key
     * and update its position in queue, returns {@code null} otherwise.
     */
    private fun getNode(key: K): Node<K, V>? {
        if (map.containsKey(key)) {
            val node = map[key]!!
            moveToHead(node)
            return node
        }
        return null
    }

}