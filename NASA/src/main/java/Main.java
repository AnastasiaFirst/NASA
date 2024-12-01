import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

    public static final String REMOTE_SERVICE_URI = "https://api.nasa.gov/planetary/apod?api_key=htMpnjSics9XB45fjrTWQuffA5TI9FWi3CzFppWV";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = createHttpClient();
            // Первый HTTP-запрос
            Data data = makeHttpRequest(httpClient, REMOTE_SERVICE_URI);

            // Получаем URL и делаем второй HTTP-запрос
            if (data != null && data.getUrl() != null) {
                String url = data.getUrl();
                System.out.println("Полученный URL: " + url);
                byte[] responseBody = makeHttpRequestAndGetResponseBody(httpClient, url);

                // Сохраняем тело ответа в файл
                saveResponseToFile(url, responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

    private static Data makeHttpRequest(CloseableHttpClient httpClient, String url) throws IOException {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                return mapper.readValue(response.getEntity().getContent(), new TypeReference<Data>() {
                });
            } else {
                System.out.println("Ошибка при выполнении запроса: " + response.getStatusLine().getStatusCode());
                return null;
            }
        }
    }

    private static byte[] makeHttpRequestAndGetResponseBody(CloseableHttpClient httpClient, String url) throws IOException {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                return response.getEntity().getContent().readAllBytes();
            } else {
                System.out.println("Ошибка при выполнении второго запроса: " + response.getStatusLine().getStatusCode());
                return null;
            }
        }
    }

    private static void saveResponseToFile(String url, byte[] responseBody) {
        String fileName = url.substring(url.lastIndexOf('/') + 1); // Получаем часть URL после последнего слэша
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName))) {
            fileOutputStream.write(responseBody);
            System.out.println("Ответ сохранен в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
