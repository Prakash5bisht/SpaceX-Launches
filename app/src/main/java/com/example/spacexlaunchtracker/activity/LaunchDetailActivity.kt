package com.example.spacexlaunchtracker.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.R
import com.example.spacexlaunchtracker.databinding.ActivityLaunchDetailBinding
import com.example.spacexlaunchtracker.databinding.LayoutMediaLinkItemBinding
import com.example.spacexlaunchtracker.databinding.LayoutPayloadItemBinding
import com.example.spacexlaunchtracker.model.LaunchSite
import com.example.spacexlaunchtracker.model.Links
import com.example.spacexlaunchtracker.model.Payloads
import com.example.spacexlaunchtracker.model.RocketInfoResponse
import com.example.spacexlaunchtracker.model.SpaceXLaunchesResponse

class LaunchDetailActivity : AppCompatActivity() {
    private lateinit var launchDetailBinding: ActivityLaunchDetailBinding
    private var spaceXLaunchDetail: SpaceXLaunchesResponse?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchDetailBinding = ActivityLaunchDetailBinding.inflate(layoutInflater)
        setContentView(launchDetailBinding.root)
        fetchExtras()
        setAppBarTitle()
        setClickListeners()
        setUpUi()
    }

    private fun setClickListeners(){
        launchDetailBinding.appBar.root.setOnClickListener {
            finish()
        }
    }

    private fun setAppBarTitle(){
        launchDetailBinding.appBar.title.text = getString(R.string.launch_details)
    }

    private fun fetchExtras(){
        spaceXLaunchDetail = intent.getParcelableExtra<SpaceXLaunchesResponse>(Constants.SPACE_X_LAUNCH_DETAIL)
    }

    private fun setUpUi(){
        spaceXLaunchDetail?.let { launchDetail->
            launchDetailBinding.tvMissionName.text = launchDetail.missionName
            launchDetailBinding.tvLaunchDate.text = launchDetail.launchDate
            setRocketInfo(launchDetail.rocketInfo)
            setPayloadDetails(launchDetail.rocketInfo?.secondStage?.payloads)
            setLaunchSite(launchDetail.launchSite)
            setMediaLinks(launchDetail.links)
        }
    }

    private fun setRocketInfo(rocketInfo: RocketInfoResponse?) {
        rocketInfo?.let {
            launchDetailBinding.tvRocketName.text = it.rocketName
            launchDetailBinding.tvRocketType.text = it.rocketType
        }
    }

    private fun setPayloadDetails(payloadsList: List<Payloads>?){
        if(!payloadsList.isNullOrEmpty()){
            launchDetailBinding.tvPayloadDetails.text = "Payload Details"
            launchDetailBinding.llPayloadDetails.removeAllViews()
            payloadsList.forEachIndexed { index, payload->
                val payloadItemBinding = LayoutPayloadItemBinding.inflate(layoutInflater)
                if(!payload.payloadId.isNullOrEmpty()){
                    payloadItemBinding.tvPayloadId.text = "Payload Id:- ".plus(payload.payloadId)
                }else{
                    payloadItemBinding.tvPayloadId.isVisible = false
                }
                if(!payload.nationality.isNullOrEmpty()){
                    payloadItemBinding.tvPayloadNationality.text = "Nationality:- ".plus(payload.nationality)
                }else{
                    payloadItemBinding.tvPayloadNationality.isVisible = false
                }
                if(!payload.customers.isNullOrEmpty()){
                    val customers = payload.customers.joinToString(", ")
                    payloadItemBinding.tvPayloadCustomers.text = "Customers:- ".plus(customers)
                }else{
                    payloadItemBinding.tvPayloadCustomers.isVisible = false
                }
                if(!payload.manufacturer.isNullOrEmpty()){
                    payloadItemBinding.tvPayloadManufacturer.text = "Manufacturer:- ".plus(payload.manufacturer)
                }else{
                    payloadItemBinding.tvPayloadManufacturer.isVisible = false
                }
                if(!payload.payloadType.isNullOrEmpty()){
                    payloadItemBinding.tvPayloadType.text = "Type:- ".plus(payload.payloadType)
                }else{
                    payloadItemBinding.tvPayloadType.isVisible = false
                }
                if(!payload.payloadMassLbs.isNullOrEmpty()){
                    var mass = "Mass:- ".plus(payload.payloadMassLbs).plus(" Lbs")
                    if(!payload.payloadMassKg.isNullOrEmpty()){
                        mass = mass.plus("( ${payload.payloadMassKg} Kg)")
                    }
                    payloadItemBinding.tvPayloadMass.text = mass
                }else{
                    payloadItemBinding.tvPayloadMass.isVisible = false
                }
                if(!payload.orbit.isNullOrEmpty()){
                    payloadItemBinding.tvPayloadOrbit.text = "Orbit:- ".plus(payload.orbit)
                }else{
                    payloadItemBinding.tvPayloadOrbit.isVisible = false
                }
                if(index == payloadsList.lastIndex){
                    payloadItemBinding.sepLine.isVisible = false
                }
                launchDetailBinding.llPayloadDetails.addView(payloadItemBinding.root)
            }
        }else{
            launchDetailBinding.tvPayloadDetails.isVisible = false
        }
    }

    private fun setLaunchSite(launchSite: LaunchSite?) {
        if(launchSite != null && launchSite.siteName.isNullOrEmpty()){
            launchDetailBinding.tvLaunchSite.text = "Launch Site:- ".plus(launchSite.siteName?:"")
        }else{
            launchDetailBinding.tvLaunchSite.isVisible = false
        }
    }

    private fun setMediaLinks(links: Links?) {
        if(links!=null){
            launchDetailBinding.tvMediaLinksTitle.isVisible = true
            launchDetailBinding.tvMediaLinksTitle.text = getString(R.string.media_links)
            if(!links.missionPatch.isNullOrEmpty()){
                launchDetailBinding.tvMissionPatchLink.isVisible = true
                launchDetailBinding.tvMissionPatchLink.text = links.missionPatch
                setMediaLinkClickListener(launchDetailBinding.tvMissionPatchLink, links.missionPatch)
            }
            if(!links.redditMedia.isNullOrEmpty()){
                launchDetailBinding.tvRedditMediaLink.isVisible = true
                launchDetailBinding.tvRedditMediaLink.text = links.redditMedia
                setMediaLinkClickListener(launchDetailBinding.tvRedditMediaLink, links.redditMedia)
            }
            if(!links.articleLink.isNullOrEmpty()){
                launchDetailBinding.tvArticleLink.isVisible = true
                launchDetailBinding.tvArticleLink.text = links.articleLink
                setMediaLinkClickListener(launchDetailBinding.tvArticleLink, links.articleLink)
            }
            if(!links.wikipedia.isNullOrEmpty()){
                launchDetailBinding.tvWikipediaLink.isVisible = true
                launchDetailBinding.tvWikipediaLink.text = links.wikipedia
                setMediaLinkClickListener(launchDetailBinding.tvWikipediaLink, links.wikipedia)
            }
            if(!links.videoLink.isNullOrEmpty()){
                launchDetailBinding.tvVideoLink.isVisible = true
                launchDetailBinding.tvVideoLink.text = links.videoLink
                setMediaLinkClickListener(launchDetailBinding.tvVideoLink, links.videoLink)
            }
        }else{
            launchDetailBinding.tvMediaLinksTitle.isVisible = false
        }
    }

    private fun setMediaLinkClickListener(view:View, link: String){
        view.setOnClickListener {
            val mediaIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(mediaIntent)
        }
    }
}