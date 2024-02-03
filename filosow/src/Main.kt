import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.random.Random

class Philosopher(private val id: Int, private val leftFork: ReentrantLock, private val rightFork: ReentrantLock) : Thread() {

    override fun run() {
        while (true) {
            think()
            eat()
        }
    }

    private fun think() {
        println("Философ $id размышляет.")
        Thread.sleep(Random.nextLong(100, 500))
    }

    private fun eat() {
        leftFork.lock()
        rightFork.lock()

        println("Философ $id обедает.")

        leftFork.unlock()
        rightFork.unlock()

        Thread.sleep(Random.nextLong(100, 500))
    }
}

fun main() {
    val numPhilosophers = 5
    val forks = List(numPhilosophers) { ReentrantLock() }

    val philosophers = List(numPhilosophers) {
        Philosopher(it + 1, forks[it], forks[(it + 1) % numPhilosophers])
    }

    philosophers.shuffled().forEach { it.start() }

    Thread.sleep(5000)

    philosophers.forEach { it.interrupt() }
    philosophers.forEach { it.join() }
}
