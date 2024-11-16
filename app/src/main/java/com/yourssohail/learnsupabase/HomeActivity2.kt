package com.yourssohail.learnsupabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.yourssohail.learnsupabase.data.network.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds



    class HomeActivity2 : ComponentActivity() {

        private lateinit var viewPager: ViewPager2
        private lateinit var imageSliderAdapter: ImageSliderAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_home2)

            viewPager = findViewById(R.id.viewPager)

            // Ambil URL gambar dari Supabase Storage
            val imageUrls = fetchImageUrlsFromSupabase()

            // Inisialisasi Adapter
            imageSliderAdapter = ImageSliderAdapter(imageUrls)
            viewPager.adapter = imageSliderAdapter

            // Mengatur padding untuk system bars
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        private fun fetchImageUrlsFromSupabase(): List<String> {
            val imageUrls = mutableListOf<String>()
            val storage = SupabaseClient.client.storage

            runBlocking {
                try {
                    val bucket = storage.from("image_slider")
                    val files = bucket.list(prefix = "") // Mengirimkan string kosong sebagai prefix

                    files.forEach { file ->
                        val url = bucket.createSignedUrl(file.name, 60.seconds) // Menggunakan Duration.seconds
                        imageUrls.add(url)
                        Log.d("Supabase", "Fetched image URL: $url")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Supabase", "Error fetching image URLs: ${e.message}")
                }
            }

            return imageUrls
        }
    }