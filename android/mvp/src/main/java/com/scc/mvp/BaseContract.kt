package com.scc.mvp

/**
 * The base contract between a presenter and its related view.
 */
abstract class BaseContract {
    /**
     * Basic structure of the [Presenter] instances.
     */
    abstract class Presenter<T : View> {
        protected var shouldNotify: Boolean = false
        protected var view: T? = null
            get() = if (shouldNotify) field else null

        /**
         * Method used by the [View] to notify the [Presenter] that the UI is ready
         * for interactions or that the UI has been successfully moved to a loaded state.
         *
         * @see (Activity Lifecycle)[https://developer.android.com/guide/components/activities/activity-lifecycle]
         */
        fun subscribe() {
            this.shouldNotify = true
        }

        /**
         * Method used by the [View] to notify the [Presenter] that the UI has been updated
         * to a state in which changes processed by the [Presenter] should no longer update
         * the UI; this could happen when the application goes to background.
         *
         * @see (Activity Lifecycle)[https://developer.android.com/guide/components/activities/activity-lifecycle]
         */
        fun unsubscribe() {
            this.shouldNotify = false
        }

        /**
         * Method used to attach a [View] to the [Presenter] for notifying the view of any
         * required UI state updates.
         *
         * @param view to be notified about state changes
         */
        fun attach(view: T) {
            this.view = view
        }
    }

    /**
     * Basic structure of the [View] instances.
     */
    interface View
}