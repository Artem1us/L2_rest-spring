package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.MagazineRequest
import ua.kpi.its.lab.rest.dto.MagazineResponse

interface MagazineService {

    fun create(magazine: MagazineRequest): MagazineResponse

    fun read(): List<MagazineResponse>

    fun readById(id: Long): MagazineResponse

    fun updateById(id: Long, magazine: MagazineRequest): MagazineResponse

    fun deleteById(id: Long): MagazineResponse
}