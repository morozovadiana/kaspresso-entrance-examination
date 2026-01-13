package ru.webrelab.kie.cerealstorage

import kotlin.IllegalStateException

class CerealStorageImpl(
    override val containerCapacity: Float,
    override val storageCapacity: Float
) : CerealStorage {

    /**
     * Блок инициализации класса.
     * Выполняется сразу при создании объекта
     */
    init {
        require(containerCapacity >= 0) {
            "Ёмкость контейнера не может быть отрицательной"
        }
        require(storageCapacity >= containerCapacity) {
            "Ёмкость хранилища не должна быть меньше ёмкости одного контейнера"
        }
    }

    private val storage = mutableMapOf<Cereal, Float>()
    override fun addCereal(cereal: Cereal, amount: Float): Float {
        check(amount > 0) { throw IllegalArgumentException() }
        val value = getAmount(cereal) + amount
        check(storageCapacity > value)
        storage[cereal] = if (value < containerCapacity) value else containerCapacity
        return if (value > containerCapacity) value - containerCapacity else value

    }

    override fun getCereal(cereal: Cereal, amount: Float): Float {
        check(storage.containsKey(cereal)) { throw IllegalArgumentException() }

        val result = when {
            amount == getAmount(cereal) -> amount
            amount < getAmount(cereal) -> getAmount(cereal) - amount
            amount > getAmount(cereal) -> getAmount(cereal)
            else -> throw IllegalArgumentException()
        }
        if (storage.getValue(cereal) >= 0F)
            storage[cereal] = getAmount(cereal) - amount
        else {
            storage[cereal] = 0F
            removeContainer(cereal)
        }
        return result

    }

    override fun removeContainer(cereal: Cereal): Boolean {
        return if (storage.contains(cereal) && storage.containsValue(0F)) {
            storage.keys.remove(cereal)
        } else {
            false
        }
    }

    override fun getAmount(cereal: Cereal): Float = storage[cereal] ?: 0F

    override fun getSpace(cereal: Cereal): Float {
        check(storage.contains(cereal)) { java.lang.IllegalStateException() }
        return containerCapacity - getAmount(cereal)
    }

    override fun toString(): String {
        return storage.entries.joinToString { "cereal : \"${it.key}\" - amount : \"${it.value}\"" }
    }

}
