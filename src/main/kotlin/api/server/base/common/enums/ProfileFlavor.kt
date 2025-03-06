package api.server.base.common.enums

enum class ProfileFlavor(val env: String) {
    LOCAL("local"),
    DEV("dev"),
    STG("stage"),
    PROD("prod");
}