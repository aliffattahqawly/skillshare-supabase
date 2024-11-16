package com.yourssohail.learnsupabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder>() {

    // ViewHolder untuk menyimpan referensi ke tampilan item
    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // Membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        // Menginflate layout untuk item slider
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        // Menggunakan Glide untuk memuat gambar dari URL
        Glide.with(holder.itemView)
            .load(images[position]) // Mengambil URL gambar dari daftar
            .into(holder.imageView) // Memasukkan gambar ke ImageView
    }

    // Mengembalikan jumlah item dalam daftar
    override fun getItemCount(): Int = images.size
}