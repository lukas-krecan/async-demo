package net.javacrumbs.randomnumberservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RandomNumberServiceApplication

fun main(args: Array<String>) {
	runApplication<RandomNumberServiceApplication>(*args)
}
