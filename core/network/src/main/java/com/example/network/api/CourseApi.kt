package com.example.network.api

import com.example.network.model.CoursesResponse
import retrofit2.http.GET

interface CourseApi {

    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): CoursesResponse
}