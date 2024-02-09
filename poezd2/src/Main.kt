import kotlin.random.Random
import kotlin.math.min

// Класс, представляющий поезд
class Train(val direction: String) {
    // Список вагонов в поезде
    val wagons = mutableListOf<Wagon>()

    // Метод для добавления вагона в поезд
    fun addWagon(capacity: Int) {
        wagons.add(Wagon(capacity))
    }

    // Метод для продажи билетов в каждом вагоне
    fun sellTickets(passengerCount: Int) {
        var passengersLeft = passengerCount
        for (wagon in wagons) {
            val ticketsSold = min(passengersLeft, wagon.capacity - wagon.passengers) // Продаем только доступное количество билетов для данного вагона
            wagon.sellTickets(ticketsSold)
            passengersLeft -= ticketsSold
            if (passengersLeft <= 0) break
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
    fun sellTickets(ticketsSold: Int) {
        passengers += ticketsSold
    }
}

// Класс для планирования поездов
class TrainPlanner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val cityList = listOf("Бийск", "Барнаул", "Новосибирск", "Томск", "Красноярск", "Иркутск", "Омск", "Кемерово", "Новокузнецк", "Курган", "Тюмень", "Челябинск", "Екатеринбург", "Пермь", "Оренбург")
            val random = Random(System.currentTimeMillis())

            while (true) {
                print("Хотите ли вы закончить работу (EXIT) или составить поезд (нажмите Enter): ")
                val userInput = readLine() ?: ""

                if (userInput.toUpperCase() == "EXIT") {
                    break
                }

                val direction = createDirection(cityList, random)
                val train = Train(direction)

                println("Шаг 1: Создано направление - $direction")

                val ticketCount = Random.nextInt(5, 202) // Генерируем случайное количество проданных билетов
                println("Шаг 2: Продано билетов ($ticketCount)")

                val wagonCapacities = generateWagonCapacities(ticketCount, random)
                for (capacity in wagonCapacities) {
                    train.addWagon(capacity)
                }

                println("Шаг 3: Сформирован поезд")

                train.sellTickets(ticketCount) // Продаем билеты

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

        // Метод для генерации вместимостей вагонов
        private fun generateWagonCapacities(passengerCount: Int, random: Random): List<Int> {
            val capacities = mutableListOf<Int>()
            var passengersLeft = passengerCount
            while (passengersLeft > 0) {
                val capacity = min(passengersLeft, Random.nextInt(5, 26)) // Генерируем случайную вместимость вагона от 5 до 25 или оставшееся количество пассажиров, если оно меньше
                capacities.add(capacity)
                passengersLeft -= capacity
            }
            return capacities
        }
    }
}
