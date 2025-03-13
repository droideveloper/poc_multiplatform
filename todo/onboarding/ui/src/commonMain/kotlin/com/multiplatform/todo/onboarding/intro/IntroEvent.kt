package com.multiplatform.todo.onboarding.intro

// TODO may be change this name to Notifications or Categories whichever is better fit in here
sealed interface IntroEvent {

    data object OnScreenViewed : IntroEvent
}
