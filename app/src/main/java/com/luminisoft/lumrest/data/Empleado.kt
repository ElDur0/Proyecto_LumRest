package com.luminisoft.lumrest.data



data class Empleado(
    var id      :String? = null,
    var nombre  : String = "",
    var puesto  : String = "",
    var horario : String = ""
){
    constructor():this(null,"","","")
}
