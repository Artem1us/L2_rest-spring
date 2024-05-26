package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ua.kpi.its.lab.rest.dto.MagazineRequest
import ua.kpi.its.lab.rest.dto.MagazineResponse
import ua.kpi.its.lab.rest.svc.MagazineService

@RestController
@RequestMapping("/magazines")
class MagazineController @Autowired constructor(
    private val magazineService: MagazineService
) {
    /**
     * Gets the list of all magazines
     *
     * @return: List of MagazineResponse
     */
    @GetMapping(path = ["", "/"])
    fun magazines(): List<MagazineResponse> = magazineService.read()

    /**
     * Reads the magazine by its id
     *
     * @param id: id of the magazine
     * @return: MagazineResponse for the given id
     */
    @GetMapping("{id}")
    fun readMagazine(@PathVariable("id") id: Long): ResponseEntity<MagazineResponse> {
        return wrapNotFound { magazineService.readById(id) }
    }

    /**
     * Creates a new magazine instance
     *
     * @param magazine: MagazineRequest with set properties
     * @return: MagazineResponse for the created magazine
     */
    @PostMapping(path = ["", "/"])
    fun createMagazine(@RequestBody magazine: MagazineRequest): MagazineResponse {
        return magazineService.create(magazine)
    }

    /**
     * Updates existing magazine instance
     *
     * @param magazine: MagazineRequest with properties set
     * @return: VMagazineResponse of the updated magazine
     */
    @PutMapping("{id}")
    fun updateMagazine(
        @PathVariable("id") id: Long,
        @RequestBody magazine: MagazineRequest
    ): ResponseEntity<MagazineResponse> {
        return wrapNotFound { magazineService.updateById(id, magazine)}
    }

    /**
     * Deletes existing magazine instance
     *
     * @param id: id of the magazine
     * @return: MagazineResponse of the deleted magazine
     */
    @DeleteMapping("{id}")
    fun deleteMagazine(
        @PathVariable("id") id: Long
    ): ResponseEntity<MagazineResponse> {
        return wrapNotFound { magazineService.deleteById(id) }
    }

    fun <T>wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        }
        catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}