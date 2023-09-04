package it.macgood.hotelsapp

import dagger.Provides
import it.macgood.hotelsapp.data.api.HotelsApi
import it.macgood.hotelsapp.data.repository.HotelsRepositoryImpl
import it.macgood.hotelsapp.domain.entyties.Hotel
import it.macgood.hotelsapp.domain.entyties.HotelDescription
import it.macgood.hotelsapp.domain.repository.HotelsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UseCaseUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun shouldReturnTheSameHotels() = runTest {

        val repository = TestRepository()
        val response = repository.getHotels()

        val serverRepository = HotelsRepositoryImpl(api)
        val serverResponse = serverRepository.getHotels()
        if (serverResponse.code() == 404) throw ConnectException()
        if (serverResponse.body() == null) throw NullPointerException()

        Assert.assertEquals(
            response.body(),
            serverResponse.body()
        )
    }

}

val retrofit = Retrofit.Builder()
    .baseUrl("https://raw.githubusercontent.com/iMofasa/ios-android-test/master/")
    .client(provideOkHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

@Provides
@Singleton
fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return logging
}

fun provideOkHttpClient() = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.SECONDS)
    .addInterceptor(provideHttpLoggingInterceptor())
    .readTimeout(1, TimeUnit.SECONDS)
    .build()

val api = retrofit.create(HotelsApi::class.java)

class TestRepository : HotelsRepository {
    override suspend fun getHotels(): Response<List<Hotel>> {
        val hotels: List<Hotel> = fakeHotelsList()
        return Response.success(hotels)
    }

    override suspend fun getHotelDescription(hotelId: String): Response<HotelDescription> {
        return Response.success(
            HotelDescription(
                id = 40611,
                address = "250 West 77th Street, Manhattan",
                distance = 100.0,
                image = "1.jpg",
                latitude = 40.78260000000000,
                longitude = -73.98130000000000,
                name = "Belleclaire Hotel",
                stars = 3.0,
                suitesAvailability = "1:44:21:87:99:34",
            )
        )
    }


    private fun fakeHotelsList(): List<Hotel> {
        val hotels: List<Hotel> = listOf(
            Hotel(
                id = 40611,
                name = "Belleclaire Hotel",
                address = "250 West 77th Street, Manhattan",
                stars = 3.0,
                distance = 100.0,
                suitesAvailability = "1:44:21:87:99:34"
            ),
            Hotel(
                id = 80899,
                name = "Americana Inn",
                address = "69 West 38th Street",
                stars = 2.0,
                distance = 99.9,
                suitesAvailability = "5:8:32:54"
            ),
            Hotel(
                id = 13100,
                name = "Best Western President Hotel at Times Square",
                address = "234 West 48th Street",
                stars = 3.0,
                distance = 13.10,
                suitesAvailability = "1"
            ),
            Hotel(
                id = 22470,
                name = "Days Hotel Broadway at 94th Street",
                address = "215 West 94th Street",
                stars = 1.0,
                distance = 999.9,
                suitesAvailability = "15:48:115:72:81"
            ),
            Hotel(
                id = 85862,
                name = "Dream",
                address = "210 W. 55 STREET, NEW YORK NY 10019, UNITED STATES",
                stars = 4.0,
                distance = 554.4,
                suitesAvailability = "42:33:22"
            ),
            Hotel(
                id = 313499,
                name = "Dream Downtown",
                address = "355 West 16th Street",
                stars = 0.0,
                distance = 716.06,
                suitesAvailability = "2:87:24:65:26:119:202:6"
            ),
            Hotel(
                id = 13370,
                name = "Midtown Lodging",
                address = "250 East 50th Street",
                stars = 2.0,
                distance = 13.10,
                suitesAvailability = "1:"
            )
        )
        return hotels
    }

}