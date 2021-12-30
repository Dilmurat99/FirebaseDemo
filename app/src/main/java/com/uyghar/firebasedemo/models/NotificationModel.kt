package com.uyghar.firebasedemo.models

data class NotificationModel(
    val title: String,
    val body: String,
    val priority: String,
    val content_available: Boolean
)

data class DataModel(
    val id: Int
)

data class FBMessage(
    val to: String,
    val notification: NotificationModel,
    val data: DataModel
)

data class MyUser(
    val name: String,
    val token: String
) {
    override fun toString(): String {
        return name
    }
}