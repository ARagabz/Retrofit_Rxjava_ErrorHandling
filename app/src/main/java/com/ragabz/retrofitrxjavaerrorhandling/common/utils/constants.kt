package com.ragabz.retrofitrxjavaerrorhandling.common.utils

enum class NetworkTime(val value: Long) {
    CONNECTION(12 * 1000),
    READ(12 * 1000),
    WRITE(12 * 1000);
}