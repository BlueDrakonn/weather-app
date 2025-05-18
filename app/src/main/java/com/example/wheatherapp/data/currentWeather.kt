package com.example.wheatherapp.data

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current
)


data class Location (

    @SerializedName("name"            ) var name           : String? = null,
    @SerializedName("region"          ) var region         : String? = null,
    @SerializedName("country"         ) var country        : String? = null,
    @SerializedName("lat"             ) var lat            : Double? = null,
    @SerializedName("lon"             ) var lon            : Double? = null,
    @SerializedName("tz_id"           ) var tzId           : String? = null,
    @SerializedName("localtime_epoch" ) var localtimeEpoch : Double?    = null,
    @SerializedName("localtime"       ) var localtime      : String? = null

)

data class Condition (

    @SerializedName("text" ) var text : String? = null,
    @SerializedName("icon" ) var icon : String? = null,
    @SerializedName("code" ) var code : Int?    = null

)

data class Current (

    @SerializedName("last_updated_epoch" ) var lastUpdatedEpoch : Double?       = null,
    @SerializedName("last_updated"       ) var lastUpdated      : String?    = null,
    @SerializedName("temp_c"             ) var tempC            : Double?    = null,
    @SerializedName("is_day"             ) var isDay            : Int?       = null,
    @SerializedName("wind_kph"           ) var windKph          : Double?    = null,
    @SerializedName("wind_degree"        ) var windDegree       : Double?       = null,
    @SerializedName("wind_dir"           ) var windDir          : String?    = null,
    @SerializedName("pressure_mb"        ) var pressureMb       : Double?       = null,
    @SerializedName("pressure_in"        ) var pressureIn       : Double?    = null,
    @SerializedName("precip_mm"          ) var precipMm         : Double?       = null,
    @SerializedName("precip_in"          ) var precipIn         : Double?       = null,
    @SerializedName("humidity"           ) var humidity         : Int?       = null,
    @SerializedName("cloud"              ) var cloud            : Int?       = null,
    @SerializedName("feelslike_c"        ) var feelslikeC       : Double?       = null,
    @SerializedName("windchill_c"        ) var windchillC       : Double?    = null,
    @SerializedName("heatindex_c"        ) var heatindexC       : Double?    = null,
    @SerializedName("dewpoint_c"         ) var dewpointC        : Double?    = null,
    @SerializedName("vis_km"             ) var visKm            : Double?       = null,
    @SerializedName("gust_mph"           ) var gustMph          : Double?    = null,
    @SerializedName("gust_kph"           ) var gustKph          : Double?    = null

)

