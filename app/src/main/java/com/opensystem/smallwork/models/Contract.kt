package com.opensystem.smallwork.models

class Contract(
   var id: String = "",
   var user: User = User(),
   var worker: Worker = Worker(),
   var userId: String = "",
   var workerId: String = "",
   var requestDate: Long = 0,
   var status: Int = 0,
   var daily: String = ""
)