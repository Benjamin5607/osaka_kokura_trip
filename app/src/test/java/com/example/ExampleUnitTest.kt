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
}
