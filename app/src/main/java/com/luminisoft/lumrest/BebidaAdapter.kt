package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Bebida

class BebidaAdapter(
    private val bebidas     : List<Bebida>,
    private val onEditar    : (Bebida) -> Unit,
    private val onEliminar  : (Bebida) -> Unit
) : RecyclerView.Adapter<BebidaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre     : TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvVolumen    : TextView = view.findViewById(R.id.tvMililitros)
        val btnEditar    : Button = view.findViewById(R.id.btnEditar)
        val btnEliminar  : Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bebida, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bebida = bebidas[position]

        holder.tvNombre.text      = bebida.nombre
        holder.tvDescripcion.text = bebida.descripcion
        holder.tvVolumen.text     = "cantidad: ${bebida.mililitros}"

        holder.btnEditar.setOnClickListener {
            onEditar(bebida)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminar(bebida)
        }
    }

    override fun getItemCount(): Int = bebidas.size
}
