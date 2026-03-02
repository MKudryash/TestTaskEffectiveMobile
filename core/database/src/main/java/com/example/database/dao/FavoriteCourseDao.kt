package com.example.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.FavoriteCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCourseDao {
    @Query("SELECT * FROM favorite_courses WHERE userId = :userId ORDER BY addedAt DESC")
    fun getFavoritesByUser(userId: String): Flow<List<FavoriteCourseEntity>>

    @Query("SELECT * FROM favorite_courses WHERE userId = :userId ORDER BY addedAt DESC")
    suspend fun getFavoritesByUserSync(userId: String): List<FavoriteCourseEntity>

    @Query("SELECT courseId FROM favorite_courses WHERE userId = :userId")
    suspend fun getFavoriteIdsByUser(userId: String): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteCourseEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteCourseEntity)

    @Query("DELETE FROM favorite_courses WHERE courseId = :courseId AND userId = :userId")
    suspend fun deleteFavoriteByCourseId(courseId: Int, userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_courses WHERE courseId = :courseId AND userId = :userId)")
    suspend fun isFavorite(courseId: Int, userId: String): Boolean
}