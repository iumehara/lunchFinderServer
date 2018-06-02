package io.umehara.lunchFinderServer.restutils

class BadRequestException(val errorMessage: ExceptionMessage): RuntimeException()

enum class ExceptionMessage {
    DUPLICATE_KEY_EXCEPTION
}