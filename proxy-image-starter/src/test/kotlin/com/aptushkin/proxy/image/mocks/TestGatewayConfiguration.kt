package com.aptushkin.proxy.image.mocks

import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestGatewayConfiguration {

    @Bean
    fun mModifyResponseBodyGatewayFilterFactory(): ModifyResponseBodyGatewayFilterFactory = mock(ModifyResponseBodyGatewayFilterFactory::class.java)
}