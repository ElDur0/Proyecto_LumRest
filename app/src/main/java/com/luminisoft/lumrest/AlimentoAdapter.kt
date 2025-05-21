package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.luminisoft.lumrest.data.Alimento

class AlimentoAdapter(private var alimentos: List<Alimento>, private val onEditar: (Alimento) -> Unit) :
    RecyclerView.Adapter<AlimentoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas: TextView = view.findViewById(R.id.tvPiezas)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_alimento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alimento = alimentos[position]

        holder.tvNombre.text = alimento.nombre
        holder.tvDescripcion.text = alimento.descripcion
        holder.tvPiezas.text = "Piezas: ${alimento.piezas}"

        holder.btnEditar.setOnClickListener { onEditar(alimento) }

        holder.btnEliminar.setOnClickListener {
            alimento.id?.let {
                FirebaseFirestore.getInstance().collection("alimentos").document(it).delete()
            }
        }
    }

    override fun getItemCount() = alimentos.size

    fun actualizarLista(nuevaLista: List<Alimento>) {
        alimentos = nuevaLista
        notifyDataSetChanged()
    }
}
