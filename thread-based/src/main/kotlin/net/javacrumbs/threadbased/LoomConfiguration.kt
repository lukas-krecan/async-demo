package net.javacrumbs.threadbased

import org.eclipse.jetty.util.thread.ThreadPool
import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

//@Configuration
class LoomConfiguration {
    @Bean
    fun jettyCustomizer(): WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> {
        return WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> {
                factory -> factory.setThreadPool(LoomThreadPool())
        }
    }

    private class LoomThreadPool: ThreadPool {
        private val executor = Executors.newVirtualThreadExecutor()
        override fun execute(command: Runnable) {
            executor.execute(command)
        }

        override fun join() {
            executor.close()
        }

        override fun getThreads(): Int = Int.MAX_VALUE

        override fun getIdleThreads(): Int = Int.MAX_VALUE

        override fun isLowOnThreads(): Boolean = false
    }
}
