package com.luminisoft.lumrest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Bebida
import com.luminisoft.lumrest.data.BebidaDao

class BebidaAdapter(
    private val lista: List<Bebida>,
    private val bebidaDao: BebidaDao,
    private val onActualizado: () -> Unit
) : RecyclerView.Adapter<BebidaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas: TextView = view.findViewById(R.id.tvPiezas)
        val btnEditar: Button = view.findViewById(R.id.btnEditarBebida)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bebida, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bebida = lista[position]

        holder.tvNombre.text = bebida.nombre
        holder.tvDescripcion.text = bebida.descripcion
        holder.tvPiezas.text = "Piezas: ${bebida.piezas}"

        holder.btnEditar.setOnClickListener {
            mostrarDialogEditar(holder.itemView.context, bebida)
        }
    }

    private fun mostrarDialogEditar(context: Context, bebida: Bebida) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_bebida, null)
        val dialog = AlertDialog.Builder(context)
            .setTitle("Editar Bebida")
            .setView(dialogView)
            .create()

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreBebida)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionBebida)
        val etPiezas = dialogView.findViewById<EditText>(R.id.etPiezasBebida)
        val btnGuardar = dialogView.findViewById<Button>(R.id.btnGuardarBebida)

        etNombre.setText(bebida.nombre)
        etDescripcion.setText(bebida.descripcion)
        etPiezas.setText(bebida.piezas.toString())

        btnGuardar.setOnClickListener {
            val nuevoNombre = etNombre.text.toString().trim()
            val nuevaDescripcion = etDescripcion.text.toString().trim()
            val nuevasPiezas = etPiezas.text.toString().toIntOrNull() ?: 0

            if (nuevoNombre.isNotEmpty() && nuevaDescripcion.isNotEmpty() && nuevasPiezas > 0) {
                bebida.nombre = nuevoNombre
                bebida.descripcion = nuevaDescripcion
                bebida.piezas = nuevasPiezas
                bebidaDao.update(bebida)
                Toast.makeText(context, "Bebida actualizada", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                onActualizado()
            } else {
                Toast.makeText(context, "Completa los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun getItemCount(): Int = lista.size
}
