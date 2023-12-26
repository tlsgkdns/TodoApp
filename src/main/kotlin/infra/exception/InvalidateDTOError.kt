package infra.exception

data class InvalidateDTOError(val dto: String, val msg: String):
    RuntimeException("$dto has Invalid Value: $msg")