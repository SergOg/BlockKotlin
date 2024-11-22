import kotlin.system.exitProcess

/*За основу берём код решения домашнего задания из предыдущего семинара и дорабатываем его.

— Добавьте новую команду export, которая экспортирует добавленные значения в текстовый файл в формате JSON.
Команда принимает путь к новому файлу. Например export /Users/user/myfile.json
— Реализуйте DSL на Kotlin, который позволит конструировать JSON и преобразовывать его в строку.
— Используйте этот DSL для экспорта данных в файл.
— Выходной JSON не обязательно должен быть отформатирован, поля объектов могут идти в любом порядке.
Главное, чтобы он имел корректный синтаксис. Такой вывод тоже принимается:
[{""emails"": [""ew@huh.ru""],""name"": ""Alex"",""phones"": [""34355"",""847564""]},{""emails"": [],""name"": ""Tom"",""phones"": [""84755""]}]

Записать текст в файл можно при помощи удобной функции-расширения writeText:
File(""/Users/user/file.txt"").writeText(""Text to write"")

Под капотом она использует такую конструкцию:
FileOutputStream(file).use {
it.write(text.toByteArray(Charsets.UTF_8))
}*/

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
                checkAddEmail(str, persons)
            } else println("Help")
            is CheckShow -> if (command.isValid()) {
                val str = command.showString()
                checkShow(str, persons)
            } else println("Help")
            is CheckFind -> if (command.isValid()) {
                val str = command.findString()
                checkFind(str, persons)
            } else println("Help")
            is CheckExport -> if (command.isValid()) {
                val str = command.exportString()
                checkExport(str, persons)
            } else println("Help")
        }
    }
}

fun checkExport(str: List<String>, persons: Person) {
    val pr = Tag()
    println(pr.a(str[1], persons))
}

open class Tag {
    protected val children = mutableListOf<Any>()

    fun b(callback: Tag.() -> Unit) {
        children.add(Bold().apply {
            callback()
        })
    }

    fun a(href: String, callback: Person) {
        children.add(Link(href).apply {
            callback
        })
    }

    fun text(text: String) : List<Any>{
        children.add(text)
        return children
    }
}

class Link(private val url: String) : Tag() {
    override fun toString(): String {
        return "jhgiyufiyutd7980tdiyrtd"
    }
}

class Bold() : Tag() {
    override fun toString(): String {
        return "j4574567457"
    }
}

fun checkAddEmail(str: List<String>, persons: Person) {
    persons.nameSet.add(str[1])
    val email = str[2].split('@')
    if (email.size > 1) {
        persons.emailMap[str[2]] = str[1]
    } else println("Неверно указан email")
}

fun checkShow(str: List<String>, persons: Person) {
    print(str[1])
    val phone = persons.phoneMap.filterValues { it == str[1] }
    print(" -> phone: $phone")
    val email = persons.emailMap.filterValues { it == str[1] }
    println(" email: $email")
}

fun checkFind(str: List<String>, persons: Person) {
    val email = str[1].split('@')
    print(str[1] + " ->")
    if (email.size > 1) {
        val person = persons.emailMap[str[1]]
        println(" person: $person")
    } else {
        val person = persons.phoneMap[str[1]]
        println(" person: $person")
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

class CheckExport(private val string: List<String>) : Command {
    override fun isValid(): Boolean {
        return string.size == 2 && string[0] == "export"
    }

    fun exportString(): List<String> {
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
        "export" -> CheckExport(str)
        else -> CheckHelp(str?.get(0)!!)
    }
}

data class Person(
    val nameSet: MutableSet<String> = mutableSetOf(),
    val phoneMap: MutableMap<String, String> = mutableMapOf(),
    val emailMap: MutableMap<String, String> = mutableMapOf()
)