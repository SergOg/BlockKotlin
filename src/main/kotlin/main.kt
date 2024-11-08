
fun main() {
    println("sumAll = ${sumAll(1, 5, 20)}")
    println("sumAll = ${sumAll()}")
    println("sumAll = ${sumAll(2, 3, 4, 5, 6, 7)}")

    println(createOutputString(name = "Alice", 42, null))
    println(createOutputString("Bob", 23, null))
    println(createOutputString(isStudent = true, name = "Carol", age = 19))
    println(createOutputString("Daniel", 32, isStudent = null))

    println(multiplyBy(null, 4))
    println(multiplyBy(3, 4))

    stars(1, 2, 4)
    println("Введите команду")
    var str: String? = readLine()
    if (str != null) {
        dz(str)
    } else println("Некорректный ввод")
}

fun dz(str: String) {
    var str1: String? = str
    while (str1 != "exit") {
        val parts = str1!!.split(" ")
        if (parts.size in 2..4) {
            val email = parts[3].split('@')
            if (email.size > 1) {
                println("Команда: ${parts[0]}, имя: ${parts[1]}, email: ${parts[3]}")
            } else {
                println("Команда: ${parts[0]}, имя: ${parts[1]}, phone: ${parts[3]}")
            }
        } else if (parts.size > 4){
            println("Неверная команда")
        }else{
            println("Команда: ${parts[0]}")
        }
        str1 = readLine()
    }
}

fun stars(a: Int, b: Int, c: Int) {
    var q = a
    if (a > 0 && b > 0 && c > 0) {
        for (y in 0..b) {
            for (x in 0 until q) {
                print('*')
            }
            println()
            q += c
        }
        q -= c
        for (y in 0 until b) {
            for (x in 0 until q - c) {
                print('*')
            }
            println()
            q -= c
        }
    }
}

fun multiplyBy(nothing: Int?, i: Int): Any? {
    return if (nothing != null) {
        nothing * i
    } else null
}

fun createOutputString(name: String, age: Int, isStudent: Boolean?): String {
    return when (isStudent) {
        true -> "student $name has age of $age"
        else -> "$name has age of $age"
    }
}

fun sumAll(vararg args: Int): Int {
    val a = args.size
    var summ: Int = 0
    for (i in 0 until a) {
        summ += args[i]
    }
    return summ
}