import kotlin.random.Random

// Класс, представляющий результат попытки угадать число
data class GuessResult(val bulls: Int, val cows: Int)

// Функция для генерации тайного 4-значного числа компьютером
fun generateSecretNumber(): String {
    // Генерация случайного 4-значного числа с неповторяющимися цифрами
    val digits = (0..9).shuffled().take(4)
    return digits.joinToString("")
}

// Функция для ввода попытки игроком
fun enterGuess(): String {
    print("Введите вашу попытку (4 цифры без повторений): ")
    while (true) {
        val guess = readLine()?.trim()
        // Проверка корректности ввода (4 цифры без повторений)
        if (guess?.matches(Regex("\\d{4}")) == true && guess.toSet().size == 4) {
            return guess
        } else {
            println("Некорректный ввод. Пожалуйста, введите 4 цифры без повторений.")
        }
    }
}

// Функция для проверки попытки и вычисления быков и коров
fun checkGuess(secretNumber: String, guess: String): GuessResult {
    // Переменные для хранения количества быков и коров
    var bulls = 0
    var cows = 0

    // Проверка каждой цифры в попытке
    for (i in secretNumber.indices) {
        if (secretNumber[i] == guess[i]) {
            bulls++
        } else if (secretNumber.contains(guess[i])) {
            cows++
        }
    }

    // Возвращаем результат
    return GuessResult(bulls, cows)
}

fun main() {
    println("Добро пожаловать в игру 'Быки и коровы'!")

    // Генерируем тайное число компьютером
    val secretNumber = generateSecretNumber()
    var attempts = 0

    println("Компьютер задумал 4-значное число. Попробуйте угадать!")

    // Основной цикл игры
    while (true) {
        // Вводим попытку игрока
        val guess = enterGuess()
        attempts++

        // Проверяем попытку и выводим результат
        val result = checkGuess(secretNumber, guess)
        println("Результат попытки $attempts: Быков - ${result.bulls}, Коров - ${result.cows}")

        // Проверяем, угадал ли игрок число полностью
        if (result.bulls == 4) {
            println("Поздравляем! Вы угадали число $secretNumber за $attempts попыток.")
            break
        }
    }
}
