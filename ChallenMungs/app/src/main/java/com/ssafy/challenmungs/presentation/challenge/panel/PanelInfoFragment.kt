package com.ssafy.challenmungs.presentation.challenge.panel

import androidx.fragment.app.activityViewModels
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentPanelInfoBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PanelInfoFragment :
    BaseFragment<FragmentPanelInfoBinding>(R.layout.fragment_panel_info) {

    private val panelPlayViewModel by activityViewModels<PanelPlayViewModel>()

    override fun initView() {
        setBind()
    }

    private fun setBind() {
        panelPlayViewModel.challengeInfo.observe(viewLifecycleOwner) {
            binding.info = it
        }

        binding.toolbar.ivBack.setOnClickListener {
            popBackStack()
        }
    }
}