package com.devsudal.sdk.addon.connection.buzzvil

data class BuzzProfile(
    val userId: String?,
    val gender: Gender?,
    val birth: Int?
) {
    enum class Gender {
        MALE,
        FEMALE
    }
}
