package com.example.themoviedb.screens.main

import android.graphics.Bitmap
import com.example.themoviedb.base.BaseRepository
import com.example.themoviedb.utils.models.PopularPeopleResponse
import io.reactivex.Single


class MainRepository : BaseRepository(),
    Contract.MainRepository {
    override fun getPopularPeople(
        currentPage: Int,
        searchedWord: String?
    ): Single<PopularPeopleResponse> {
        val apiService = remoteDataSource.api

        return if (searchedWord == null) {
            apiService.getPopularPeople(currentPage.toString())
        } else
            apiService.getPopularPeopleSearch(currentPage.toString(), searchedWord)
    }

    override fun saveImage(image: Bitmap) {
        localDataSource.saveImageToStorage(image)
    }
}