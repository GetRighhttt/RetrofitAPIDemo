package com.example.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitdemo.model.AlbumServiceResponse
import com.example.retrofitdemo.model.AlbumsDataClass
import com.example.retrofitdemo.model.AlbumsDataClassItem
import com.example.retrofitdemo.model.RetrofitInstance
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retServiceResponse: AlbumServiceResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Basic example of how we can use our Retrofit to display some data.
         *
         * First we need to get an instance of our Service Response.
         *
         */
        retServiceResponse = RetrofitInstance.getRetrofitInstance()
            .create(AlbumServiceResponse::class.java)

        /**
         * Methods to display each type of requests
         */
        getAllAlbums()
//        getRequestsWithQueryParameters()
        // getRequestsWithPathParameters()
//        uploadAlbum()
    }

    private fun getAllAlbums() {
        /**
         * A response that will retrieve all of the albums and display them on the textview.
         */
        val responseLiveData: LiveData<Response<AlbumsDataClass>> = liveData {
            val response = retServiceResponse.getAlbums()
            emit(response)
        }
        // Use LiveData to observe the life cycle and display the data in a scrollview
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if(albumsList != null) {
                while(albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    Log.i("MYTAG", albumsItem.title)
                    val result = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album userId : ${albumsItem.userId}" + "\n" +
                            " " + "Album id : ${albumsItem.id}" + "\n\n\n"
                    val textView: TextView = findViewById(R.id.textView)
                    textView.append(result)
                }
            }
        })
    }

    private fun getRequestsWithQueryParameters() {
        /**
         * This is a regular response that specifies based on the id 3 for the album.
         *
         * This demonstrates a query response.
         *
         * Then we will use a Coroutine LiveData builder, we will get the Retrofit
         * response as Live Data using liveData{} Coroutine scope.
         *
         * listIterator() returns a list iterator over the elements to get the elements
         * one by one in a proper sequence.
         *
         * We will use a while loop to get the album objects.
         *
         * We can interchange based on the method also.
         * getSortedAlbums, getAlbums, etc.
         */
        val responseLiveData: LiveData<Response<AlbumsDataClass>> = liveData {
            val response = retServiceResponse.getSortedAlbums(5)
            emit(response)
        }
        // Use LiveData to observe the life cycle and display the data in a scrollview
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if(albumsList != null) {
                while(albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    Log.i("MYTAG", albumsItem.title)
                    val result = " " + "Album Title : ${albumsItem.title}" + "\n" +
                    " " + "Album userId : ${albumsItem.userId}" + "\n" +
                    " " + "Album id : ${albumsItem.id}" + "\n\n\n"
                    val textView: TextView = findViewById(R.id.textView)
                    textView.append(result)
                }
            }
        })
    }

    private fun getRequestsWithPathParameters() {
        /**
         * Here is an example of how a path parameter works.
         *
         * Again, this is if we want to return an item object based on a specific name.
         *
         * To do this, we will need to use a separate LiveData Coroutine builder.
         *
         * And we will use that LiveData Coroutine builder (liveData) to get the body
         * and display a Toast message.
         */
        val pathResponse : LiveData<Response<AlbumsDataClassItem>> = liveData {
            val response = retServiceResponse.getSingleAlbum(3)
            emit(response)
        }
        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(this,title, Toast.LENGTH_LONG).show()
        })
    }

    /**
     * Here below we will show an example of a POST requests.
     *
     * The id will be ignored by the server because it will automatically update
     * itself in the database, so we can pass in any ID as an Int.
     *
     * The rest of the steps are the same.
     *
     * We are going to display the result in a textview.
     */
    private fun uploadAlbum() {
        val album = AlbumsDataClassItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumsDataClassItem>> = liveData {
            val response = retServiceResponse.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result = "" + "Album Title : ${receivedAlbumsItem?.title}" + "\n"
            "" + "Album userId : ${receivedAlbumsItem?.userId}" + "\n"
            "" + "Album id : ${receivedAlbumsItem?.id}" + "\n\n\n"
            val textView: TextView = findViewById(R.id.textView)
            textView.append(result)
        })
    }
}