package com.ssafy.challenmungs.data.remote.datasource.klaytn

import com.google.gson.annotations.SerializedName
import com.ssafy.challenmungs.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.challenmungs.domain.entity.klaytn.Account

data class AccountResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("chainId")
    val chainId: Int,
    @SerializedName("createdAt")
    val createdAt: Int,
    @SerializedName("keyId")
    val keyId: String,
    @SerializedName("krn")
    val krn: String,
    @SerializedName("publicKey")
    val publicKey: String,
    @SerializedName("updatedAt")
    val updatedAt: Int
) : DataToDomainMapper<Account> {

    override fun toDomainModel(): Account =
        Account(
            address = address,
            chainId = chainId,
            createdAt = createdAt,
            keyId = keyId,
            krn = krn,
            publicKey = publicKey,
            updatedAt = updatedAt
        )
}
