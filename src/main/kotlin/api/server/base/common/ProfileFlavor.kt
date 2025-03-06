package api.server.base.common

enum class ProfileFlavor(val env: String) {
    LOCAL("local"),
    DEV("dev"),
    STG("stage"),
    PROD("prod");
}