package com.example.spacexlaunchtracker.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.example.spacexlaunchtracker.Constants
import com.example.spacexlaunchtracker.R
import com.example.spacexlaunchtracker.databinding.FragmentSpaceXStoreBinding

class SpaceXStoreFragment : Fragment() {
    private lateinit var spaceXStoreBinding: FragmentSpaceXStoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        spaceXStoreBinding = FragmentSpaceXStoreBinding.inflate(inflater, container, false)
        return spaceXStoreBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
        loadWebView()
    }

    private fun setUpWebView() {
        spaceXStoreBinding.webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = false
            allowContentAccess = false
        }
        addWebViewClient()
    }

    private fun addWebViewClient() {
        spaceXStoreBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return if (request?.url != null && request.url.equals(Constants.STORE_URL)) {
                    false
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, request?.url)
                    startActivity(intent)
                    true
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                spaceXStoreBinding.circularProgress.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                spaceXStoreBinding.circularProgress.isVisible = false
            }
        }
    }

    private fun loadWebView() {
        spaceXStoreBinding.webView.loadUrl(Constants.STORE_URL)
    }

    companion object {
        fun newInstance() = SpaceXStoreFragment()
    }
}