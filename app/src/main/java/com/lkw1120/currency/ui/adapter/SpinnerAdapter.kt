package com.lkw1120.currency.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.lkw1120.currency.R
import com.lkw1120.currency.usecase.model.CountryInfo

class SpinnerAdapter(
    context: Context,
    resId: Int,
    private val values: ArrayList<CountryInfo>
) : ArrayAdapter<CountryInfo>(context, resId, values) {

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): CountryInfo {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        return label.apply {
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            text = context.getString(
                R.string.form_country_and_code,
                values[position].country,
                values[position].code
            )
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        return label.apply {
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            text = context.getString(
                R.string.form_country_and_code,
                values[position].country,
                values[position].code
            )
        }
    }

}