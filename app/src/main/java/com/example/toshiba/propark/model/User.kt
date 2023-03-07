package com.example.toshiba.propark.model

data class User(var email: String? = null,
                var password: String? = null,
                var name: String? = null,
                var phoneNumber: String? = null,
                var from : Int? = 0,
                var to : Int? = 0,
                var loc : Int? = 0,
                var uid: String? = null) {}