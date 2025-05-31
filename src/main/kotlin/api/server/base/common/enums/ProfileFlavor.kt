package api.server.base.common.enums

enum class ProfileFlavor(val env: String) {
    TEST("test"),
    LOCAL("local"),
    DEV("dev"),
    STG("stage"),
    PROD("prod");
}