package io.fiap.fastfood.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*


@EnableReactiveMongoRepositories(basePackages = ["io.fiap.fastfood.infrastructure.repository.**"])
@Configuration
class MongoConfiguration {
    @Bean
    fun customConversions(): MongoCustomConversions {
        val converters: MutableList<Converter<*, *>?> = ArrayList()
        converters.add(DateToLocalDateConverter.INSTANCE)
        converters.add(LocalDateToDateConverter.INSTANCE)
        converters.add(LocalDateTimeToDateConverter.INSTANCE)
        converters.add(DateToLocalDateTimeConverter.INSTANCE)
        return MongoCustomConversions(converters)
    }

    internal enum class DateToLocalDateConverter : Converter<Date, LocalDate> {
        INSTANCE;

        override fun convert(source: Date): LocalDate {
            return LocalDate.ofInstant(source.toInstant(), ZoneId.of("Z"))
        }
    }

    internal enum class LocalDateToDateConverter : Converter<LocalDate, Date> {
        INSTANCE;

        override fun convert(source: LocalDate): Date {
            return Date.from(source.atStartOfDay().atZone(ZoneId.of("Z")).toInstant())
        }
    }

    internal enum class LocalDateTimeToDateConverter : Converter<LocalDateTime, Date> {
        INSTANCE;

        override fun convert(source: LocalDateTime): Date {
            return Date.from(source.toInstant(ZoneOffset.UTC))
        }
    }

    internal enum class DateToLocalDateTimeConverter : Converter<Date, LocalDateTime> {
        INSTANCE;

        override fun convert(source: Date): LocalDateTime {
            return LocalDateTime.ofInstant(source.toInstant(), ZoneId.of("Z"))
        }
    }

}