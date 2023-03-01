package de.florianmichael.spigotcommandutils.util

class Timer {

    private var time = System.currentTimeMillis()

    fun reset() {
        time = System.currentTimeMillis()
    }

    fun hasTimeReached(delta: Long) = (System.currentTimeMillis() - time) >= delta
}
