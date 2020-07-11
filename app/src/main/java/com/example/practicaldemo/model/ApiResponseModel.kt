package com.example.practicaldemo.model


data class ApiResponseModel(
    val `data`: Data,
    val message: Any,
    val status: Boolean
)
data class Data(
    val has_more: Boolean,
    val users: List<User>
)
data class User(
    val image: String,
    val items: List<String>,
    val name: String
)