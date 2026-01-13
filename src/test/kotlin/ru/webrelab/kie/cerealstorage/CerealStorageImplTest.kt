package ru.webrelab.kie.cerealstorage

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CerealStorageImplTest {

    private val storage = CerealStorageImpl(10f, 20f)

    @Test
    fun `should throw if containerCapacity is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(-4f, 10f)
        }
    }

    @Test
    fun `should throw if containerCapacity more than storageCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            CerealStorageImpl(20f, 10f)
        }
    }

    @Test
    fun `check creating container with cereal if it's not in Map`() {
        assertEquals(10F, storage.addCereal(Cereal.BUCKWHEAT, 10F))
    }

    @Test
    fun `check adding cereal if it's in Map`() {
        storage.addCereal(Cereal.PEAS, 5F)
        assertEquals(9F, storage.addCereal(Cereal.PEAS, 4F))
    }

    @Test
    fun `check the remaining cereal if amount more than containerCapacity`() {
        assertEquals(1F, storage.addCereal(Cereal.PEAS, 11F))

    }

    @Test
    fun `should throw if amount of cereal is negative`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.addCereal(Cereal.PEAS, -20F)
        }
    }

    @Test
    fun `should throw if storageCapacity is full`() {
        storage.addCereal(Cereal.PEAS, 10F)
        storage.addCereal(Cereal.BULGUR, 10F)
        assertThrows(IllegalStateException::class.java) {
            storage.addCereal(Cereal.PEAS, 20F)
        }
    }

    @Test
    fun `check leftover cereal in containerCapacity`() {
        storage.addCereal(Cereal.PEAS, 9F)
        assertEquals(4F, storage.getCereal(Cereal.PEAS, 5F))
    }

    @Test
    fun `check leftover bringing cereal`() {
        storage.addCereal(Cereal.RICE, 4F)
        assertEquals(4F, storage.getCereal(Cereal.RICE, 4F))
    }

    @Test
    fun `check leftover bringing cereal more than containerCapacity`() {
        storage.addCereal(Cereal.RICE, 4F)
        assertEquals(4F, storage.getCereal(Cereal.RICE, 8F))
    }

    @Test
    fun `should throw if get negative amount of cereal containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.RICE, -5F)
        }
    }

    @Test
    fun `should throw if cereal isn't containerCapacity`() {
        assertThrows(IllegalArgumentException::class.java) {
            storage.getCereal(Cereal.MILLET, 5F)
        }
    }

    @Test
    fun `check True if container removed`() {
        storage.addCereal(Cereal.PEAS, 2F)
        storage.getCereal(Cereal.PEAS, 2F)
        assertTrue { storage.removeContainer(Cereal.PEAS) }
    }

    @Test
    fun `check False if container is full`() {
        storage.addCereal(Cereal.PEAS, 9F)
        assertFalse { storage.removeContainer(Cereal.PEAS) }
    }

    @Test
    fun `check False if container wasn't found`() {
        assertFalse { storage.removeContainer(Cereal.BULGUR) }
    }

    @Test
    fun `check zero if cereal doesn't in container`() {
        assertEquals(0F, storage.getAmount(Cereal.BUCKWHEAT))
    }

    @Test
    fun `check amount if cereal is in container`() {
        storage.addCereal(Cereal.PEAS, 9F)
        assertEquals(9F, storage.getAmount(Cereal.PEAS))
    }

    @Test
    fun `check the amount of cereal that the container can hold`() {
        storage.addCereal(Cereal.PEAS, 9F)
        assertEquals(1F, storage.getSpace(Cereal.PEAS))
    }

    @Test
    fun `should throw if container doesn't found `() {
        assertThrows(IllegalStateException::class.java) {
            (storage.getSpace(Cereal.PEAS))
        }
    }
}