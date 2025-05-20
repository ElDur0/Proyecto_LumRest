package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Mesa

class MesaAdapter(
    private val mesas: List<Mesa>
) : RecyclerView.Adapter<MesaAdapter.ViewHolder>() {

    private var expandida = -1

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre = view.findViewById<TextView>(R.id.tvNombreMesa)
        val tvDescripcion = view.findViewById<TextView>(R.id.tvDescripcionMesa)
        val btnEditar = view.findViewById<Button>(R.id.btnEditarMesa)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminarMesa)
        val layoutDetalles = view.findViewById<LinearLayout>(R.id.layoutDetallesMesa)
        val header = view.findViewById<LinearLayout>(R.id.headerMesa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mesa, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mesa = mesas[position]

        holder.tvNombre.text = mesa.nombre
        holder.tvDescripcion.text = mesa.descripcion

        val estaExpandida = position == expandida
        holder.layoutDetalles.visibility = if (estaExpandida) View.VISIBLE else View.GONE

        holder.header.setOnClickListener {
            expandida = if (estaExpandida) -1 else position
            notifyDataSetChanged()
        }

        holder.btnEditar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Editar: ${mesa.nombre}", Toast.LENGTH_SHORT).show()
        }

        holder.btnEliminar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Eliminar: ${mesa.nombre}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = mesas.size
}
