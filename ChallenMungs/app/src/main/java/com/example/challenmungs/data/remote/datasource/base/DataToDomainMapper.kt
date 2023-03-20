package com.example.challenmungs.data.remote.datasource.base

interface DataToDomainMapper<T> {
    fun toDomainModel(): T
}