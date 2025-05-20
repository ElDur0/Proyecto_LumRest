package com.luminisoft.lumrest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Empleado

class EmpleadoAdapter(private val empleados: List<Empleado>) :
    RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {

        private var posicionExpandida = -1
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvPuesto = view.findViewById<TextView>(R.id.tvPuesto)
        val tvHorario = view.findViewById<TextView>(R.id.tvHorario)
        val btnEditar = view.findViewById<Button>(R.id.btnEditar)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminar)
        val layoutDetalles = view.findViewById<LinearLayout>(R.id.layoutDetalles)
        val headerEmpleado = view.findViewById<LinearLayout>(R.id.headerEmpleado)
    }


   /* class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvPuesto: TextView = view.findViewById(R.id.tvPuesto)
        val tvHorario: TextView = view.findViewById(R.id.tvHorario)
    }*/

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

        val estaExpandido = position == posicionExpandida
        holder.layoutDetalles.visibility = if (estaExpandido) View.VISIBLE else View.GONE

        // Click para expandir
        holder.headerEmpleado.setOnClickListener {
            posicionExpandida = if (estaExpandido) -1 else position
            notifyDataSetChanged()
        }

        holder.btnEliminar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Eliminar: ${empleado.nombre}", Toast.LENGTH_SHORT).show()
            // pendiente lógica de eliminar
        }


        holder.btnEditar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Editar: ${empleado.nombre}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = empleados.size
}
