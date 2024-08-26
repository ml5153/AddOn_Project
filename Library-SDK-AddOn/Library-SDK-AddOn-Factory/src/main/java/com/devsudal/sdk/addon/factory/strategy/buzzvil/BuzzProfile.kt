package com.devsudal.sdk.addon.factory.strategy.buzzvil

data class BuzzProfile(
    val userId: String?,
    val gender: Gender,
    val birth: Int
) {
    enum class Gender {
        MALE,
        FEMALE
    }
}
