package net.javacrumbs.reactorbased

import org.springframework.web.reactive.function.client.WebClient

val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()
