package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Botana

class BotanaAdapter(
    private val botanas    : List<Botana>,
    private val onEditar   : (Botana) -> Unit,
    private val onEliminar : (Botana) -> Unit
) : RecyclerView.Adapter<BotanaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre      : TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion : TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas      : TextView = view.findViewById(R.id.tvPiezas)
        val btnEditar     : Button   = view.findViewById(R.id.btnEditar)
        val btnEliminar   : Button   = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_botana, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val botana                = botanas[position]

        holder.tvNombre.text      = botana.nombre
        holder.tvDescripcion.text = botana.descripcion
        holder.tvPiezas.text      = "Piezas: ${botana.piezas}"

        holder.btnEditar.setOnClickListener   { onEditar(botana)   }
        holder.btnEliminar.setOnClickListener { onEliminar(botana) }
    }

    override fun getItemCount(): Int = botanas.size
}
