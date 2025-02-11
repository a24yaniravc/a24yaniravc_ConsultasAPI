import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable


@Serializable
data class Root(
    val listaEstacionsMeteo: List<ListaEstacionsMeteo>,
)


@Serializable
data class ListaEstacionsMeteo(
    val altitude: Double,
    val concello: String,
    val estacion: String,
    val idEstacion: Long,
    val lat: Double,
    val lon: Double,
    val provincia: String,
    val utmx: String,
    val utmy: String,
)


fun main() {
    // Crear cliente HTTP
    val client = HttpClient.newHttpClient()


    // Crear solicitud
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://servizos.meteogalicia.gal/mgrss/observacion/listaEstacionsMeteo.action"))
        .GET()
        .build()


    // Enviar la solicitud con el cliente
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())


    // Obtener string con datos
    val jsonBody = response.body()


    // Deserializar el JSON a un objeto Root
    val root: Root = Json.decodeFromString(jsonBody)


    // Obtener la lista de estaciones meteorológicas
    val listaEstacionsMeteo = root.listaEstacionsMeteo


    // Imprimir las estaciones meteorológicas
    println("Lista de estaciones meteorológicas de Meteogalicia:")
    listaEstacionsMeteo.forEach { listaEstacionMeteo ->
        println(
            "Altitude: ${listaEstacionMeteo.altitude}, " +
                    "Concello: ${listaEstacionMeteo.concello}, " +
                    "Estación meteorológica: ${listaEstacionMeteo.estacion}, " +
                    "Id Estación: ${listaEstacionMeteo.idEstacion}, " +
                    "Latitud: ${listaEstacionMeteo.lat}, " +
                    "Longitud: ${listaEstacionMeteo.lon}, " +
                    "Provincia: ${listaEstacionMeteo.provincia}, " +
                    "UTMX: ${listaEstacionMeteo.utmx}, " +
                    "UTMY: ${listaEstacionMeteo.utmy}"
        )
    }
}

