package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Bebida
import com.luminisoft.lumrest.data.CarritoManager

class BebidaMenuAdapter(private val bebidas: List<Bebida>) :
    RecyclerView.Adapter<BebidaMenuAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView      = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvMililitros: TextView  = view.findViewById(R.id.tvMililitros)
        val btnAgregar: Button      = view.findViewById(R.id.btnAgregarBebida)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_bebida, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bebida                = bebidas[position]
        holder.tvNombre.text      = bebida.nombre
        holder.tvDescripcion.text = bebida.descripcion
        holder.tvMililitros.text  = "Volumen: ${bebida.mililitros} ml"

        holder.btnAgregar.setOnClickListener {
            CarritoManager.agregarBebida(bebida)
            Toast.makeText(holder.itemView.context, "${bebida.nombre} agregada al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = bebidas.size
}