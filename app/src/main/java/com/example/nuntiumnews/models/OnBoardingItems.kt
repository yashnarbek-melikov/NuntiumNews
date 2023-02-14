package com.example.nuntiumnews.models

class OnBoardingItems {

    private var image: Int? = null

    constructor(image: Int?) {
        this.image = image
    }

    constructor()

    fun getImage(): Int? {
        return image
    }
}