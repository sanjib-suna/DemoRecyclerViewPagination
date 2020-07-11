package com.example.practicaldemo.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.practicaldemo.Adapter.ListAdapter
import com.example.practicaldemo.R
import com.example.practicaldemo.api.ApiManager
import com.example.practicaldemo.api.Params
import com.example.practicaldemo.api.callApi
import com.example.practicaldemo.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var listRes = ArrayList<User>()
    var page:Int =1
    var limit = 10

    private var adapter: ListAdapter? = null

    private var loading = true

    var pastVisiblesItems = 0
    var visibleItemCount = 0
    var  totalItemCount = 0

    var layoutManager:LinearLayoutManager? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRecyclerView()
        apiCallList(true)
    }

    private fun setRecyclerView(){
        val gLayout =
            GridLayoutManager(this, 4)
        layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

//        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
//        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL // set Horizontal Orientation
//
//        recyclerView.setLayoutManager(gridLayoutManager)


        rcvMainlist.layoutManager = layoutManager
        adapter = ListAdapter(this,listRes)
        rcvMainlist.adapter = adapter


        rcvMainlist.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = layoutManager!!.getChildCount();
                    totalItemCount = layoutManager!!.getItemCount();
                    pastVisiblesItems = layoutManager!!.findFirstVisibleItemPosition()
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {

                            apiCallList(false);

                            adapter!!.showLoading(true);
                            loading = false;
                        }
                    } else {
                        adapter!!.showLoading(false);
                    }
                }

            }
        })
    }

    private fun apiCallList(isShow:Boolean){

        if (isShow) {
            pbProgess.visibility = View.VISIBLE
        }

        ApiManager.instance.getList(with(Params()){
            set("offset",""+page.toString())
            set("limit",""+limit.toString())
        }).callApi(
            onSuccess = {
                if(it.status){

                    pbProgess.visibility = View.GONE


                    if (page == 1) {
                        listRes.clear();
                    }
                    if (it.data.has_more) {
                        page++;
                        loading = true;
                    } else {
                        loading = false;
                    }
                    for (i in 0 until it.data.users.size) {
                        listRes.add(it.data.users[i])
                    }
                    adapter!!.setItems(listRes);
                    adapter!!.notifyDataSetChanged();

                    if (loading)
                        adapter!!.showLoading(true);
                    else
                        adapter!!.showLoading(false);

                }

            }, onFailure = {
                pbProgess.visibility = View.GONE
            }
        )

    }

}
