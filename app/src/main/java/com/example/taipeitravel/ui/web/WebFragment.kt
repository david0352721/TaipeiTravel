package com.example.taipeitravel.ui.web

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.taipeitravel.R
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
        binding.webWebView.settings.javaScriptEnabled = true
        binding.webWebView.webViewClient = WebViewClient()
        binding.webWebView.webChromeClient = WebChromeClient()

        viewModel.viewOfficialSite.value = officialWebUrl
        viewModel.observeViewOfficialSite().observe(viewLifecycleOwner) {
            binding.webWebView.loadUrl(it)
        }

    }

}