package com.hong.mason.moverecoder.view

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.util.AndroidUtils

class CategoryFooterViewHolder(itemView: View, onTextChanged: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val editOther: EditText = itemView.findViewById(R.id.edit_other)
    private val buttonCheck: View = itemView.findViewById(R.id.button_check)

    init {
        editOther.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                onTextChanged.invoke(p0?.toString() ?: "")
            }
        })
    }

    fun bind(text: String, selected: Boolean) {
        if (selected) {
            editOther.visibility = View.VISIBLE
            editOther.requestFocus()
            AndroidUtils.showKeyboard(itemView.context, editOther)
        } else {
            editOther.visibility = View.GONE
            AndroidUtils.hideKeyboard(itemView.context, editOther)
            editOther.clearFocus()
        }
        editOther.setText(text)
        buttonCheck.isSelected = selected
    }
}