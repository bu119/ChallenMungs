package com.ssafy.challenmungs.presentation.challenge.panel

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.ssafy.challenmungs.R
import com.ssafy.challenmungs.databinding.FragmentPanelCreateBinding
import com.ssafy.challenmungs.presentation.base.BaseFragment
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialog
import com.ssafy.challenmungs.presentation.common.CustomSimpleDialogInterface
import com.ssafy.challenmungs.presentation.common.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PanelCreateFragment :
    BaseFragment<FragmentPanelCreateBinding>(R.layout.fragment_panel_create),
    CustomSimpleDialogInterface, PlayAreaSettingInterface {

    private val panelCreateViewModel by viewModels<PanelCreateViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var centerLat: Double = 0.0
    private var centerLng: Double = 0.0
    private var mapSize: Double = 1.0
    private var cellSize: Double = 0.05

    private lateinit var checkValue: Pair<Boolean, String>
    private var headCountStep = 2
    private var defHeadCountValue = 4
    private var minHeadCountValue = 2
    private var maxHeadCountValue = 6
    private var curHeadCountValue = defHeadCountValue
    private var participationMoneyStep = 10
    private var defParticipationMoneyValue = 10
    private var minParticipationMoneyValue = 10
    private var maxParticipationMoneyValue = 100
    private var curParticipationMoneyValue = defParticipationMoneyValue

    override fun initView() {
        setBind()
        initListener()
    }

    private fun setBind() {
        binding.toolbar.title = getString(R.string.title_panel_play_create)

        panelCreateViewModel.maxParticipantCount.observe(viewLifecycleOwner) {
            binding.tvMaxHeadcountContent.text = it.toString()
        }

        panelCreateViewModel.title.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.etTitle.text.clear()
        }

        panelCreateViewModel.entryFee.observe(viewLifecycleOwner) {
            binding.tvParticipationMoneyAmount.text = it.toString()
        }

        panelCreateViewModel.isAreaSetting.observe(viewLifecycleOwner) {
            binding.btnPlayAreaSet.text =
                if (!it) getString(R.string.content_not_set) else getString(R.string.content_set)
        }

        panelCreateViewModel.gameType.observe(viewLifecycleOwner) {
            if (it == 1) {
                binding.btnIndividual.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bg_rect_golden_glow_golden_poppy_radius5_stroke1
                )
                binding.btnTeam.background =
                    ContextCompat.getDrawable(requireContext(), R.color.white_smoke)
            } else {
                binding.btnIndividual.background =
                    ContextCompat.getDrawable(requireContext(), R.color.white_smoke)
                binding.btnTeam.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.bg_rect_golden_glow_golden_poppy_radius5_stroke1
                )
            }
        }

        panelCreateViewModel.startDate.observe(viewLifecycleOwner) {
            binding.tvDateContent.apply {
                when (it) {
                    "" -> {
                        text = getString(R.string.content_choice_date)
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.suva_grey))
                    }
                    else -> {
                        text = getString(
                            R.string.content_period,
                            it,
                            panelCreateViewModel.endDate.value
                        )
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initListener() {
        val constrainBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
        val builder = MaterialDatePicker.Builder.dateRangePicker().apply {
            setCalendarConstraints(constrainBuilder.build())
            setTitleText(getString(R.string.title_select_date))
        }
        val picker = builder.build().apply {
            addOnPositiveButtonClickListener {
                val startDateInMillis = it.first
                val endDateInMillis = it.second
                val mStartDate = Date(startDateInMillis)
                val mEndDate = Date(endDateInMillis)
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

                panelCreateViewModel.setEndDate(simpleDateFormat.format(mEndDate))
                panelCreateViewModel.setStartDate(simpleDateFormat.format(mStartDate))
            }
            addOnNegativeButtonClickListener { this.dismiss() }
        }
        val openCalendarListener = View.OnClickListener {
            picker.show(childFragmentManager, "datePicker")
        }
        val areaSettingDialog = PlayAreaSettingDialog(requireContext(), this@PanelCreateFragment)

        binding.apply {
            val typeClickListener = View.OnClickListener {
                if (it == btnIndividual) {
                    panelCreateViewModel.setGameType(1)
                } else {
                    panelCreateViewModel.setGameType(2)
                }
            }

            btnCalendar.setOnClickListener(openCalendarListener)
            tvDateContent.setOnClickListener(openCalendarListener)
            btnIndividual.setOnClickListener(typeClickListener)
            btnTeam.setOnClickListener(typeClickListener)

            toolbar.ivBack.setOnClickListener {
                popBackStack()
            }

            btnOk.setOnClickListener {
                checkValue = checkContentEmpty()
                createCheck(checkValue.first, checkValue.second)
            }

            btnPlus.setOnClickListener {
                curHeadCountValue += headCountStep
                curHeadCountValue = checkRange(
                    tvMaxHeadcountContent,
                    curHeadCountValue,
                    minHeadCountValue,
                    maxHeadCountValue,
                    clMaxHeadcountArea
                )
                panelCreateViewModel.setMaxParticipantCount(curHeadCountValue)
            }

            btnMinus.setOnClickListener {
                curHeadCountValue -= headCountStep
                curHeadCountValue = checkRange(
                    tvMaxHeadcountContent,
                    curHeadCountValue,
                    minHeadCountValue,
                    maxHeadCountValue,
                    clMaxHeadcountArea
                )
                panelCreateViewModel.setMaxParticipantCount(curHeadCountValue)
            }

            btnParticipationMoneyPlus.setOnClickListener {
                curParticipationMoneyValue += participationMoneyStep
                curParticipationMoneyValue = checkRange(
                    tvParticipationMoneyAmount,
                    curParticipationMoneyValue,
                    minParticipationMoneyValue,
                    maxParticipationMoneyValue,
                    clParticipationMoneyArea
                )
                panelCreateViewModel.setEntryFee(curParticipationMoneyValue)
            }

            btnParticipationMoneyMinus.setOnClickListener {
                curParticipationMoneyValue -= participationMoneyStep
                curParticipationMoneyValue = checkRange(
                    tvParticipationMoneyAmount,
                    curParticipationMoneyValue,
                    minParticipationMoneyValue,
                    maxParticipationMoneyValue,
                    clParticipationMoneyArea
                )
                panelCreateViewModel.setEntryFee(curParticipationMoneyValue)
            }

            btnPlayAreaSet.setOnClickListener {
                areaSettingDialog.show(childFragmentManager, "areaSettingDialog")
            }

            btnReset.setOnClickListener {
                setReset()
            }

            toolbar.ivBack.setOnClickListener {
                popBackStack()
            }
        }
    }

    private fun setReset() {
        panelCreateViewModel.apply {
            setIsAreaSetting(false)
            setEndDate("")
            setStartDate("")
            setTitle("")
            setGameType(1)
            setEntryFee(defParticipationMoneyValue)
            setMaxParticipantCount(defHeadCountValue)
        }
        centerLat = 0.0
        centerLng = 0.0
        curParticipationMoneyValue = defParticipationMoneyValue
        curHeadCountValue = defHeadCountValue
    }

    private fun checkRange(
        numberView: TextView,
        curValue: Int,
        minValue: Int,
        maxValue: Int,
        shakeView: ConstraintLayout
    ): Int {
        when {
            curValue in minValue..maxValue -> {
                numberView.text = curValue.toString()
                return curValue
            }
            curValue < minValue -> {
                numberView.text = minValue.toString()
                val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
                shakeView.startAnimation(shakeAnimation)
                return minValue
            }
            else -> {
                numberView.text = maxValue.toString()
                val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
                shakeView.startAnimation(shakeAnimation)
                return maxValue
            }
        }
    }

    private fun createCheck(checkBoolean: Boolean, checkMessage: String) {
        val dialog = CustomSimpleDialog(
            requireContext(),
            this,
            !checkBoolean,
            checkMessage,
            getString(R.string.content_check)
        )
        dialog.show()
    }

    private fun checkContentEmpty(): Pair<Boolean, String> {
        binding.apply {
            if (etTitle.text.isEmpty()) {
                etTitle.error = getString(R.string.content_enter_title)
                return Pair(false, getString(R.string.content_enter_title))
            } else
                panelCreateViewModel.setTitle(etTitle.text.toString())

            if (panelCreateViewModel.startDate.value?.isEmpty() == true || panelCreateViewModel.endDate.value?.isEmpty() == true) {
                return Pair(false, getString(R.string.content_choice_date))
            }

            if (panelCreateViewModel.isAreaSetting.value != true)
                return Pair(false, getString(R.string.content_play_area_setting_message))

            return Pair(true, getString(R.string.content_create_check_message))
        }
    }

    override fun onPositiveButton() {
        if (checkValue.first) {
            lifecycleScope.launch {
                val result = panelCreateViewModel.createPanelChallenge(
                    centerLat,
                    centerLng,
                    mapSize,
                    cellSize
                )
                if (result) {
                    createCheck(false, getString(R.string.content_create_success))
                    popBackStack()
                    // 챌린지 탭으로 status 섩정하는 코드 필요
                    mainViewModel.setStatus(0)
                } else {
                    createCheck(false, getString(R.string.content_create_failed))
                    checkValue = Pair(false, "")
                }
            }
        }
    }

    override fun setArea(location: LatLng) {
        centerLat = location.latitude
        centerLng = location.longitude
        panelCreateViewModel.setIsAreaSetting(true)
    }
}