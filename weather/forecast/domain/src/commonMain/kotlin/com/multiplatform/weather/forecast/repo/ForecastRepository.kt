package com.multiplatform.weather.forecast.repo

import com.multiplatform.td.core.repository.Repository
import com.multiplatform.weather.city.City
import com.multiplatform.weather.forecast.Forecast

typealias ForecastRepository = Repository<City, Forecast>
