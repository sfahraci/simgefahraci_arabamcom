package com.arabam.android.assigment.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.GridItem
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.list.listItems
import com.arabam.android.assigment.adapter.CarListAdapter
import com.arabam.android.assigment.databinding.ActivityMainBinding
import com.arabam.android.assigment.databinding.ContentMainBinding
import com.arabam.android.assigment.model.Car
import com.arabam.android.assigment.service.ServiceConnector
import com.arabam.android.assigment.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    //sıralama
    private val sortByDate = 1
    private val sortByPrice = 0
    private val sortByYear = 2

    private var sortType = 1
    private var take = 0


    private var currentPage = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var contentBinding: ContentMainBinding

    //https://stackoverflow.com/a/24471410/4862911
    private lateinit var adapter: CarListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        contentBinding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        init()
        getData()

    }

    private fun init() {


        binding.filterTV.setOnClickListener {
            val items = listOf(
                BasicGridItem(0, "Fiyata Göre"),
                BasicGridItem(0, "Eklenme Tarihine Göre"),
                BasicGridItem(0, "Yıla Göre")
            )


            val list : List<CharSequence> = listOf("Fiyata Göre","Eklenme Tarihine Göre","Yıla Göre")

            MaterialDialog(this).show {
                listItems(items = list) { dialog, index, text ->
                    take = 0
                    sortType = index
                    adapter.carList = arrayListOf()
                    getData()
                }
            }
        }


        //https://stackoverflow.com/a/24471410/4862911
        adapter = CarListAdapter(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val itemPosition: Int = binding.recylerView.getChildLayoutPosition(v!!)
                val item: Car = adapter.carList[itemPosition]

                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(Constants.PRODUCT_ID, item.id)
                startActivity(intent)
            }
        })

        //recylerview init
        val gridLayoutManager =
            GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
        binding.recylerView.layoutManager = gridLayoutManager
        binding.recylerView.adapter = adapter

        //scroll listener atanıyor paging için
        //https://stackoverflow.com/questions/35673854/how-to-implement-infinite-scroll-in-gridlayout-recylcerview
        binding.recylerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val layoutManager =
                        binding.recylerView.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        getData()
                    }
                }
            }
        })
    }

    private fun getData() {
        binding.progress.visibility = View.VISIBLE

        take += 10


        val queryMap = mutableMapOf<String, Int>()
        queryMap["sort"] = sortType
        queryMap["sortDirection"] = 0
        queryMap["take"] = take

        currentPage++
        ServiceConnector.arabamcomApi?.getCarList(queryMap)?.enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                binding.progress.visibility = View.GONE

                response.body()?.let {
                    adapter.setAdapterItems(response.body()!!)
                }


            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
            }

        })
    }

}