package com.example.scoutv1

data class Item(
    var itemId: String="",
    var name: String? = null,
    var itemPrice: String? = null,
    var imageLink: String? = null,
    var description: String? = null,
    var phoneNumber: String? = null,
    var userMail: String? = null,
    var itemCategory: String? = null
)