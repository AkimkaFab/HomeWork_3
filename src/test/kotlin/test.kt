import data.Priority
import data.Task
import data.TasksRepository
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import io.qameta.allure.Allure

var tryNumber = 1

    class Tests : AnnotationSpec(){
        fun createRepository(): TasksRepository = data.TasksRepositoryMemory()
        val repository = createRepository()
        val taskAmount = (1..10).random()
        fun generateTestData(): List<Int> {
            val list = mutableListOf<Int>()
            for (i: Int in 0 until taskAmount) {
                val priorityLocal = listOf("LOW", "MEDIUM", "HIGH").random()
                list.add(repository.addTask(Task(name = "Task ${i+1}", priority = Priority.valueOf(priorityLocal) )))
            }
            return list
        }

        @Test
        fun createTasksTest() {
//            println("КОЛИЧЕСТВО ТАСОК: $taskAmount")
//            if (tryNumber != 1) {
//                var taskToDelete = 0
//                do {
//                    repository.deleteTask(taskToDelete++)
//                    println("Удалил таску номер $taskToDelete")
//                } while (taskToDelete != taskAmount)
//            }
            generateTestData()
            val allTasks = repository.getTasks()
            allTasks.size/2 shouldBe taskAmount
            //assert(allTasks.size == taskAmount) {"ТЕСТ СОЗДАНИЯ ЗАДАЧ НЕ ПРОЙДЕН"}
            println("ТЕСТ СОЗДАНИЯ ЗАДАЧ ПРОЙДЕН")


        }


        @Test
        fun completedUncompletedTasksFilterTest() {
            generateTestData()
            val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
            val allTasksOrigin = repository.getTasks()
            val completedTask = (1..taskAmount).random()
            repository.completeTask(completedTask)
            allTasks.removeAt(completedTask-1)
            val filteredTasks = repository.getTasks(completed = false)
            filteredTasks shouldBe allTasks
            //assert(filteredTasks == allTasks) {"ТЕСТ ЗАВЕРШЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"} // Проверяем, что завершенная задача пропала из фильтра
            println("ТЕСТ ЗАВЕРШЕНИЯ ЗАДАЧИ ПРОЙДЕН")
            repository.uncompleteTask(completedTask)
            val allTasks1 = repository.getTasks(completed = false)
            allTasks1 shouldBe allTasksOrigin
            //assert(allTasks1 == allTasksOrigin) {"ТЕСТ ОТМЕНЫ ЗАВЕРШЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"} // Проверяем, что после отмены завершения задача вернулась в фильтр и с неё пропал крестик
            println("ТЕСТ ОТМЕНЫ ЗАВЕРШЕНИЯ ЗАДАЧИ ПРОЙДЕН")

        }

        @Test
        fun deleteTaskTest() {
            generateTestData()
            val allTasks:MutableList<Task> = repository.getTasks().toMutableList()
            val deletedTask = (1..taskAmount).random()
            repository.deleteTask(deletedTask)
            allTasks.removeAt(deletedTask-1)
            val allTasks1 = repository.getTasks()
            allTasks1 shouldBe allTasks
            //assert(allTasks1 == allTasks) {"ТЕСТ УДАЛЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"}
            println("ТЕСТ УДАЛЕНИЯ ЗАДАЧИ ПРОЙДЕН")
        }

        @Test
        fun negativeTaskCounterTest() {
            println("НЕГАТИВНЫЙ ТЕСТ СЧЁТЧИКА ЗАДАЧ. ПОПЫТКА ${tryNumber++}")
            generateTestData()
            val allTasks = repository.getTasks()
            allTasks.size/2 shouldBe taskAmount+100
            //assert(allTasks.size == taskAmount+100) {"ТЕСТ СЧЕТЧИКА ЗАДАЧ НЕ ПРОЙДЕН"}
            println("ТЕСТ СЧЕТЧИКА ЗАДАЧ ПРОЙДЕН")
        }


    }
