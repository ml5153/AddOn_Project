package com.devsudal.sdk.addon.buzzvil

import com.buzzvil.buzzad.benefit.core.models.UserProfile

data class BuzzUser(
    val user: String,
    val gender: UserProfile.Gender,
    val birthYear: Int,

)
