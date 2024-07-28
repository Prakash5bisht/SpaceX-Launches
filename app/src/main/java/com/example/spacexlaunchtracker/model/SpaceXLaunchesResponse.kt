package com.example.spacexlaunchtracker.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpaceXLaunchesResponse(
    @SerializedName("flight_number")
    val flightNumber: String? = "",

    @SerializedName("mission_name")
    val missionName: String? = "",

    @SerializedName("launch_year")
    val launchYear: String? = "",

    @SerializedName("launch_date_utc")
    val launchDate: String? = "",

    @SerializedName("rocket")
    val rocketInfo: RocketInfoResponse? = null,

    @SerializedName("launch_site")
    val launchSite: LaunchSite? = null,

    @SerializedName("links")
    val links: Links? = null
):Parcelable

@Parcelize
data class RocketInfoResponse(
    @SerializedName("rocket_name")
    val rocketName: String? = "",

    @SerializedName("rocket_type")
    val rocketType: String? = "",

    @SerializedName("second_stage")
    val secondStage: SecondStage? = null,
) : Parcelable

@Parcelize
data class SecondStage(
    @SerializedName("payloads")
    val payloads: List<Payloads>? = emptyList(),
) : Parcelable

@Parcelize
data class Payloads(
    @SerializedName("payload_id")
    val payloadId: String? = "",
    @SerializedName("customers")
    val customers: List<String>? = emptyList(),
    @SerializedName("nationality")
    val nationality: String? = "",
    @SerializedName("manufacturer")
    val manufacturer: String? = "",
    @SerializedName("payload_type")
    val payloadType: String? = "",
    @SerializedName("payload_mass_kg")
    val payloadMassKg: String? = "",
    @SerializedName("payload_mass_lbs")
    val payloadMassLbs: String? = "",
    @SerializedName("orbit")
    val orbit: String? = "",
) : Parcelable

@Parcelize
data class LaunchSite(
    @SerializedName("site_name_long")
    val siteName: String? = "",
) : Parcelable

@Parcelize
data class Links(
    @SerializedName("mission_patch")
    val missionPatch: String? = "",
    @SerializedName("reddit_media")
    val redditMedia: String? = "",
    @SerializedName("article_link")
    val articleLink: String? = "",
    @SerializedName("wikipedia")
    val wikipedia: String? = "",
    @SerializedName("video_link")
    val videoLink: String? = "",
) : Parcelable
