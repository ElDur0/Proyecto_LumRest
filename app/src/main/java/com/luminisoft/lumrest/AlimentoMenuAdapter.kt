package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.CarritoManager

class AlimentoMenuAdapter(private val alimentos: List<Alimento>) :
    RecyclerView.Adapter<AlimentoMenuAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView      = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas: TextView      = view.findViewById(R.id.tvPiezas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alimento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alimento = alimentos[position]

        holder.tvNombre.text      = alimento.nombre
        holder.tvDescripcion.text = alimento.descripcion
        holder.tvPiezas.text      = "Piezas: ${alimento.piezas}"

        holder.itemView.setOnClickListener {
            CarritoManager.agregarAlimento(alimento)
            Toast.makeText(
                holder.itemView.context,
                "${alimento.nombre} agregado al carrito",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = alimentos.size
}
