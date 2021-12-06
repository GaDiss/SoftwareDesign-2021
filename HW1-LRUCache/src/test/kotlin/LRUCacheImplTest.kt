import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LRUCacheImplTest {

    private val emptyLRUCache = LRUCacheImpl<Int, Int?>(0)
    private val singletonLRUCache = LRUCacheImpl<Int?, Int?>(1)
    private val smallLRUCache = LRUCacheImpl<Int, Int?>(2)
    private val regularLRUCache = LRUCacheImpl<Int, Int?>(4)

    @Test
    fun `empty cache`() {
        emptyLRUCache.put(0, 0)
        assertNull(emptyLRUCache.get(0))
        assertEquals(0, emptyLRUCache.size())
    }

    @Test
    fun `overflow singleton cache`() {
        singletonLRUCache.put(0, 0)
        singletonLRUCache.put(1, 1)
        assertNull(singletonLRUCache.get(0))
        assertEquals(1, singletonLRUCache.get(1))
        assertEquals(1, singletonLRUCache.size())
    }

    @Test
    fun `same key singleton cache`() {
        singletonLRUCache.put(0, 1)
        assertEquals(1, singletonLRUCache.put(0, 2))
        assertEquals(2, singletonLRUCache.get(0))
        assertEquals(1, singletonLRUCache.size())
    }

    @Test
    fun contains() {
        singletonLRUCache.put(0, 0)
        assertTrue(singletonLRUCache.contains(0))
        assertFalse(singletonLRUCache.contains(1))
        singletonLRUCache.put(1, 0)
        assertFalse(singletonLRUCache.contains(0))
    }

    @Test
    fun `null value`() {
        assertEquals(null, singletonLRUCache.get(0))
        assertFalse(singletonLRUCache.contains(0))

        singletonLRUCache.put(0, null)

        assertEquals(null, singletonLRUCache.get(0))
        assertTrue(singletonLRUCache.contains(0))
    }

    @Test
    fun `null key`() {
        singletonLRUCache.put(null, 0)
        assertEquals(0, singletonLRUCache.get(null))
    }

    @Test
    fun `update of elements in the middle`() {
        for (i in 4 downTo 1) regularLRUCache.put(i, i) // order 1 -> 2 -> 3 -> 4

        //get from the middle
        regularLRUCache.get(3)                          // order 3 -> 1 -> 2 -> 4
        regularLRUCache.put(5, 5)                       // order 5 -> 3 -> 1 -> 2
        assertFalse(regularLRUCache.contains(4))
        regularLRUCache.put(6, 6)                       // order 6 -> 5 -> 3 -> 1
        assertFalse(regularLRUCache.contains(2))
    }

    @Test
    fun `update of elements in the back`() {
        for (i in 2 downTo 1) smallLRUCache.put(i, i)   // order 1 -> 2

        //get from the back
        smallLRUCache.get(2)                            // order 2 -> 1
        smallLRUCache.put(3, 3)                         // order 3 -> 2
        assertFalse(smallLRUCache.contains(1))
    }

    @Test
    fun `update order on put`() {
        for (i in 2 downTo 1) smallLRUCache.put(i, i)  // order 1 -> 2

        smallLRUCache.put(2, 4)                        // order 2 -> 1
        smallLRUCache.put(3, 3)                        // order 3 -> 2
        assertFalse(smallLRUCache.contains(1))
    }

    @Test
    fun `max size less then 0`() {
        assertThrows(IllegalArgumentException::class.java) {
            LRUCacheImpl<Int, Int?>(-1)
        }
    }
}