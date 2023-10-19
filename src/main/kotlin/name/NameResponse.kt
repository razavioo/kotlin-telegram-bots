package name

import kotlinx.serialization.Serializable

@Serializable
data class GenderizeResponse(val name: String, val gender: String)

@Serializable
data class AgifyResponse(val name: String, val age: Int)

@Serializable
data class NationalizeResponse(val name: String, val country: List<CountryInfo>)

@Serializable
data class CountryInfo(val country_id: String, val probability: Float)