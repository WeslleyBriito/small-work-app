package com.opensystem.smallwork.utils

import com.opensystem.smallwork.models.Category
import com.opensystem.smallwork.models.UAddress
import com.opensystem.smallwork.models.User

/**
 * AppInfo is a file to get info about the app and user
 *
 * @author Wesley Brito
 * @Created 2023/08/05
 */

data class AppInfo(
   var user: User = User(),
   var address: UAddress = UAddress(),
   var categories: List<Category> = emptyList()
)

private val mAppInfo = AppInfo()

fun setAppInfo(user: User, address: UAddress, categories: List<Category>? = null){
   mAppInfo.user = user
   mAppInfo.address = address
   if (categories != null) mAppInfo.categories = categories
}

fun authUser(): User{
   return  mAppInfo.user
}

fun uAddress(): UAddress{
   return  mAppInfo.address
}

fun categories(): List<Category>{
   return mAppInfo.categories
}

fun getUserAddress() = if (appUAddress.complement != null && appUAddress.complement != "") {
   appUAddress.street + ", " + appUAddress.number + ", " + appUAddress.complement
} else {
   appUAddress.street + ", " + appUAddress.number
}

inline val appUser: User get() = authUser()
inline val appUAddress: UAddress get() = uAddress()
inline val appUAddressName: String get() = if (appUser.addressId.isNullOrEmpty()) "" else getUserAddress()
inline val appCategories: List<Category> get() = categories()