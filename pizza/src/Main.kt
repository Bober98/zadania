import kotlin.random.Random

class PizzaCity(
    val cityName: String,
    private var totalChecksShown: Int = 0,
    private var totalDiscounts: Double = 0.0,
    private var totalCoffeesSold: Int = 0,
    private var totalCoffeeRevenue: Double = 0.0,
    private var checkPhotoCount: Int = 0,
    private var coffeePurchaseCount: Int = 0,
    private val pizzaSales: MutableMap<String, Int> = mutableMapOf(),
    private val sauceSales: MutableMap<String, Int> = mutableMapOf(),
    private var totalRevenue: Double = 0.0
) {
    // Функция для продажи пиццы с учетом скидок
    fun sellPizzaWithDiscount(pizzaType: String, discount: Double) {
        totalChecksShown++
        totalDiscounts += discount

        pizzaSales[pizzaType] = pizzaSales.getOrDefault(pizzaType, 0) + 1
    }

    // Функция для продажи кофе
    fun sellCoffee(price: Double) {
        totalCoffeesSold++
        totalCoffeeRevenue += price
    }

    // Функция для предложения кофе и скидки
    fun offerCoffeeAndDiscount(checkPhoto: Boolean, pizzaType: String, sauceType: String?, saucePrice: Double?) {
        // Учитываем скидку за фото чека
        if (checkPhoto) {
            checkPhotoCount++
            totalDiscounts += 5.0
        }

        // Учитываем покупку кофе
        if (Random.nextBoolean()) {
            coffeePurchaseCount++
            totalCoffeeRevenue += 2.0
        }

        // Учитываем продажу пиццы с учетом скидки
        sellPizzaWithDiscount(pizzaType, 0.0)

        // Учитываем продажу соуса (если предлагается)
        if (sauceType != null && saucePrice != null) {
            sauceSales[sauceType] = sauceSales.getOrDefault(sauceType, 0) + 1
            totalRevenue += saucePrice
        }
    }

    // Функция для вывода статистики
    fun showStatistics() {
        println("Статистика для $cityName:")
        println("Количество показанных чеков: $totalChecksShown")
        println("Общая сумма скидок: $totalDiscounts")
        println("Количество покупок кофе: $coffeePurchaseCount")
        println("Общая сумма выручки за кофе: $totalCoffeeRevenue")
        println("Процент показавших фото чека: ${percentage(checkPhotoCount, totalChecksShown)}%")
        println("Процент покупок кофе: ${percentage(coffeePurchaseCount, totalChecksShown)}%")

        println("\nСтатистика по пиццам:")
        pizzaSales.forEach { (pizzaType, count) ->
            println("Пицца '$pizzaType': $count покупок (${percentage(count, totalChecksShown)}%)")
        }

        println("\nСтатистика по соусам:")
        sauceSales.forEach { (sauceType, count) ->
            println("Соус '$sauceType': $count покупок")
        }

        println("\nОбщая выручка: $totalRevenue")
    }

    // Вспомогательная функция для вычисления процентов
    private fun percentage(value: Int, total: Int): Double {
        return if (total == 0) 0.0 else (value.toDouble() / total.toDouble()) * 100.0
    }
}

fun main() {
    val city1 = PizzaCity("Город1")
    val city2 = PizzaCity("Город2")

    // Пример использования методов
    city1.sellPizzaWithDiscount("Маргарита", 2.0)
    city1.sellCoffee(2.5)
    city1.offerCoffeeAndDiscount(true, "Пепперони", "Томатный", 1.0)

    city2.sellPizzaWithDiscount("Гавайская", 3.0)
    city2.offerCoffeeAndDiscount(false, "Сырная", "Грибной", 1.5)

    // Пример вывода статистики
    city1.showStatistics()
    println("\n-------------------------\n")
    city2.showStatistics()
}
