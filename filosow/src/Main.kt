import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class Fork(val id: Int) {
    private val lock: Lock = ReentrantLock()

    fun pickUp() {
        lock.lock()
    }

    fun putDown() {
        lock.unlock()
    }
}

class Philosopher(val id: Int, val leftFork: Fork, val rightFork: Fork, val isRunning: AtomicBoolean) : Thread() {
    override fun run() {
        while (isRunning.get()) {
            think()
            eat()
        }
    }

    private fun think() {
        println("Философ $id размышляет.")
        Thread.sleep(100)
    }

    private fun eat() {
        leftFork.pickUp()
        rightFork.pickUp()

        println("Философ $id обедает.")
        Thread.sleep(100)

        leftFork.putDown()
        rightFork.putDown()
    }
}

fun main() {
    val numOfPhilosophers = 5
    val forks = List(numOfPhilosophers) { Fork(it) }

    val isRunning = AtomicBoolean(true)

    val philosophers = List(numOfPhilosophers) {
        Philosopher(it, forks[it], forks[(it + 1) % numOfPhilosophers], isRunning)
    }

    philosophers.forEach { it.start() }

    // Ждем некоторое время, а затем устанавливаем флаг завершения
    Thread.sleep(5000)
    isRunning.set(false)

    // Ждем, пока все потоки философов завершатся
    philosophers.forEach { it.join() }
}
