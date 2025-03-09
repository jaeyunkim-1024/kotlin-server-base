package api.server.base.common.model

open class CustomException: Exception {
    val resultCode: CustomResultCode

    constructor(resultCode: CustomResultCode, cause: Throwable? = null)
            : this(resultCode, resultCode.name, cause, null)

    constructor(
        resultCode: CustomResultCode,
        message: String,
        additionalInfo: Map<String, Any>? = null,
    ) : this(resultCode, message, null, additionalInfo)

    constructor(
        resultCode: CustomResultCode,
        cause: Throwable,
        additionalInfo: Map<String, Any>? = null,
    ) : this(resultCode, resultCode.name, cause, additionalInfo)

    constructor(
        resultCode: CustomResultCode,
        message: String,
        cause: Throwable?,
        additionalInfo: Map<String, Any>? = null,
    ) : super(
        resultCode.name + " | " + message + if (additionalInfo == null) "" else " | $additionalInfo",
        cause,
    ) {
        this.resultCode = resultCode
    }
}