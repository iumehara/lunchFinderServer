package io.umehara.lunchFinderServer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class LunchFinderServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(LunchFinderServerApplication::class.java, *args)
}
