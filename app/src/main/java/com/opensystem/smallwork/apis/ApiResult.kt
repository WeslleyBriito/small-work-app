package com.opensystem.smallwork.apis

import com.opensystem.smallwork.models.ErrorSW

interface ApiResult<T> {

   fun responseBody(body: T, type: String) {}

   fun responseBodyList(list: List<T>, type: String) {}

   fun error(error: ErrorSW)

}