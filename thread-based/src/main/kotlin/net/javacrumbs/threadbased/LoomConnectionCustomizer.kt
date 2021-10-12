package net.javacrumbs.threadbased

import org.apache.catalina.Executor
import org.apache.catalina.LifecycleListener
import org.apache.catalina.LifecycleState
import org.apache.catalina.connector.Connector
import org.apache.coyote.AbstractProtocol
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@Component
class LoomConnectionCustomizer: TomcatConnectorCustomizer {
    override fun customize(connector: Connector) {
        val tomcatService = connector.service
        val executor = createExecutor()
        tomcatService.addExecutor(executor)

        val handler = connector.protocolHandler
        if (handler is AbstractProtocol<*>) {
            handler.executor = executor
        }
    }

    private fun createExecutor(): Executor {
        return object: Executor {
            private val virtualPool = Executors.newVirtualThreadExecutor()
            override fun execute(command: Runnable?, timeout: Long, unit: TimeUnit?) {
                TODO("Not yet implemented")
            }

            override fun execute(command: Runnable) {
                virtualPool.execute(command)
            }

            override fun addLifecycleListener(listener: LifecycleListener?) {
                TODO("Not yet implemented")
            }

            override fun findLifecycleListeners(): Array<LifecycleListener> {
                TODO("Not yet implemented")
            }

            override fun removeLifecycleListener(listener: LifecycleListener?) {
                TODO("Not yet implemented")
            }

            override fun init() {
            }

            override fun start() {
            }

            override fun stop() {
                virtualPool.close()
            }

            override fun destroy() {
            }

            override fun getState(): LifecycleState {
                TODO("Not yet implemented")
            }

            override fun getStateName(): String {
                TODO("Not yet implemented")
            }

            override fun getName(): String = "loom-executor"
        }
    }
}
