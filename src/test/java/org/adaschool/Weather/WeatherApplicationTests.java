package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherApplicationTests {

	private MockMvc mockMvc;

	// Mocks para pruebas de controlador
	@Mock
	WeatherReportService weatherReportService;

	@InjectMocks
	WeatherReportController weatherReportController;

	@Test
	void contextLoads() {
		// Verificar que el contexto de la aplicación cargue correctamente
	}

	@Test
	public void testGetWeatherReportService() {
		// Simular respuesta del API
		WeatherReport report = new WeatherReport();
		report.setTemperature(25.0);
		report.setHumidity(60.0);

		// Simular la llamada del servicio
		when(weatherReportService.getWeatherReport(37.8267, -122.4233)).thenReturn(report);

		// Ejecutar el método y validar los resultados
		WeatherReport result = weatherReportService.getWeatherReport(37.8267, -122.4233);
		assertEquals(25.0, result.getTemperature());
		assertEquals(60.0, result.getHumidity());
	}

	@Test
	public void testGetWeatherReportController() throws Exception {
		// Simular respuesta del servicio
		WeatherReport report = new WeatherReport();
		report.setTemperature(25.0);
		report.setHumidity(60.0);

		when(weatherReportService.getWeatherReport(37.8267, -122.4233))
				.thenReturn(report);

		// Configurar MockMvc para simular las solicitudes HTTP
		mockMvc = MockMvcBuilders.standaloneSetup(weatherReportController).build();

		// Simular una solicitud GET y verificar la respuesta
		mockMvc.perform(get("/v1/api/weather-report")
						.param("latitude", "37.8267")
						.param("longitude", "-122.4233"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.temperature").value(25.0))
				.andExpect(jsonPath("$.humidity").value(60.0));
	}
}
