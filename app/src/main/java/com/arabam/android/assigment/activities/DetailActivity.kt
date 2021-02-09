package com.arabam.android.assigment.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.arabam.android.assigment.R
import com.arabam.android.assigment.databinding.ActivityDetailBinding
import com.arabam.android.assigment.model.Car
import com.arabam.android.assigment.service.ServiceConnector
import com.arabam.android.assigment.util.Constants
import com.arabam.android.assigment.util.replacePhotoForDetail
import com.arabam.android.assigment.util.replacePhotoForList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.detail_image.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {


    private var id: Int? = null

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getData()

    }

    private fun init() {
        id = intent.getIntExtra(Constants.PRODUCT_ID, 0)
    }

    private fun getData() {
        binding.progress.visibility = View.VISIBLE

        val queryMap = mutableMapOf<String, Int>()
        queryMap["id"] = id!!


        ServiceConnector.arabamcomApi?.getCarDetail(queryMap)?.enqueue(object :
            Callback<Car> {
            override fun onResponse(call: Call<Car>, response: Response<Car>) {
                binding.progress.visibility = View.GONE

                response.body()?.let {
                    binding.titleTV.text = it.title
                    binding.dateTV.text = it.dateFormatted
                    binding.modelNameTV.text = it.modelName
                    binding.priceFormattedTV.text = it.priceFormatted

                    binding.webview.settings.setJavaScriptEnabled(true);
                    binding.webview.loadData(it.text, "text/html; charset=utf-8", "UTF-8");


                    binding.vp.adapter = CustomPagerAdapter(this@DetailActivity, it.photos)
                    binding.dotsIndicator.setViewPager(binding.vp)
                }
            }

            override fun onFailure(call: Call<Car>, t: Throwable) {
                binding.progress.visibility = View.GONE
                Toast.makeText(this@DetailActivity, "fail", Toast.LENGTH_LONG).show()
            }

        })
    }


    //https://www.bignerdranch.com/blog/viewpager-without-fragments/
    class CustomPagerAdapter(private val mContext: Context, private val listOfCar : List<String>) : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(mContext)
            val layout = inflater.inflate(R.layout.detail_image, collection, false) as ViewGroup
            Glide.with(layout.context).load(listOfCar[position].replacePhotoForDetail()).placeholder(R.drawable.not_found).fitCenter().into(layout.image)

            collection.addView(layout)
            return layout
        }

        override fun destroyItem(
            collection: ViewGroup,
            position: Int,
            view: Any
        ) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return listOfCar.size
        }

        override fun isViewFromObject(
            view: View,
            `object`: Any
        ): Boolean {
            return view === `object`
        }


    }

}