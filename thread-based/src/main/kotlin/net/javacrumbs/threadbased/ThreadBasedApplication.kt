package net.javacrumbs.threadbased

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class ThreadBasedApplication

fun main(args: Array<String>) {
	runApplication<ThreadBasedApplication>(*args)
}

@Configuration
class WebConfig: WebMvcConfigurer {
	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(RequestIdInterceptor)
	}
}
