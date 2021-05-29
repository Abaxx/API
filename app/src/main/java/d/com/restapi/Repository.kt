package d.com.restapi

class Repository(private val apiService: ApiService) {
    suspend fun getResuilts(results: String) = apiService.getResults(results)
}