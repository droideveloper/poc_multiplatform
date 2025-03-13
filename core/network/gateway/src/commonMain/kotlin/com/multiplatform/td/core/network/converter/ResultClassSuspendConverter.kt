package com.multiplatform.td.core.network.converter

import com.multiplatform.td.core.network.HttpErrorCode
import com.multiplatform.td.core.network.HttpErrorException
import com.multiplatform.td.core.network.NoContentCode
import com.multiplatform.td.core.network.NoContentException
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo

internal class ResultClassSuspendConverter(
    private val typeData: TypeData,
) : Converter.SuspendResponseConverter<HttpResponse, Any> {

    override suspend fun convert(result: KtorfitResult): Any {
        return when (result) {
            is KtorfitResult.Success -> result.toResult()
            is KtorfitResult.Failure -> Result.failure<Any>(result.throwable)
        }
    }

    private suspend fun KtorfitResult.Success.toResult(
        typeInfo: TypeInfo = typeData.typeArgs.first().typeInfo,
        response: HttpResponse = this.response,
        code: Int = response.status.value,
    ): Any =
        when  {
            code < 200 || code >= 300 -> Result.failure(
                exception = HttpErrorException(HttpErrorCode.getOrThrow(code))
            )
            code == 204 || code == 205 -> Result.failure(
                exception = NoContentException(NoContentCode.getOrThrow(code))
            )
            else -> {
                Result.success<Any>(response.body(typeInfo))
            }
        }
}
