package com.luminisoft.lumrest.data

data class Mesa(
    var id          :String? = null,
    var nombre      :String  = "",
    var descripcion :String  = ""
){
    constructor():this(null,"","")
}
