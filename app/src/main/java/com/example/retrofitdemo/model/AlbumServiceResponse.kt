package com.example.retrofitdemo.model

import retrofit2.Response
import retrofit2.http.*
import java.sql.RowId

/**
 * We must define our methods for our JSON objects in a Service Interface. This is the standard
 * approach when working with APIs.
 *
 * This is necessary to define methods with URL endpoints.
 */

interface AlbumServiceResponse {

    /**
     * Method to return a Retrofit response object of type AlbumsDataClass.
     * Retrofit always gives the response as a Response Object.
     *
     * We are using coroutines with this project so we must add suspend.
     *
     * Here is where we also define path and query parameters.
     *
     * @GET simplest form of HTTP request types with URL endpoint in the parenthesis.
     *
     * @Query is used to get something sorted based on a "?" after the end URL.
     */

    // Base URL - https://jsonplaceholder.typicode.com/
    // endpoint - /albums
    @GET("/albums")
    suspend fun getAlbums(): Response<AlbumsDataClass>

    /**
     * Below we will show how we can use @Query to sort a list based on a parameter
     *
     * The URL endpoint doesn't change. We just provide the name of the field as a value.
     *
     * In this case, it is userId.
     */
    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int): Response<AlbumsDataClass>

    /**
     * Now we will use a path parameter to return a single album.
     *
     * Path parameters are known as name replacements and depends on the API
     * Implementation.
     *
     * @Path is used for path requests.
     *
     * The return type is the type of Item object based on the API integration.
     *
     * We add the "id" inside the curly brackets indicated that the path is
     * based on that id endpoint.
     */
    @GET("/albums/{id}")
    suspend fun getSingleAlbum(@Path(value = "id") albumId: Int): Response<AlbumsDataClassItem>

    /**
     * Now, we will show an example of a POST requests.
     *
     * @POST requests are used to send data to a server and the server typically will save
     * that data to the database.
     *
     * Post requests are very secure whereas @GET requests are not.
     *
     * We will send an item, that will send as the BODY of the POST requests.
     */
    @POST("/albums")
    suspend fun uploadAlbum(@Body album: AlbumsDataClassItem): Response<AlbumsDataClassItem>
}