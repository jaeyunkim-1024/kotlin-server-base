package api.server.base.common.model

open class CommonException: Exception {
    val resultCode: CommonResultCode

    constructor(resultCode: CommonResultCode, cause: Throwable? = null)
            : this(resultCode, resultCode.name, cause, null)

    constructor(
        resultCode: CommonResultCode,
        message: String,
        additionalInfo: Map<String, Any>? = null,
    ) : this(resultCode, message, null, additionalInfo)

    constructor(
        resultCode: CommonResultCode,
        cause: Throwable,
        additionalInfo: Map<String, Any>? = null,
    ) : this(resultCode, resultCode.name, cause, additionalInfo)

    constructor(
        resultCode: CommonResultCode,
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