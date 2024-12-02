import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConversorMoedas {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        try {
            String moedaBase = "USD"; // Moeda base
            String moedaDestino = "EUR"; // Moeda de destino
            double valor = 100; // Valor a ser convertido

            double resultado = converterMoeda(moedaBase, moedaDestino, valor);
            System.out.printf("Resultado: %.2f %s\n", resultado, moedaDestino);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double converterMoeda(String base, String destino, double valor) throws Exception {
        String urlString = API_URL + base;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonResponse = response.toString();
        double taxa = extrairTaxa(jsonResponse, destino);
        return valor * taxa;
    }

    private static double extrairTaxa(String json, String moedaDestino) {
        // Aqui você deve usar uma biblioteca JSON para extrair a taxa
        // Exemplo: usar Gson ou org.json
        int startIndex = json.indexOf(moedaDestino) + 5; // 5 é o tamanho da string "EUR": "
        int endIndex = json.indexOf(",", startIndex);
        return Double.parseDouble(json.substring(startIndex, endIndex));
    }
}