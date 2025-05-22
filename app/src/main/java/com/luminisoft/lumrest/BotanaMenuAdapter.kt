package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Botana
import com.luminisoft.lumrest.data.CarritoManager

class BotanaMenuAdapter(
    private val botanas: List<Botana>
) : RecyclerView.Adapter<BotanaMenuAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvNombre        : TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion   : TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas        : TextView = view.findViewById(R.id.tvPiezas)
        val btnAgregar      : Button   = view.findViewById(R.id.btnAgregarAlimento)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_alimento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val botana = botanas[position]

        holder.tvNombre.text      = botana.nombre
        holder.tvDescripcion.text = botana.descripcion
        holder.tvPiezas.text      = "Piezas: ${botana.piezas}"

        holder.btnAgregar.setOnClickListener {
            CarritoManager.agregarAlimento(botana.toAlimento())
            Toast.makeText(holder.itemView.context, "${botana.nombre} agregada al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = botanas.size
}
