/*За основу берём код решения домашнего задания из предыдущего семинара и дорабатываем его.
— Создайте иерархию sealed классов, которые представляют собой команды. В корне иерархии интерфейс Command.
— В каждом классе иерархии должна быть функция isValid(): Boolean, которая возвращает true, если команда введена с корректными аргументами.
Проверку телефона и email нужно перенести в эту функцию.
— Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает один из классов-наследников
Command, соответствующий введённой команде.
— Создайте data класс Person, который представляет собой запись о человеке. Этот класс должен содержать поля:
name – имя человека
phone – номер телефона
email – адрес электронной почты
— Добавьте новую команду show, которая выводит последнее значение, введённой с помощью команды add. Для этого значение должно быть сохранено
в переменную типа Person. Если на момент выполнения команды show не было ничего введено, нужно вывести на экран сообщение “Not initialized”.
— Функция main должна выглядеть следующем образом. Для каждой команды от пользователя:
Читаем команду с помощью функции readCommand
Выводим на экран получившийся экземпляр Command
Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.*/

fun main() {
    var person = Person()
    while (true) {
        println("Введите команду:")
        var command: Command = readCommand()
        when (command) {
            is CheckExit -> {
                println("Exit")
                System.exit(0)
            }
            is CheckHelp -> if (command.isValid()) println("Help") else System.exit(0)
            is CheckAdd -> if (command.isValid()) {
                println("Add")

            } else println("Help")
            is CheckShow -> if (command.isValid()) println(person) else println("Not initialized")
        }
    }
}

sealed interface Command {
    fun isValid(): Boolean
}

class CheckHelp(private val string: String) : Command {
    override fun isValid(): Boolean {
        return string == "help"
    }
}

class CheckExit(private val string: String) : Command {
    override fun isValid(): Boolean {
        return string == "exit"
    }
}

class CheckAdd(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
//        Проверку телефона и email нужно перенести в эту функцию
        TODO("Not yet implemented")
    }
}

class CheckShow(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size > 1
    }
}

fun readCommand(): Command {
    val str = readLine()?.split(" ")
    return when (str?.get(0)) {
        "exit" -> CheckExit(str[0])
        "help" -> CheckHelp(str[0])
        "add" -> CheckAdd(str)
        "show" -> CheckShow(str)
        else -> CheckHelp(str?.get(0)!!)    //Хваленый sealed не работает и нужен else c говнокодом
    }
}

data class Person(
    var name: String = "",
    var phone: String = "",
    var email: String = ""
)