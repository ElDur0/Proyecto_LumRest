package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Empleado

class EmpleadoAdapter(private val lista: List<Empleado>) :
    RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPuesto: TextView = view.findViewById(R.id.tvPuesto)
        val tvHorario: TextView = view.findViewById(R.id.tvHorario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = lista[position]
        holder.tvNombre.text = empleado.nombre
        holder.tvPuesto.text = empleado.puesto
        holder.tvHorario.text = empleado.horario
    }

    override fun getItemCount() = lista.size
}
