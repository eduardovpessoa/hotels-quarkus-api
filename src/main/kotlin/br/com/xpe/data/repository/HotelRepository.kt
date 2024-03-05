package br.com.xpe.data.repository

import br.com.xpe.data.entity.HotelEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class HotelRepository : PanacheMongoRepository<HotelEntity>