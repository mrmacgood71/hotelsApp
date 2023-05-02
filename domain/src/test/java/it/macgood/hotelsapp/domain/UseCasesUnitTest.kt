package it.macgood.hotelsapp.domain

import it.macgood.hotelsapp.domain.entyties.Hotel
import it.macgood.hotelsapp.domain.entyties.HotelDescription
import it.macgood.hotelsapp.domain.repository.HotelsRepository
import it.macgood.hotelsapp.domain.usecase.GetHotelUseCase
import it.macgood.hotelsapp.domain.usecase.GetHotelsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class UseCasesUnitTest {

    @Test
    fun shouldReturnBelleclaireHotel() = runTest {

        val repository = TestRepository()
        val response = repository.getHotelDescription("40611")
        val useCase = GetHotelUseCase(repository = TestRepository())
        val execute = useCase.execute("40611")

        assertEquals(response.body(), execute.body())
    }

    @Test
    fun shouldReturnAllHotels() = runTest {

        val repository = TestRepository()
        val response = repository.getHotels()
        val useCase = GetHotelsUseCase(repository = TestRepository())
        val execute = useCase.execute()

        assertEquals(response.body(), execute.body())
    }
}

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

