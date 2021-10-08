package net.javacrumbs.threadbased

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThreadBasedApplication

fun main(args: Array<String>) {
	runApplication<ThreadBasedApplication>(*args)
}
