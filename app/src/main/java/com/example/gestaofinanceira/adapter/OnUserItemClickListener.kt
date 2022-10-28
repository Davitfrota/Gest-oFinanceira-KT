package com.example.gestaofinanceira.adapter

import android.view.View


interface OnUserItemClickListener {

    fun onClick(view : View, position: Int)

    fun onLongClick(view : View, position: Int)
}