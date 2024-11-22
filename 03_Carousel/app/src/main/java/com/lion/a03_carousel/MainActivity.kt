package com.lion.a03_carousel

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.lion.a03_carousel.databinding.ActivityMainBinding
import com.lion.a03_carousel.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 이미지 데이터들
    val imageRes = arrayOf(
        R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,
        R.drawable.image_4, R.drawable.image_5, R.drawable.image_6,
        R.drawable.image_7, R.drawable.image_8, R.drawable.image_9,
        R.drawable.image_10
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.apply {
            recyclerView.adapter = CarouselAdapter()
            // 회전 목마용 LayoutManager
            recyclerView.layoutManager = CarouselLayoutManager()
            // recyclerView.layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            // recyclerView.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy())
            // recyclerView.layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.VERTICAL)

            val snapHelper = CarouselSnapHelper()
            snapHelper.attachToRecyclerView(recyclerView)
        }
    }

    inner class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>(){
        inner class CarouselViewHolder(val rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root), OnClickListener{
            override fun onClick(v: View?) {
                activityMainBinding.imageView.setImageResource(imageRes[adapterPosition])
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
            val rowBinding = RowBinding.inflate(layoutInflater, parent, false)
            val carouselViewHolder = CarouselViewHolder(rowBinding)
            rowBinding.root.setOnClickListener(carouselViewHolder)
            return carouselViewHolder
        }

        override fun getItemCount(): Int {
            return imageRes.size
        }

        override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
            holder.rowBinding.imageViewRow.setImageResource(imageRes[position])
        }
    }
}
