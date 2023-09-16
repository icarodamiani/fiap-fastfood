package io.fiat.fastfood.configuration.exception

class TechnicalException(message: String? = null, err: Throwable? = null) : RuntimeException(message, err)

class BusinessException(message: String? = null, err: Throwable? = null) : RuntimeException(message, err)

class ConflictException(message: String? = null, err: Throwable? = null) : RuntimeException(message, err)

class UnavailableException(message: String? = null, err: Throwable? = null) : RuntimeException(message, err)

