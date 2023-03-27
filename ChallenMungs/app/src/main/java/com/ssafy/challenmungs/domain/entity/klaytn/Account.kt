package com.ssafy.challenmungs.domain.entity.klaytn

data class Account(
    val address: String,
    val chainId: Int,
    val createdAt: Int,
    val keyId: String,
    val krn: String,
    val publicKey: String,
    val updatedAt: Int
)
