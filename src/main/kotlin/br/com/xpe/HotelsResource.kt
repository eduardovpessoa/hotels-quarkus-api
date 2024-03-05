package br.com.xpe

import br.com.xpe.data.model.HotelDTO
import br.com.xpe.data.repository.HotelRepository
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.bson.types.ObjectId

@Path("/hotels")
class HotelsResource {

    @Inject
    lateinit var repository: HotelRepository

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    fun listHotels(): Response = Response.ok(repository.listAll()).build()

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @GET
    fun getHotelById(@PathParam("id") id: String): Response {
        return try {
            if (id.isNotEmpty() && id.length == 24) {
                val result = repository.findById(ObjectId(id))
                if (result == null) {
                    Response.status(Response.Status.NOT_FOUND).build()
                } else {
                    Response.ok(result).build()
                }
            } else {
                Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID! 24 characters required!").build()
            }
        } catch (ex: Exception) {
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.message.orEmpty()).build()
        }
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    fun addHotel(hotel: HotelDTO): Response {
        repository.persist(hotel.toEntity())
        return Response.status(Response.Status.CREATED).entity(repository.listAll()).build()
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @PUT
    fun updateHotel(@PathParam("id") id: String, hotel: HotelDTO): Response {
        repository.findById(ObjectId(id))?.let {
            it.name = hotel.name
            it.avgPrice = hotel.avgPrice
            it.status = hotel.status
            repository.update(it)
            return Response.ok(repository.listAll()).build()
        }
        return Response.status(Response.Status.NOT_FOUND).build()
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @DELETE
    fun deleteHotel(@PathParam("id") id: String): Response {
        repository.findById(ObjectId(id))?.let {
            repository.delete(it)
            return Response.ok(repository.listAll()).build()
        }
        return Response.status(Response.Status.NOT_FOUND).build()
    }
}