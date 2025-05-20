package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.CarritoManager

class AlimentoCarritoAdapter(
    private val alimentos: MutableList<Alimento>,
    private val onItemEliminado: () -> Unit
) : RecyclerView.Adapter<AlimentoCarritoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas: TextView = view.findViewById(R.id.tvPiezas)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarAlimento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito_alimento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alimento = alimentos[position]
        holder.tvNombre.text = alimento.nombre
        holder.tvDescripcion.text = alimento.descripcion
        holder.tvPiezas.text = "Piezas: ${alimento.piezas}"

        holder.btnEliminar.setOnClickListener {
            CarritoManager.eliminarAlimento(alimento)
            alimentos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, alimentos.size)
            onItemEliminado()
        }
    }

    override fun getItemCount(): Int = alimentos.size
}
