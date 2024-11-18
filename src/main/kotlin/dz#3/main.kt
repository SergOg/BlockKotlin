import kotlin.system.exitProcess

/*За основу берём код решения домашнего задания из предыдущего семинара и дорабатываем его.

— Измените класс Person так, чтобы он содержал список телефонов и список почтовых адресов, связанных с человеком.
— Теперь в телефонной книге могут храниться записи о нескольких людях. Используйте для этого наиболее подходящую структуру данных.
— Команда AddPhone теперь должна добавлять новый телефон к записи соответствующего человека.
— Команда AddEmail теперь должна добавлять новый email к записи соответствующего человека.
— Команда show должна принимать в качестве аргумента имя человека и выводить связанные с ним телефоны и адреса электронной почты.
— Добавьте команду find, которая принимает email или телефон и выводит список людей, для которых записано такое значение.*/

fun main() {
    val persons = Person()
    while (true) {
        println("Введите команду:")
        val command: Command = readCommand()
        when (command) {
            is CheckExit -> {
                println("Exit")
                exitProcess(0)
            }
            is CheckHelp -> if (command.isValid()) println("Help") else {
                println("Пользователь не захотел больше общаться :(")
                exitProcess(0)
            }
            is CheckAddPhone -> if (command.isValid()) {
                val str = command.sendString()
                persons.nameSet.add(str[1])
                persons.phoneMap[str[2]] = str[1]
            } else println("Help")
            is CheckAddEmail -> if (command.isValid()) {
                val str = command.sendString()
                persons.nameSet.add(str[1])
                val email = str[2].split('@')
                if (email.size > 1) {
                    persons.emailMap[str[2]] = str[1]
                } else println("Неверно указан email")
            } else println("Help")
            is CheckShow -> if (command.isValid()) {
                val str = command.showString()
                print(str[1])
                val phone = persons.phoneMap.filterValues { it == str[1] }
                print(" -> phone: $phone")
                val email = persons.emailMap.filterValues { it == str[1] }
                println(" email: $email")
            } else println("Help")
            is CheckFind -> if (command.isValid()) {
                val str = command.findString()
                val email = str[1].split('@')
                print(str[1] + " ->")
                if (email.size > 1) {
                    val person = persons.emailMap[str[1]]
                    println(" person: $person")
                } else {
                    val person = persons.phoneMap[str[1]]
                    println(" person: $person")
                }
            } else println("Help")
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

class CheckAddPhone(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size == 3
    }

    fun sendString(): List<String> {
        return string
    }
}

class CheckAddEmail(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size == 3
    }

    fun sendString(): List<String> {
        return string
    }
}

class CheckShow(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size == 2 && string[0] == "show"
    }

    fun showString(): List<String> {
        return string
    }
}

class CheckFind(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size == 2 && string[0] == "find"
    }

    fun findString(): List<String> {
        return string
    }
}

fun readCommand(): Command {
    val str = readLine()?.split(" ")
    return when (str?.get(0)) {
        "exit" -> CheckExit(str[0])
        "help" -> CheckHelp(str[0])
        "addPhone" -> CheckAddPhone(str)
        "addEmail" -> CheckAddEmail(str)
        "show" -> CheckShow(str)
        "find" -> CheckFind(str)
        else -> CheckHelp(str?.get(0)!!)
    }
}

data class Person(
    val nameSet: MutableSet<String> = mutableSetOf(),
    val phoneMap: MutableMap<String, String> = mutableMapOf(),
    val emailMap: MutableMap<String, String> = mutableMapOf()
)