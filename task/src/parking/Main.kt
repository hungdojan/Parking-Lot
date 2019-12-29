package parking

import java.util.*

val sc = Scanner(System.`in`)
var parkSlotInfo = Array<Any>(0) { false } // if there is no car -> false
fun main() {
    var input: String
    do {
        input = sc.next()
        if (input == "exit") break
        if (input != "create" && parkSlotInfo.isEmpty()) {
            println("Sorry, parking lot is not created.")
            sc.nextLine()
            continue
        }
        menu(input)
    } while (input != "exit")
}

fun menu(input: String) {
    when (input) {
        "status" -> status()
        "leave" -> leave()
        "park" -> park()
        "create" -> parkSlotInfo = create()
        "reg_by_color" -> regByColor()
        "spot_by_color" -> slotByColor()
        "spot_by_reg" -> slotByReg()
    }
}

fun regByColor() {
    val color = sc.next()
    var found = ""
    for (i in parkSlotInfo.indices) {
        if (parkSlotInfo[i] is CarInfo) {
            val obj: CarInfo = parkSlotInfo[i] as CarInfo
            if (color.toUpperCase() == obj.color.toUpperCase()) {
                found += "${obj.registNum}, "
            }
        }
    }
    if (found.isEmpty())
        println("No cars with color $color were found.")
    else
        println(found.substring(0, found.length - 2))
}

fun slotByColor() {
    val color = sc.next()
    var found = ""
    for (i in parkSlotInfo.indices) {
        if (parkSlotInfo[i] is CarInfo) {
            val obj: CarInfo = parkSlotInfo[i] as CarInfo
            if (color.toUpperCase() == obj.color.toUpperCase()) {
                found += "${i + 1}, "
            }
        }
    }
    if (found.isEmpty())
        println("No cars with color $color were found.")
    else
        println(found.substring(0, found.length - 2))
}

fun slotByReg() {
    val registNum = sc.next()
    var found = ""
    for (i in parkSlotInfo.indices) {
        if (parkSlotInfo[i] is CarInfo) {
            val obj: CarInfo = parkSlotInfo[i] as CarInfo
            if (registNum == obj.registNum) {
                found += "${i + 1}, "
            }
        }
    }
    if (found.isEmpty())
        println("No cars with registration number $registNum were found.")
    else
        println(found.substring(0, found.length - 2))
}

/**
 * Creates new parking lot
 * @return New parking lot
 */
fun create(): Array<Any> {
    val parkSize = sc.nextInt()
    val newParkLot = Array<Any>(parkSize) { false }
    println("Created a parking lot with $parkSize spots.")
    return newParkLot
}

/**
 * Parks a car
 */
fun park() {
    val registNum = sc.next()
    val color = sc.next()
    val firstFreeSpot = isFreeSlot(registNum, color)
    if (firstFreeSpot == 0) {
        println("Sorry, parking lot is full.")
    } else {
        println("$color car parked on the spot $firstFreeSpot.")
    }
}

/**
 * Searches for free parking slot
 * If it finds one, it parks the car (creates object CarInfo)
 * @param registNum Registration number of a car
 * @param color Color of the car
 * @return Position where the car is parked (if returns 0, all slots are full)
 */
fun isFreeSlot(registNum: String, color: String): Int {
    var spot = 0
    for (i in parkSlotInfo.indices) {
        if (parkSlotInfo[i] == false) {
            spot = i + 1
            parkSlotInfo[i] = CarInfo(registNum, color)
            break
        }
    }
    return spot
}

/**
 * Deletes info about the car
 * If the isn't any, prints error
 */
fun leave() {
    val spot = sc.nextInt()
    if (parkSlotInfo[spot - 1] != false) {
        println("Spot $spot is free.")
        parkSlotInfo[spot - 1] = false
    } else {
        println("There is no car in the spot $spot.")
    }
}

/**
 * Prints all cars that are on the parking slots
 */
fun status() {
    var anyCar = false
    for (i in parkSlotInfo.indices) {
        if (parkSlotInfo[i] is CarInfo) {
            val obj: CarInfo = parkSlotInfo[i] as CarInfo
            println("${i + 1} ${obj.registNum} ${obj.color}")
            anyCar = true
        }
    }
    if (!anyCar) println("Parking lot is empty.")
}

class CarInfo(val registNum: String = "", val color: String = "")
