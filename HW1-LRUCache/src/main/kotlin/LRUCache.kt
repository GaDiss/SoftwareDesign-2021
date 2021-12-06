/**
 * Least Recently Used Cache.
 *
 * A cache that holds strong references to a limited number of values.
 * Each time a value is accessed, it is moved to the head of a queue.
 * When a value is added to a full cache, the value at the end of that
 * queue is evicted and may become eligible for garbage collection.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
interface LRUCache<K, V> {
    /**
     * Returns the value for the key if it exists in the cache
     * or {@code null} otherwise.
     *
     * @param key the key whose cached value is to be returned
     * @return value for the key if it exists in the cache or {@code null} otherwise
     */
    fun get(key: K): V?

    /**
     * Caches value for key.
     *
     * @param key the key whose associated value is to be cached
     * @param value value to be cached
     * @return value, previously associated with the key or {@code null} otherwise
     */
    fun put(key: K, value: V): V?

    /**
     * Checks if value with specified key is cached.
     *
     * @param key the key whose presence is to be tested
     * @return {@code true} if the value with specified key is cached, {@code false} otherwise
     */
    fun contains(key: K): Boolean

    /**
     * Return the number of cached values.
     *
     * @return the number of cached values
     */
    fun size(): Int
}