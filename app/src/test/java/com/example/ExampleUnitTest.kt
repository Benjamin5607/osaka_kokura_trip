package com.example

import com.example.data.ContingencyData
import com.example.data.InitialTripData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testContingencyDataIntegrity() {
        // Verify alternative activities
        val activities = ContingencyData.alternativeActivities
        assertTrue("Alternative activities should be provided for all trip days", activities.size >= 6)
        activities.forEach {
            assertTrue(it.originalSpot.isNotBlank())
            assertTrue(it.alternativeTitle.isNotBlank())
            assertTrue(it.indoorAdvantage.isNotBlank())
            assertTrue(it.transitGuide.isNotBlank())
        }

        // Verify traffic links
        val trafficLinks = ContingencyData.trafficLinks
        assertTrue(trafficLinks.isNotEmpty())
        trafficLinks.forEach {
            assertTrue(it.url.startsWith("http"))
            assertTrue(it.delayTip.isNotBlank())
        }

        // Verify weather guides
        val weatherGuides = ContingencyData.weatherGuides
        assertEquals(3, weatherGuides.size) // Osaka, Kyoto, Kitakyushu

        // Verify emergency phrases
        val phrases = ContingencyData.emergencyPhrases
        assertTrue("Emergency phrases should cover vital situations", phrases.size >= 12)
        phrases.forEach {
            assertTrue(it.japaneseText.isNotBlank())
            assertTrue(it.pronunciation.isNotBlank())
            assertTrue(it.koreanTitle.isNotBlank())
        }

        // Verify default emergency contacts
        val contacts = InitialTripData.getDefaultEmergencyContacts()
        assertTrue(contacts.size >= 8)
        val hasEmbassy = contacts.any { it.category == "대사관/영사관" }
        val hasPolice = contacts.any { it.phoneNumber == "110" }
        val hasAmbulance = contacts.any { it.phoneNumber == "119" }
        assertTrue(hasEmbassy)
        assertTrue(hasPolice)
        assertTrue(hasAmbulance)
    }

    @Test
    fun testTripScheduleIntegrityAndKlookTour() {
        val schedules = InitialTripData.getDefaultSchedules()
        assertTrue("Schedule should contain at least 20 items across 6 days", schedules.size >= 20)

        // Day 2 must have the Klook Arashiyama Kyoto bus tour
        val day2Schedules = schedules.filter { it.dayNumber == 2 }
        assertTrue("Day 2 should have multiple tour spots", day2Schedules.isNotEmpty())
        val busTourItem = day2Schedules.find { it.title.contains("Klook") || it.title.contains("교토") }
        assertTrue("Day 2 should feature Klook Kyoto bus tour", busTourItem != null)
        assertTrue("Day 2 should include Arashiyama", day2Schedules.any { it.locationName.contains("아라시야마") || it.title.contains("아라시야마") })

        // Verify that sashimi (회, 사시미) and wagyu/beef (와규, 소고기) are actively included in Osaka schedules (Days 1~3)
        val osakaSchedules = schedules.filter { it.dayNumber in 1..3 }
        val hasSashimiInOsaka = osakaSchedules.any { it.restaurantMenu.contains("사시미") || it.restaurantMenu.contains("활어회") || it.restaurantMenu.contains("생선회") }
        val hasWagyuInOsaka = osakaSchedules.any { it.restaurantMenu.contains("와규") || it.restaurantMenu.contains("소고기") }
        assertTrue("Osaka schedule should actively feature sashimi for parents", hasSashimiInOsaka)
        assertTrue("Osaka schedule should actively feature wagyu/beef for parents", hasWagyuInOsaka)

        // Verify that offal (내장, 호르몬, 곱창) is strictly excluded from all menus
        schedules.forEach { item ->
            assertFalse("Should strictly exclude offal/hormone from recommended menu: ${item.restaurantMenu}", 
                item.restaurantMenu.contains("곱창") || item.restaurantMenu.contains("호르몬") || item.restaurantMenu.contains("대창"))
        }
    }

    @Test
    fun testOsakaMetroPassAndVisaContactless() {
        val schedules = InitialTripData.getDefaultSchedules()
        val checklists = InitialTripData.getDefaultChecklists()

        // 1. Day 1 should include Osaka Metro Pass 2-Day with booking code ZNZ343191
        val day1 = schedules.filter { it.dayNumber == 1 }
        val hasMetroPassDay1 = day1.any { it.transitGuide.contains("메트로패스") || it.kidsFriendlyTip.contains("메트로패스") }
        assertTrue("Day 1 should mention Metro Pass", hasMetroPassDay1)

        // 2. Day 3 should mention Visa contactless or 1-day pass
        val day3 = schedules.filter { it.dayNumber == 3 }
        val hasVisaOr1DayPass = day3.any { it.transitGuide.contains("비자") || it.kidsFriendlyTip.contains("비자") || it.transitGuide.contains("1일권") }
        assertTrue("Day 3 should guide on Visa contactless or 1-day pass", hasVisaOr1DayPass)

        // 3. Checklists should have the Metro Pass voucher and Visa Contactless card
        val hasMetroPassChecklist = checklists.any { it.title.contains("ZNZ343191") }
        val hasVisaChecklist = checklists.any { it.title.contains("컨택리스") || it.title.contains("비자") }
        assertTrue("Checklist should contain Metro Pass voucher with booking no", hasMetroPassChecklist)
        assertTrue("Checklist should contain Visa contactless card", hasVisaChecklist)
    }

    @Test
    fun testTripDatesAndDaysOfWeek() {
        val schedules = InitialTripData.getDefaultSchedules()

        val expectedDates = mapOf(
            1 to "9월 20일 (일)",
            2 to "9월 21일 (월)",
            3 to "9월 22일 (화)",
            4 to "9월 23일 (수)",
            5 to "9월 24일 (목)",
            6 to "9월 25일 (금)"
        )

        for ((day, expectedDate) in expectedDates) {
            val daySchedules = schedules.filter { it.dayNumber == day }
            assertTrue("Day $day should have schedules", daySchedules.isNotEmpty())
            daySchedules.forEach {
                assertEquals("Day $day must have date text $expectedDate", expectedDate, it.dateText)
            }
        }
    }
}
