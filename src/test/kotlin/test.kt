import data.Priority
import data.Task
import data.TasksRepository
import org.junit.jupiter.api.Test
import io.qameta.allure.Allure

    class Tests {
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
            generateTestData()
            val allTasks = repository.getTasks()
            assert(allTasks.size == taskAmount) {"ТЕСТ СОЗДАНИЯ ЗАДАЧ НЕ ПРОЙДЕН"}
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
            assert(filteredTasks == allTasks) {"ТЕСТ ЗАВЕРШЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"} // Проверяем, что завершенная задача пропала из фильтра
            println("ТЕСТ ЗАВЕРШЕНИЯ ЗАДАЧИ ПРОЙДЕН")
            repository.uncompleteTask(completedTask)
            val allTasks1 = repository.getTasks(completed = false)
            assert(allTasks1 == allTasksOrigin) {"ТЕСТ ОТМЕНЫ ЗАВЕРШЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"} // Проверяем, что после отмены завершения задача вернулась в фильтр и с неё пропал крестик
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
            assert(allTasks1 == allTasks) {"ТЕСТ УДАЛЕНИЯ ЗАДАЧИ НЕ ПРОЙДЕН"}
            println("ТЕСТ УДАЛЕНИЯ ЗАДАЧИ ПРОЙДЕН")
        }

    }
