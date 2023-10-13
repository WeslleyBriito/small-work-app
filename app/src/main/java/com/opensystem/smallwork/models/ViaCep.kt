package com.opensystem.smallwork.models

import com.google.gson.annotations.SerializedName

class ViaCep {

    @field:SerializedName("cep")
    var postalCode: String? = null

    @field:SerializedName("logradouro")
    var street: String? = null

    @field:SerializedName("complemento")
    var complement: String? = null

    @field:SerializedName("bairro")
    var nbh: String? = null

    @field:SerializedName("localidade")
    var city: String? = null

    @field:SerializedName("uf")
    var state: String? = null

}