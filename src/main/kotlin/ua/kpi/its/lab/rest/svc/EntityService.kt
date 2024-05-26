package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.MagazineRequest
import ua.kpi.its.lab.rest.dto.MagazineResponse

interface MagazineService {
    /**
     * Creates a new Magazine record.
     *
     * @param magazine: The VehicleRequest instance to be inserted
     * @return: The recently created VehicleResponse instance
     */
    fun create(magazine: MagazineRequest): MagazineResponse

    /**
     * Reads all created Vehicle records.
     *
     * @return: List of created VehicleResponse records
     */
    fun read(): List<MagazineResponse>

    /**
     * Reads a Magazine record by its id.
     * The order is determined by the order of creation.
     *
     * @param id: The id of <Magazine>Request record
     * @return: The MagazineResponse instance at index
     */
    fun readById(id: Long): MagazineResponse

    /**
     * Updates a MagazineRequest record data.
     *
     * @param id: The id of the Magazine instance to be updated
     * @param magazine: The MagazineRequest with new Magazine values
     * @return: The updated MagazineResponse record
     */
    fun updateById(id: Long, magazine: MagazineRequest): MagazineResponse

    /**
     * Deletes a MagazineRequest record by its index.
     * The order is determined by the order of creation.
     *
     * @param id: The id of Magazine record to delete
     * @return: The deleted MagazineResponse instance at index
     */
    fun deleteById(id: Long): MagazineResponse
}