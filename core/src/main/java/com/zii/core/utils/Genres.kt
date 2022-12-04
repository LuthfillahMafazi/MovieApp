package com.zii.core.utils

import com.zii.core.main.domain.model.Genre

object Genres {
    private var genres = listOf<Genre>()

    fun getData() = genres
    fun setData(data: List<Genre>?) {
        data?.let { genres = data }
    }
}