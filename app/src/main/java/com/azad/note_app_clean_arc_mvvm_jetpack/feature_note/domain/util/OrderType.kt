package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
