package net.javacrumbs.reactorbased

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactorBasedApplication

fun main(args: Array<String>) {
	runApplication<ReactorBasedApplication>(*args)
}
