package com.ikhwanma.common.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PagingRemoteSource<ResultType:Any, RequestType>: PagingSource<Int, ResultType>(){

    override fun getRefreshKey(state: PagingState<Int, ResultType>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultType> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = createCall(20, nextPageNumber)
            LoadResult.Page(
                data = mapToResult(response.results),
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 20,
                nextKey = nextPageNumber + 20
            )
        } catch (e: Exception) {
            Log.e("TAG", e.message.toString())
            LoadResult.Error(e)
        }
    }

    abstract suspend fun createCall(limit: Int, offset: Int): PagingResponse<RequestType>

    abstract fun mapToResult(response: List<RequestType>): List<ResultType>

}

data class PagingResponse<DataType>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<DataType>
)