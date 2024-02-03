import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlin.random.Random

// Класс, представляющий философа
class Philosopher(private val id: Int, private val leftFork: Mutex, private val rightFork: Mutex) {

    // Функция для имитации размышления
    suspend fun think() {
        println("Философ $id размышляет.")
        // Добавляем случайную задержку для имитации размышления
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

        // Добавляем случайную задержку для имитации обеда
        delay(Random.nextLong(100, 500))
    }
}

suspend fun main() {
    // Количество философов и вилок
    val numPhilosophers = 5
    // Создаем список вилок с использованием Mutex для синхронизации доступа
    val forks = List(numPhilosophers) { Mutex() }

    // Создаем список философов, присваивая каждому пару вилок
    val philosophers = List(numPhilosophers) {
        Philosopher(it + 1, forks[it], forks[(it + 1) % numPhilosophers])
    }

    // Создаем корутины для каждого философа
    val jobs = philosophers.map {
        GlobalScope.launch {
            // Философ поочередно размышляет и обедает в бесконечном цикле
            while (isActive) {
                it.think()
                it.eat()
            }
        }
    }

    // Пауза для имитации работы программы
    delay(5000)

    // Останавливаем и ждем завершения всех корутин
    jobs.forEach { it.cancelAndJoin() }
}

