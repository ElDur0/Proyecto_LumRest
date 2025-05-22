package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Empleado

class EmpleadoAdapter(
    private val empleados: List<Empleado>,
    private val onEditar: (Empleado) -> Unit,
    private val onEliminar: (Empleado) -> Unit
) : RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPuesto: TextView = view.findViewById(R.id.tvPuesto)
        val tvHorario: TextView = view.findViewById(R.id.tvHorario)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = empleados[position]
        holder.tvNombre.text = empleado.nombre
        holder.tvPuesto.text = "Puesto: ${empleado.puesto}"
        holder.tvHorario.text = "Horario: ${empleado.horario}"

        holder.btnEditar.setOnClickListener { onEditar(empleado) }
        holder.btnEliminar.setOnClickListener { onEliminar(empleado) }
    }

    override fun getItemCount(): Int = empleados.size
}
