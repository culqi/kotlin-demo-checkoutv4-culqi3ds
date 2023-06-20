package culqi.com.democheckoutv4

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        WebView webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/checkoutv4.html");
        */
    }

    fun loadPage(view: View?) {
        val browser = WebView(this)
        browser.settings.javaScriptEnabled = true
        browser.webChromeClient = object : WebChromeClient() {}
        //browser.loadUrl("file:///android_asset/jquery.min.js");
       //browser.loadUrl("file:///android_asset/checkoutv6.html")
        browser.loadUrl("https://jordandiaz1988.000webhostapp.com/checkoutv6.html")
        setContentView(browser)
        val ws = browser.settings
        ws.javaScriptEnabled = true
        ws.domStorageEnabled = true
        browser.addJavascriptInterface(JavaScriptInterface(this), "Android")
        browser.addJavascriptInterface(JavaScriptInterface2(), "AndroidInterface")
        browser.addJavascriptInterface(JavaScriptInterface3(this), "AndroidInterfaceMessage")
        browser.addJavascriptInterface(JavaScriptInterface4(this), "AndroidInterfaceMessageError")
        browser.evaluateJavascript("Culqi.close();") { value ->
            // Verificar si Culqi.close() se ejecutó y realizar la lógica necesaria
            if (value != null && value == "\"Culqi close ejecutado\"") {
                // Culqi.close() se ejecutó, realiza la lógica necesaria aquí
                // Ejemplo: notificar que se ejecutó Culqi.close()
                runOnUiThread { // Notificar al código Android que se ejecutó Culqi.close()
                    browser.loadUrl("javascript:AndroidInterface.onCulqiClose()")
                }
            }
        }

        browser.evaluateJavascript("console.log(\"Llamado Exitoso\");") { value ->
            // Verificar si Culqi.close() se ejecutó y realizar la lógica necesaria
            if (value != null && value == "\"Culqi close ejecutado\"") {
                // Culqi.close() se ejecutó, realiza la lógica necesaria aquí
                // Ejemplo: notificar que se ejecutó Culqi.close()
                runOnUiThread { // Notificar al código Android que se ejecutó Culqi.close()
                    browser.loadUrl("javascript:AndroidInterfaceMessage.onCulqiMenssage()")
                }
            }
        }

        browser.evaluateJavascript("console.log(\"Llamado con error\");") { value ->
            // Verificar si Culqi.close() se ejecutó y realizar la lógica necesaria
            if (value != null && value == "\"Culqi close ejecutado\"") {
                // Culqi.close() se ejecutó, realiza la lógica necesaria aquí
                // Ejemplo: notificar que se ejecutó Culqi.close()
                runOnUiThread { // Notificar al código Android que se ejecutó Culqi.close()
                    browser.loadUrl("javascript:AndroidInterfaceMessageError.onCulqiMenssageError()")
                }
            }
        }

        /*browser.addJavascriptInterface( new Object() {
            @JavascriptInterface // For API 17+
            public void performClick (String strl) {
                Toast. makeText (MainActivity. this, strl , Toast. LENGTH_SHORT ).show() ;
            }
        } , "ok" ) ;*/
    }

    inner class JavaScriptInterface2 {
        @JavascriptInterface
        fun onCulqiClose() {
            // Se ejecutó Culqi.close(), realiza la lógica necesaria aquí
            // Ejemplo: notificar que se ejecutó Culqi.close()
            runOnUiThread {
                // En tu actividad
                val intent = intent
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // ...
        }
    }

    inner class JavaScriptInterface3  internal constructor(var mContext: Context) {

        @JavascriptInterface
        fun onCulqiMenssage() {
            // Se ejecutó Culqi.close(), realiza la lógica necesaria aquí
            // Ejemplo: notificar que se ejecutó Culqi.close()
            runOnUiThread {
                Toast.makeText(mContext, "Cargo Realizado Correctamente", Toast.LENGTH_SHORT).show()
                val intent = intent
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // ...
        }
    }

    inner class JavaScriptInterface4  internal constructor(var mContext: Context) {

        @JavascriptInterface
        fun onCulqiMenssageError() {
            // Se ejecutó Culqi.close(), realiza la lógica necesaria aquí
            // Ejemplo: notificar que se ejecutó Culqi.close()
            runOnUiThread {
                Toast.makeText(mContext, "Error al Realizar Cargo", Toast.LENGTH_SHORT).show()
                val intent = intent
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // ...
        }
    }

    inner class JavaScriptInterface internal constructor(var mContext: Context) {
        @JavascriptInterface
        fun sendParamsCheckoutv4FromAndroid(): String {
            val sdc = SendDataToCheckout()
            sdc.title = "Tienda Android Pruebas"
            sdc.amount = "1000" // 150.00
            var json = ""
            val jsonBody =
                "{\"amount\": 1000, \"currency_code\": \"PEN\", \"description\": \"Venta de prueba\", \"order_number\": \"pedido-97u8byc3c884sy4a33m3232323\", \"redirectPath\": \"google.com\", \"backUrl\": \"google.com\", \"client_details\": { \"first_name\": \"Richard\", \"last_name\": \"Hendricks\", \"email\": \"richard@piedpiper.com\", \"phone_number\": \"+51945145280\" }, \"expiration_date\": 1685828077}"
            val gson = Gson()
            try {
                val url = URL("https://qa-api.culqi.xyz/v2/orders")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                //Se recomienda no poner la llave sk directamente en el codigo fuente por temas de pruebas se coloco aqui
                connection.setRequestProperty("Authorization", "Bearer sk_test_281ae76a3127fbe8")
                connection.doOutput = true
                val outputStream = connection.outputStream
                outputStream.write(jsonBody.toByteArray())
                outputStream.flush()
                outputStream.close()
                val responseCode = connection.responseCode
                return if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    val responseData = StringBuilder()
                    while (reader.readLine().also { line = it } != null) {
                        responseData.append(line)
                    }
                    reader.close()
                    Log.d("Tag", "Este es un mensaje de depuración")
                    val jsonResponse = responseData.toString()
                    val parser = JsonParser()
                    val jsonObject = parser.parse(jsonResponse).asJsonObject
                    val order = jsonObject["id"].asString
                    sdc.orderId = order
                    json = gson.toJson(sdc)
                    json
                    //return responseData.toString();
                } else {
                    json = gson.toJson(sdc)
                    json
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            json = gson.toJson(sdc)
            return json
        }
    }

}