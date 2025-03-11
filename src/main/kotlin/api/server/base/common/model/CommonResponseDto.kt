package api.server.base.common.model

import org.springframework.http.HttpStatus

class CommonResponseDto<T>(
    val resultCode: String = HttpStatus.OK.value().toString(),
    val message: String? = null,
    val data: T? = null,
)