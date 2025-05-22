package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Mesa

class MesaAdapter(
    private val mesas       : List<Mesa>,
    private val onEditar    : (Mesa) -> Unit,
    private val onEliminar  : (Mesa) -> Unit
) : RecyclerView.Adapter<MesaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre        : TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion   : TextView = view.findViewById(R.id.tvDescripcion)
        val btnEditar       : Button   = view.findViewById(R.id.btnEditar)
        val btnEliminar     : Button   = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mesa, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mesa                    = mesas[position]
        holder.tvNombre.text        = mesa.nombre
        holder.tvDescripcion.text   = mesa.descripcion

        holder.btnEditar    .setOnClickListener { onEditar(mesa)   }
        holder.btnEliminar  .setOnClickListener { onEliminar(mesa) }
    }

    override fun getItemCount(): Int = mesas.size
}
