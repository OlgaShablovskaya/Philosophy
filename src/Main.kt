import kotlin.concurrent.thread
import java.util.concurrent.Semaphore
class Philosopher(private val name: String, private val leftFork: Semaphore, private val rightFork: Semaphore) : Runnable {
    override fun run() { // класс философ
        repeat(5) {
            think()
            eat()
        }
    }
    private fun think() { // философ размышляет
        println("$name размышляет")
        Thread.sleep((Math.random() * 1000).toLong())
    }
    private fun eat() {
        leftFork.acquire()// левая вилка
        rightFork.acquire()// правая вилка
        println("$name ест")// философ ест
        leftFork.release()// вилки освобождаются
        rightFork.release()
        // философ делает перерыв перед следующим циклом размышлений и еды
        Thread.sleep((Math.random() * 1000).toLong())
    }
}
fun main() {
    val numPhilosophers = 5
    val forks = List(numPhilosophers) { Semaphore(1) } // создание списока семафоров для представления вилок
    repeat(numPhilosophers) { i -> // создание потоков для каждого философа
        thread {
            // получаем левую и правую вилку для каждого философа
            val leftFork = forks[i]
            val rightFork = forks[(i + 1) % numPhilosophers]
            val philosopher = Philosopher("Философ ${i + 1}", leftFork, rightFork) // создание философа
            philosopher.run() // запуска цикла размышлений и еды для каждого философа
        }
    }
}
