package com.example.themoviedb.base

import android.os.Bundle
import androidx.annotation.LayoutRes

interface BaseContract {
    interface BaseView {

        val presenter: BasePresenter

        fun onViewReady(savedInstanceState: Bundle?)

        @LayoutRes
        fun getLayoutResourceId(): Int
    }

    interface BasePresenter {
        fun viewOnCreated()
        fun onViewDestroy()
    }

    interface BaseRepository
}