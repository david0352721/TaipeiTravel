package com.example.taipeitravel.ui.web

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.taipeitravel.MainActivity
import com.example.taipeitravel.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding
    private lateinit var viewModel: WebViewModel
    private val args: WebFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[WebViewModel::class.java]
        val officialWebUrl = args.webOfficialSite
        binding.webWebView.settings.useWideViewPort = true
        binding.webWebView.settings.loadWithOverviewMode = true
        binding.webWebView.settings.setSupportZoom(true)
        binding.webWebView.settings.javaScriptEnabled = true
        binding.webWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                (requireActivity() as MainActivity).supportActionBar?.title = view?.title
            }
        }
        binding.webWebView.webChromeClient = WebChromeClient()

        viewModel.viewOfficialSite.value = officialWebUrl
        viewModel.observeViewOfficialSite().observe(viewLifecycleOwner) {
            binding.webWebView.loadUrl(it)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.webWebView.onResume()
        binding.webWebView.resumeTimers()
        binding.webWebView.setOnKeyListener { _, i, _ ->
            if (i == KeyEvent.KEYCODE_BACK && binding.webWebView.canGoBack()) {
                binding.webWebView.goBack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onPause() {
        super.onPause()
        binding.webWebView.onPause()
        binding.webWebView.pauseTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        val viewGroup: ViewGroup = binding.webWebView.parent as ViewGroup
        viewGroup.removeView(binding.webWebView)
        binding.webWebView.destroy()
    }

}