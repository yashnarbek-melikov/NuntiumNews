package com.example.nuntiumnews.utils

fun textToDate(text: String): String {
    val hour = text.substring(11, 16)
    val date = text.substring(8,10)
    val month = text.substring(5,7)
    val year = text.substring(0,4)

    return "$hour / $date.$month.$year"
}