package com.example.myapplication

import com.example.myapplication.BottomSheetNotificationsPM.*
import com.example.myapplication.MVP.KPresentationModel

class BottomSheetNotificationsPM : KPresentationModel<State, News>() {

    override val initialState: State
        get() = State(
            isLoading = false
        )



    data class State(
        val isLoading: Boolean
    )

    sealed class News {
        object Fail : News()
    }
}