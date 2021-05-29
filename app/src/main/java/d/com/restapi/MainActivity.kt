package d.com.restapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

     var allResults: List<Results>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            val retro = Retrofit.Builder()
                .baseUrl(URl_Rick_API).addConverterFactory(MoshiConverterFactory.create()).build()
            val service = retro.create(ApiService::class.java)
            val characterRequest = service.getResults("2")

            characterRequest.enqueue(object : Callback<List<Results>> {
                override fun onResponse(
                    call: Call<List<Results>>,
                    response: Response<List<Results>>
                ) {

                    allResults = response.body()
                }

                override fun onFailure(call: Call<List<Results>>, t: Throwable) {
                    Log.v(MainActivity::class.simpleName, "FAILURE")
                }
            })

        }

        val mAdapter = myAdapter(this, allResults)
        recyclerView.adapter = mAdapter
    }

    companion object {
        const val URl_Rick_API = "https://rickandmortyapi.com/api/"
    }


    class myAdapter(
        var context: Context,
        var results: List<Results>?
    ) :
        RecyclerView.Adapter<myAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
            var name: TextView? = null
            var status: TextView? = null
            var species: TextView? = null
            var image: ImageView? = null

            init {
                name = itemView.findViewById(R.id.name)
                status = itemView.findViewById(R.id.status)
                species = itemView.findViewById(R.id.specie)
                image = itemView.findViewById(R.id.image)
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val customView =
                LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
            return ViewHolder(customView)
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.name?.text = results?.get(position)?.name
            holder.status?.text = results?.get(position)?.status
            holder.species?.text = results?.get(position)?.species
            Picasso.get().load(results?.get(position)?.image).into(holder.image)
        }

        override fun getItemCount(): Int {
            return 25
        }

    }
}