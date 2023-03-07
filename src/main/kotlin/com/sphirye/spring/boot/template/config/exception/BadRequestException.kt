package com.sphirye.lonjas.config.exception

import java.lang.RuntimeException

class BadRequestException: RuntimeException {
    constructor(message: String?): super(message)
    constructor(): super("BadRequest")
}