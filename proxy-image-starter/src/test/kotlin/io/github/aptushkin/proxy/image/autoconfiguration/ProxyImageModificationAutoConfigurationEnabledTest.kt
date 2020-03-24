package io.github.aptushkin.proxy.image.autoconfiguration

import io.github.aptushkin.proxy.image.ImageModificationConfiguration
import io.github.aptushkin.proxy.image.mocks.TestGatewayConfiguration
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes =  [ProxyImageModificationAutoConfiguration::class, TestGatewayConfiguration::class])
@TestPropertySource(
        properties = [
            "proxy.image.enabled = true"
        ]
)
internal class ProxyImageModificationAutoConfigurationEnabledTest {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Test
    fun contextInitializedSuccessfully() {
        assertDoesNotThrow { applicationContext.getBean(ImageModificationConfiguration::class.java) }
    }
}

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes =  [ProxyImageModificationAutoConfiguration::class, TestGatewayConfiguration::class])
@TestPropertySource(
        properties = [
            "proxy.image.enabled = false"
        ]
)
internal class ProxyImageModificationAutoConfigurationDisabledTest {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Test
    fun contextInitializedSuccessfully() {
        assertThrows(NoSuchBeanDefinitionException::class.java) {
            applicationContext.getBean(ImageModificationConfiguration::class.java)
        }
    }
}