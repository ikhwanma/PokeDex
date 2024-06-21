package com.ikhwanma.common.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(convertResponseToModel(apiResponse.data)))
                onFetchSuccess()
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<ResultType>(
                        apiResponse.message
                    )
                )
            }
            ApiResponse.Empty -> emit(Resource.Error("Empty List"))
        }
    }

    protected abstract fun convertResponseToModel(response: RequestType): ResultType

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected open suspend fun onFetchSuccess() {}

    protected open suspend fun onFetchFailed() {}

    fun asFlow() = result

}