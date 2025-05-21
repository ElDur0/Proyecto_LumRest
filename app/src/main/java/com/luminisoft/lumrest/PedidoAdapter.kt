package com.luminisoft.lumrest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Pedido

class PedidoAdapter(
    private val pedidos:        List<Pedido>,
    private val onEstadoCambio: (Pedido, String) -> Unit
) : RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMesa          : TextView = view.findViewById(R.id.tvMesa)
        val tvDescripcion   : TextView = view.findViewById(R.id.tvDescripcion)
        val tvEstado        : TextView = view.findViewById(R.id.tvEstadoPedido)
        val btnPreparacion  : Button   = view.findViewById(R.id.btnPreparacion)
        val btnListo        : Button   = view.findViewById(R.id.btnListo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido = pedidos[position]

        holder.tvMesa.text          = "Mesa: ${pedido.mesa}"
        holder.tvDescripcion.text   = pedido.descripcion
        holder.tvEstado.text        = "Estado: ${pedido.estado}"

        holder.btnPreparacion.setOnClickListener {
            onEstadoCambio(pedido, "En preparación")
        }

        holder.btnListo.setOnClickListener {
            onEstadoCambio(pedido, "Listo")
        }
    }

    override fun getItemCount(): Int = pedidos.size
}
