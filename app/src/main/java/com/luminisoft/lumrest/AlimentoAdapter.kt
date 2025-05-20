package com.luminisoft.lumrest

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.luminisoft.lumrest.data.Alimento
import com.luminisoft.lumrest.data.AlimentoDao

class AlimentoAdapter(
    private val lista: List<Alimento>,
    private val alimentoDao: AlimentoDao,
    private val onActualizado: () -> Unit
) : RecyclerView.Adapter<AlimentoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView      = view.findViewById(R.id.tvNombre)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvPiezas: TextView      = view.findViewById(R.id.tvPiezas)
        val btnEditar: Button       = view.findViewById(R.id.btnEditarAlimento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alimento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alimento = lista[position]

        holder.tvNombre.text      = alimento.nombre
        holder.tvDescripcion.text = alimento.descripcion
        holder.tvPiezas.text      = "Piezas: ${alimento.piezas}"

        holder.btnEditar.setOnClickListener {
            mostrarDialogEditar(holder.itemView, alimento)
        }
    }

    override fun getItemCount(): Int = lista.size

    private fun mostrarDialogEditar(view: View, alimento: Alimento) {
        val contexto   = view.context
        val dialogView = LayoutInflater.from(contexto).inflate(R.layout.dialog_agregar_alimento, null)

        val etNombre      = dialogView.findViewById<EditText>(R.id.etNombreAlimento)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionAlimento)
        val etPiezas      = dialogView.findViewById<EditText>(R.id.etPiezas)
        val btnGuardar    = dialogView.findViewById<Button>(R.id.btnGuardarAlimento)

        // Prellenar campos
        etNombre.setText(alimento.nombre)
        etDescripcion.setText(alimento.descripcion)
        etPiezas.setText(alimento.piezas.toString())

        val dialog = AlertDialog.Builder(contexto)
            .setTitle("Editar Alimento")
            .setView(dialogView)
            .create()

        btnGuardar.setOnClickListener {
            val nombre      = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val piezas      = etPiezas.text.toString().trim().toIntOrNull() ?: 0

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(contexto, "Campos vacíos", Toast.LENGTH_SHORT).show()
            } else {
                alimento.nombre = nombre
                alimento.descripcion = descripcion
                alimento.piezas = piezas

                alimentoDao.update(alimento)
                onActualizado()
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}
