package com.devsudal.sdk.addon.buzzvil

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.buzzvil.buzzad.benefit.BuzzAdBenefitConfig
import com.buzzvil.buzzad.benefit.core.ad.AdError
import com.buzzvil.buzzad.benefit.core.models.UserProfile
import com.buzzvil.buzzad.benefit.presentation.feed.BuzzAdFeed
import com.buzzvil.buzzad.benefit.presentation.feed.FeedConfig
import com.buzzvil.sdk.BuzzvilSdk
import com.buzzvil.sdk.BuzzvilSetUserProfileListener
import com.devsudal.sdk.addon.connection.AddOnInitListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzAddOnConnectListener
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile

class BuzzAddOn : AppCompatActivity(), BuzzAddOnConnectListener {

    companion object {
        const val NAME = "BuzzAddO"
    }


    override fun init(
        application: Application,
        initLitener: AddOnInitListener
    ) {
        Log.e(NAME, "$NAME -> init")

        // Feed(베네핏허브) 설정
        val feedConfig = FeedConfig.Builder("343494563809189")
            .build()

        // BuzzBenefit 설정
        val buzzAdBenefitConfig = BuzzAdBenefitConfig.Builder("app-pub-166340565595997")
            .setDefaultFeedConfig(feedConfig)
            .build()

        // Buzzvil SDK 초기화
        BuzzvilSdk.initialize(
            application = application,
            buzzAdBenefitConfig = buzzAdBenefitConfig
        )


        try {
            initLitener.onSuccess()
        } catch (e: Exception) {
            initLitener.onFailure()
        }
    }

    override fun setUserProfile(profile: BuzzProfile) {
        Log.e(NAME, "$NAME -> setUserProfile { profile: $profile }")

        BuzzvilSdk.setUserProfile(
            userId = profile.userId,
            gender = when (profile.gender) {
                BuzzProfile.Gender.MALE -> UserProfile.Gender.MALE
                BuzzProfile.Gender.FEMALE -> UserProfile.Gender.FEMALE
                else -> UserProfile.Gender.FEMALE
            },
            birthYear = profile.birth ?: 1987,
            // (선택) 로그인 상태를 확인할 수 있는 리스너를 등록합니다.
            listener = object : BuzzvilSetUserProfileListener {
                override fun loggedIn() {
                    // 유저 정보가 정상적으로 등록된 경우 호출됩니다.
                    Log.e("asd", "$NAME -> setUserProfile::loggedIn")
                }

                override fun loggedOut() {
                    // 유저 정보를 삭제하는 경우 호출됩니다.
                    Log.e("asd", "$NAME -> setUserProfile::loggedOut")
                }

                override fun onSessionReady() {
                    // loggedIn() 이후에 버즈빌 서버에서 auth token을 정상적으로 받아오면 호출됩니다.
                    Log.e("asd", "$NAME -> setUserProfile::onSessionReady")
                }

                override fun onFailure(errorType: BuzzvilSetUserProfileListener.ErrorType) {
                    // 유저 정보를 정상적으로 등록하지 못한 경우 호출됩니다.
                    Log.e("asd", "$NAME -> setUserProfile::onFailure { errorType: ${errorType.name} ")

                }
            }
        )
    }

    override fun loadAD(activity: Activity) {
        Log.e(NAME, "$NAME -> loadAD")
        val buzzAdFeed = BuzzAdFeed.Builder().build()
        buzzAdFeed.load(object : BuzzAdFeed.FeedLoadListener {
            override fun onSuccess() {
                // 광고 재할당에 성공한 경우 호출됩니다.
                Log.e("asd", "$NAME -> loadAD -> buzzAdFeed.load::onSuccess")
                buzzAdFeed.show(activity)
            }

            override fun onError(error: AdError?) {
                // 광고 재할당에 실패한 경우 호출됩니다.
                Log.e("asd", "$NAME -> loadAD -> buzzAdFeed.load::onError { errorType: ${error?.adErrorType}, message: ${error?.message} }")
                buzzAdFeed.show(activity)
            }
        })
    }
}

