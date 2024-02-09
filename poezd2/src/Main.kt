import kotlin.random.Random

// Класс, представляющий поезд
class Train(val direction: String) {
    // Список вагонов в поезде
    val wagons = mutableListOf<Wagon>()

    // Метод для добавления вагона в поезд
    fun addWagon(capacity: Int) {
        wagons.add(Wagon(capacity))
    }

    // Метод для продажи билетов в каждом вагоне
    fun sellTickets() {
        for (wagon in wagons) {
            wagon.sellTickets()
        }
    }

    // Метод для отображения информации о поезде
    fun displayInfo() {
        println("Поезд $direction отправлен.")
        println("Количество вагонов: ${wagons.size}")
        for ((index, wagon) in wagons.withIndex()) {
            println("Вагон ${index + 1}: Вместимость - ${wagon.capacity}, Пассажиров - ${wagon.passengers}")
        }
    }
}

// Класс, представляющий вагон
class Wagon(val capacity: Int) {
    // Количество пассажиров в вагоне
    var passengers = 0
        private set

    // Метод для продажи билетов в вагоне
    fun sellTickets() {
        // Генерируем случайное количество пассажиров в диапазоне от 5 до 201
        passengers = Random.nextInt(5, minOf(202, capacity + 1))
    }

    // Метод для добавления пассажира в вагон
    fun addPassenger() {
        if (passengers < capacity) {
            passengers++
        }
    }
}

// Класс для планирования поездов
class TrainPlanner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Список городов для создания направлений
            val cityList = listOf("Бийск", "Барнаул", "Новосибирск", "Томск", "Красноярск", "Иркутск", "Омск", "Кемерово", "Новокузнецк", "Курган", "Тюмень", "Челябинск", "Екатеринбург", "Пермь", "Оренбург")
            val random = Random(System.currentTimeMillis())

            // Цикл работы программы
            while (true) {
                // Запрос на создание нового поезда или завершение работы
                print("Хотите ли вы закончить работу (EXIT) или составить поезд (нажмите Enter): ")
                val userInput = readLine() ?: ""

                if (userInput.toUpperCase() == "EXIT") {
                    break
                }

                // Создание случайного направления для поезда
                val direction = createDirection(cityList, random)
                val train = Train(direction)

                println("Шаг 1: Создано направление - $direction")

                // Продажа билетов для поезда
                train.sellTickets()
                println("Шаг 2: Продано билетов")

                // Формирование поезда с учетом проданных билетов и вместимости вагонов
                for (wagon in train.wagons) {
                    while (wagon.passengers < wagon.capacity) {
                        wagon.addPassenger()
                    }
                }

                println("Шаг 3: Сформирован поезд")

                // Отображение информации о поезде
                train.displayInfo()
                println("Шаг 4: Отправлен поезд")
            }
        }

        // Метод для создания случайного направления поезда
        private fun createDirection(cityList: List<String>, random: Random): String {
            val start = cityList.random()
            var end = cityList.random()
            while (start == end) {
                end = cityList.random()
            }
            return "$start - $end"
        }
    }
}
