package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.ScientificArticleResponse
import ua.kpi.its.lab.rest.dto.MagazineRequest
import ua.kpi.its.lab.rest.dto.MagazineResponse
import ua.kpi.its.lab.rest.entity.ScientificArticle
import ua.kpi.its.lab.rest.entity.Magazine
import ua.kpi.its.lab.rest.repo.MagazineRepository
import ua.kpi.its.lab.rest.svc.MagazineService
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrElse


@Service
class MagazineServiceImpl @Autowired constructor(
    private val repository: MagazineRepository
): MagazineService {
    override fun create(magazine: MagazineRequest): MagazineResponse {
        val scientificArticle = magazine.scientificArticle
        val newScientificArticle = ScientificArticle(
            title = scientificArticle.title,
            author = scientificArticle.author,
            writingDate = scientificArticle.writingDate,
            wordCount = scientificArticle.wordCount,
            numberLinks = scientificArticle.numberLinks,
//            numberLinks = this.stringToDate(scientificArticle.numberLinks),
//            chargeTime = scientificArticle.chargeTime,
            origLang = scientificArticle.origLang
        )
        var newMagazine = Magazine(
            name = magazine.name,
            topic = magazine.topic,
            language = magazine.language,
//            establishDate = magazine.establishDate,
            establishDate = this.stringToDate(magazine.establishDate),
            issn = magazine.issn,
            price = this.stringToPrice(magazine.price),
            periodicity = magazine.periodicity,
            scientificArticle = newScientificArticle
        )
        newScientificArticle.magazine = newMagazine
        newMagazine = this.repository.save(newMagazine)
        val magazineResponse = this.magazineEntityToDto(newMagazine)
        return magazineResponse
    }

    override fun read(): List<MagazineResponse> {
        return this.repository.findAll().map(this::magazineEntityToDto)
    }

    override fun readById(id: Long): MagazineResponse {
        val magazine = this.getMagazineById(id)
        val magazineResponse = this.magazineEntityToDto(magazine)
        return magazineResponse
    }

    override fun updateById(id: Long, magazine: MagazineRequest): MagazineResponse {
        val oldMagazine = this.getMagazineById(id)
        val scientificArticle = magazine.scientificArticle

        oldMagazine.apply {
            name = magazine.name
            topic = magazine.topic
            language = magazine.language
            establishDate = this@MagazineServiceImpl.stringToDate(magazine.establishDate)
            issn = magazine.issn
            price = this@MagazineServiceImpl.stringToPrice(magazine.price)
            periodicity = magazine.periodicity
        }
        oldMagazine.scientificArticle.apply {
            title = scientificArticle.title
            author = scientificArticle.author
            writingDate = scientificArticle.writingDate
            wordCount = scientificArticle.wordCount
//            manufactureDate = this@MagazineServiceImpl.stringToDate(scientificArticle.manufactureDate)
            numberLinks = scientificArticle.numberLinks
            origLang = scientificArticle.origLang
        }
        val newMagazine = this.repository.save(oldMagazine)
        val magazineResponse = this.magazineEntityToDto(newMagazine)
        return magazineResponse
    }

    override fun deleteById(id: Long): MagazineResponse {
        val magazine = this.getMagazineById(id)
        this.repository.delete(magazine)
        val magazineResponse = magazineEntityToDto(magazine)
        return magazineResponse
    }

    private fun getMagazineById(id: Long): Magazine {
        return this.repository.findById(id).getOrElse {
            throw IllegalArgumentException("Magazine not found by id = $id")
        }
    }

    private fun magazineEntityToDto(magazine: Magazine): MagazineResponse {
        return MagazineResponse(
            id = magazine.id,
            name = magazine.name,
            topic = magazine.topic,
            language = magazine.language,
            establishDate = this.dateToString(magazine.establishDate),
            issn = magazine.issn,
            price = this.priceToString(magazine.price),
            periodicity = magazine.periodicity,
            scientificArticle = this.scientificArticleEntityToDto(magazine.scientificArticle)
        )
    }

    private fun scientificArticleEntityToDto(scientificArticle: ScientificArticle): ScientificArticleResponse {
        return ScientificArticleResponse(
            id = scientificArticle.id,
            title = scientificArticle.title,
            author = scientificArticle.author,
            writingDate = scientificArticle.writingDate,
            wordCount = scientificArticle.wordCount,
            //manufactureDate = this.dateToString(scientificArticle.manufactureDate),
            numberLinks = scientificArticle.numberLinks,
            origLang = scientificArticle.origLang
        )
    }

    private fun dateToString(date: Date): String {
        val instant = date.toInstant()
        val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    private fun stringToDate(date: String): Date {
        val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val instant = dateTime.toInstant(ZoneOffset.UTC)
        return Date.from(instant)
    }

    private fun priceToString(price: BigDecimal): String = price.toString()

    private fun stringToPrice(price: String): BigDecimal = BigDecimal(price)
}