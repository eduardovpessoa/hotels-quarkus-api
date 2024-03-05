package br.com.xpe.data.entity

import br.com.xpe.data.model.HotelDTO
import io.quarkus.mongodb.panache.common.MongoEntity
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@MongoEntity(collection = "hotels")
data class HotelEntity @BsonCreator constructor(
    @BsonId val id: ObjectId,
    @BsonProperty("name") var name: String,
    @BsonProperty("avgPrice") var avgPrice: Double,
    @BsonProperty("status") var status: Boolean
) {
    fun toDTO(): HotelDTO {
        return HotelDTO(
            id = this.id.toString(),
            name = this.name,
            avgPrice = this.avgPrice,
            status = this.status
        )
    }
}