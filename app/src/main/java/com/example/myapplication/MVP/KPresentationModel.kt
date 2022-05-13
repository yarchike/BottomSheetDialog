package com.example.myapplication.MVP

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class KPresentationModel<STATE, NEWS>  : Disposable {

    // state

    private val _stateRelay by lazy {
        BehaviorRelay.create<STATE>().apply {
            accept(initialState)
        }
    }

    protected val _state: STATE
        get() = _stateRelay.value!!

    // used by children
    protected val _disposables = CompositeDisposable()

    abstract val initialState: STATE

    protected fun changeState(newState: STATE) {
        if (newState != _state)
            _stateRelay.accept(newState)
    }

    private var isBooted = false

    fun subscribe(
        observeOnScheduler: Scheduler = AndroidSchedulers.mainThread(),
        onNext: (STATE) -> Unit
    ): Disposable {
        if (!isBooted) {
            isBooted = true
            bootstrap()
        }

        return _stateRelay
            .observeOn(observeOnScheduler)
            .subscribe(onNext)
    }

    open fun bootstrap() {}

    // news

    val news: Observable<NEWS> = PublishRelay.create<NEWS>()

    protected val _newsRelay: PublishRelay<NEWS> = news as PublishRelay<NEWS>

    // Disposable //////////////////////////////////////////////////////////////////////////////

    override fun dispose() {
        _disposables.dispose()
    }

    override fun isDisposed(): Boolean = _disposables.isDisposed
}
