package com.example.myapplication.MVP

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

abstract class KPresentationModelDialog<STATE, PM : KPresentationModel<STATE, *>, VIEW_STATE, NEWS> :
    BottomSheetDialogFragment() {

    // binding lifecycle ///////////////////////////////////////////////////////////////////////
    protected lateinit var pm: PM

    private lateinit var disposable: CompositeDisposable

    open val stateMapper: ((STATE) -> VIEW_STATE)? = null

    override fun onResume() {
        super.onResume()

        disposable = CompositeDisposable()

        // state
        disposable += pm.subscribe {
            renderAndSave(
                if (stateMapper != null)
                    stateMapper!!(it)
                else it as VIEW_STATE
            )
        }

        // news
        disposable += pm.news
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { news ->
                receiveNews(news as NEWS)
            }
    }

    override fun onPause() {
        super.onPause()

        disposable.dispose()
    }


    // state ///////////////////////////////////////////////////////////////////////////////////

    protected var lastState: VIEW_STATE? = null
        private set

    private fun renderAndSave(state: VIEW_STATE) {
        render(state)
        lastState = state
    }

    open fun render(state: VIEW_STATE): VIEW_STATE = state

    // news ////////////////////////////////////////////////////////////////////////////////////

    open fun receiveNews(news: NEWS) {}
}
