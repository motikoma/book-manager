package com.book.manager.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

// SpringとRedisでのセッション管理を有効化
@EnableRedisHttpSession
class HttpSessionConfig {
    @Bean
    fun connectionFactory(): JedisConnectionFactory{
        val redisStandaloneConfiguration = RedisStandaloneConfiguration().also {
            it.hostName = "localhost"
            it.port = 6379
        }
        return JedisConnectionFactory(redisStandaloneConfiguration)
    }
}