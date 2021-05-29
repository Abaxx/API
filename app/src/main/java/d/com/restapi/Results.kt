package d.com.restapi

import com.squareup.moshi.Json

data class Results(
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: String,
        @Json(name = "species") val species: String,
        @Json(name = "image") val image: String
                      )
