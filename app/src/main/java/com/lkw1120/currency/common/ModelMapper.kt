package com.lkw1120.currency.common

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.reflect.KParameter


object ModelMapper {

    /**
     * 반환 클래스의 주 생성자를 통해 값을 바인딩하여 반환
     */
    inline fun <reified T, reified R> mapper(source: T): R {
        R::class.constructors
            .last { constructor ->
                val mutableMap: MutableMap<KParameter, Any?> = mutableMapOf<KParameter, Any?>()
                constructor.parameters.forEach { parameter ->
                    mutableMap[parameter] = T::class.members.find { it.name == parameter.name }?.call(source)
                }
                return constructor.callBy(mutableMap)
            }
        throw RuntimeException("${R::class.simpleName} 클래스의 생성자가 존재하지 않습니다.")
    }

    /**
     * json 문자열을 Map 형태 변환하여 반환
     */
    fun convertJsonToMap(json: String): Map<String, Any> {
        val type: Type = object : TypeToken<Map<String?, Any?>?>() {}.type
        return (Gson().fromJson(json, type) as LinkedTreeMap<String, Any>).toMap()
    }

}