package com.lkw1120.currency.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lkw1120.currency.R
import com.lkw1120.currency.databinding.FragmentMainBinding
import com.lkw1120.currency.ui.adapter.SpinnerAdapter
import com.lkw1120.currency.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribeUi()
    }

    private fun initView() {
        binding.apply {

            adapter = SpinnerAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrayListOf()
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val currencyInfo = adapter.getItem(position)
                    viewModel.setReceiverCountry(currencyInfo)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            etRemittanceContent.addTextChangedListener(object: TextWatcher {
                private var editText: String = ""
                override fun afterTextChanged(
                    s: Editable?
                ) {
                    s?.let { editable ->
                        val money = if (editable.isNotBlank()) {
                            editable.toString().replace(",","").toDouble()
                        }
                        else {
                            0.0
                        }
                        viewModel.setRemittance(money)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val newText = s?.toString() ?: ""
                    if (!TextUtils.isEmpty(newText) && !TextUtils.equals(editText, newText)) {
                        val money = newText.replace(",","").toInt()
                        val df = DecimalFormat("#,##0")
                        editText = df.format(money)
                        etRemittanceContent.apply {
                            setText(editText)
                            setSelection(text.length)
                        }
                    }
                }
            })
        }
    }

    private fun subscribeUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCountryInfo()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.senderCountryStateFlow.collectLatest {
                binding.tvSenderCountryContent.text =
                    resources.getString(R.string.form_country_and_code, it.country, it.code)
                binding.tvRemittanceCurrencyCode.text = it.code
                viewModel.setExchangeRateCode()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.receiverCountryStateFlow.collectLatest {
                binding.tvReceiverCountryContent.text =
                    resources.getString(R.string.form_country_and_code, it.country, it.code)
                viewModel.getExchangeRate()
                viewModel.setExchangeRateCode()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.countryInfoStateFlow.collectLatest {
                adapter.run {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.exchangeRateStateFlow.collectLatest {
                val df = DecimalFormat("#,##0.00")
                binding.tvExchangeRateContent.text = df.format(it)
                viewModel.calculateRemittance()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.exchangeRateCodeStateFlow.collectLatest {
                val codes = it.split("/")
                binding.tvExchangeRateCode.text =
                    resources.getString(R.string.form_exchange_rate_code,codes[0],codes[1])
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.timestampStateFlow.collectLatest {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                binding.tvTimestampContent.text = sdf.format(it*1000)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.remittanceStateFlow.collectLatest {
                viewModel.calculateRemittance()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.amountReceivedStateFlow.collectLatest {
                val amountReceived = it.split(" ")
                val remittance: Int = viewModel.remittanceStateFlow.value.toInt()
                val df = DecimalFormat("#,##0.00")
                val max = resources.getInteger(R.integer.remittance_max)
                val min = resources.getInteger(R.integer.remittance_min)
                binding.tvAmountReceived.text =
                    if(remittance in min..max) {
                        resources.getString(
                            R.string.form_amount_received,
                            df.format(amountReceived[0].toDouble()),
                            amountReceived[1]
                        )
                    }
                    else {
                        resources.getText(R.string.error_remittance_is_invalid)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}