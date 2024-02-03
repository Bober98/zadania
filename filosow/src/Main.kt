import kotlinx.coroutines.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.random.Random

// Класс, представляющий философа
class Philosopher(private val id: Int, private val leftFork: ReentrantLock, private val rightFork: ReentrantLock) {

    // Функция для имитации размышления
    suspend fun think() {
        println("Философ $id размышляет.")
        // Имитация времени размышления
        delay(Random.nextLong(100, 500))
    }

    // Функция для имитации обеда
    suspend fun eat() {
        // Захватываем вилки поочередно
        withContext(Dispatchers.Default) {
            leftFork.lock()
            rightFork.lock()
        }

        println("Философ $id обедает.")

        // Отпускаем вилки после обеда
        leftFork.unlock()
        rightFork.unlock()

        // Имитация времени обеда
        delay(Random.nextLong(100, 500))
    }
}

suspend fun main() {
    // Количество философов
    val numPhilosophers = 5
    // Создаем список вилок
    val forks = List(numPhilosophers) { ReentrantLock() }

    // Создаем список философов
    val philosophers = List(numPhilosophers) {
        Philosopher(it + 1, forks[it], forks[(it + 1) % numPhilosophers])
    }

    // Запускаем корутины для каждого философа
    val jobs = philosophers.map {
        GlobalScope.launch {
            while (isActive) {
                it.think()
                it.eat()
            }
        }
    }

    // Ждем некоторое время
    delay(5000)

    // Отменяем корутины (завершаем работу философов)
    jobs.forEach { it.cancelAndJoin() }
}

 