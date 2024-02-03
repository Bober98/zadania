import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

// Класс, представляющий вилку
class Fork(val id: Int) {
    private val lock: Lock = ReentrantLock()

    // Функция для взятия вилки
    fun pickUp() {
        lock.lock()
    }

    // Функция для положения вилки
    fun putDown() {
        lock.unlock()
    }
}

// Класс, представляющий философа
class Philosopher(val id: Int, val leftFork: Fork, val rightFork: Fork, val isRunning: AtomicBoolean) : Thread() {
    // Функция, выполняемая потоком философа
    override fun run() {
        while (isRunning.get()) {
            think()
            eat()
        }
    }

    // Функция для имитации размышления
    private fun think() {
        println("Философ $id размышляет.")
        // Добавьте задержку для имитации размышления
        Thread.sleep(100)
    }

    // Функция для имитации обеда
    private fun eat() {
        // Захватываем вилки поочередно
        leftFork.pickUp()
        rightFork.pickUp()

        println("Философ $id обедает.")
        // Добавьте задержку для имитации обеда
        Thread.sleep(100)

        // Отпускаем вилки после обеда
        leftFork.putDown()
        rightFork.putDown()
    }
}

fun main() {
    // Количество философов и вилок
    val numOfPhilosophers = 5
    // Создаем список вилок
    val forks = List(numOfPhilosophers) { Fork(it) }

    // Используем AtomicBoolean для обеспечения видимости изменений в разных потоках
    val isRunning = AtomicBoolean(true)

    // Создаем список философов, присваивая каждому пару вилок и флаг завершения
    val philosophers = List(numOfPhilosophers) {
        Philosopher(it, forks[it], forks[(it + 1) % numOfPhilosophers], isRunning)
    }

    // Запускаем каждого философа в отдельном потоке
    philosophers.forEach { it.start() }

    // Ждем некоторое время, а затем устанавливаем флаг завершения
    Thread.sleep(5000)
    isRunning.set(false)

    // Ждем, пока все потоки философов завершатся
    philosophers.forEach { it.join() }
}
