# DEMO - Culqi Kotlin + Checkout V4 + Culqi 3DS

La demo integra Culqi Kotlin, Checkout V4 , Culqi 3DS y es compatible con la v2.0 del Culqi API, con esta demo podrás generar cargos y ordenes.

## Requisitos

* WebHosting
* Kotlin 1.9.0 +
* Backend para generar cargos y órdenes.
* Afiliate [aquí](https://afiliate.culqi.com/).
* Si vas a realizar pruebas obtén tus llaves desde [aquí](https://integ-panel.culqi.com/#/registro), si vas a realizar transacciones reales obtén tus llaves desde [aquí](https://panel.culqi.com/#/registro) (1).

> Recuerda que para obtener tus llaves debes ingresar a tu CulqiPanel > Desarrollo > ***API Keys***.

![alt tag](http://i.imgur.com/NhE6mS9.png)

> Recuerda que las credenciales son enviadas al correo que registraste en el proceso de afiliación.

## Configuración

### Configurar las pruebas y ejemplos

El código y su historial usan placeholders en lugar de credenciales:

| Placeholder | Configuración |
| --- | --- |
| `INSERTE_SU_LLAVE_PUBLICA` | Tu llave pública de pruebas (`pk_test_`), para Checkout y Culqi 3DS. |
| `INSERTE_SU_LLAVE_PRIVADA` | Tu llave privada de pruebas (`sk_test_`), exclusivamente en tu backend. |
| `INSERTE_SU_LLAVE_RSA` | Tu llave RSA, si tu integración requiere cifrado. |
| `INSERTE_SU_RSA_ID` | El identificador de tu llave RSA, si corresponde. |

Obtén tus propias llaves de prueba desde CulqiPanel. No hagas commit de
credenciales ni las publiques en el README, HTML, APK o historial Git.
Los placeholders no son credenciales válidas: las operaciones de cargos y
órdenes deben delegarse a tu backend, que conserva la SK y llama a Culqi.
No reemplaces `INSERTE_SU_LLAVE_PRIVADA` por una llave real en
`checkoutv4.html` ni en `MainActivity.kt`, tampoco para pruebas.
Las llamadas configuradas contra Culqi son reales; usa solo datos y llaves
de prueba en un entorno controlado.

Configura tu propio hosting y sustituye su URL tanto en `browser.loadUrl`
de `MainActivity.kt` como en `returnUrl` de `checkoutv4.html`. La URL de
ejemplo incluida no es un servicio mantenido por este repositorio.

Dentro de la carpeta **assets** encontraras un archivo con el nombre checkoutv4.html donde modificaremos las siguientes lineas:

```html
 Culqi3DS.publicKey = "INSERTE_SU_LLAVE_PUBLICA";
 Culqi.publicKey = 'INSERTE_SU_LLAVE_PUBLICA';
```

En dichas lineas estamos asignando nuestra llave pública(pk), tanto a la configuración del checkoutv4 asi como a la del Culqi 3DS. La autorización para cargos y órdenes se configura exclusivamente en tu backend:

> Importante: No debes colocar tu llave privada(sk) dentro de tu proyecto front.

```javascript
"Authorization": "Bearer INSERTE_SU_LLAVE_PRIVADA"
```

Luego debemos cargar el checkoutv4.html y el archivo jquery.min.js a nuestro webhosting.
Subido los archivos deberemos tener una ruta parecida a la siguiente:

https://{tudominio}/checkoutv4.html

Luego, en el archivo MainActivity.kt colocamos dicha ruta en la siguiente parte de código.


```kotlin
browser.loadUrl("https://{tudominio}/checkoutv4.html")
```

Tambien remplazamos esa url en el archivo checkoutv4.html, esto es necesario para una correcta configuración de Culqi 3DS.

```javascript
returnUrl: "https://{tudominio}/checkoutv4.html"
```


## Inicializar la demo

Para inicializar la demo en AndroidStudio primero debemos seleccionar el emulador o celular donde se levantará la aplicación y pulsar el botón run.


## Probar la demo

Para poder visualizar la demo debemos generar un apk desde el menu Build/Build Bundle(s)/APK(s) de AndroidStudio, luego proceder a instalarlo en algún emulador o dispositivo celular.


## Importante para producción

No debes configurar tu llave privada(sk) dentro del proyecto. Los ejemplos de autorización en `checkoutv4.html` y `MainActivity.kt` contienen únicamente placeholders; no los reemplaces por llaves reales.
Debes desarrollar un backend que conserve tu SK y consuma los servicios de [cargos](https://apidocs.culqi.com/#tag/Cargos/operation/crear-cargo) y [órdenes](https://apidocs.culqi.com/#tag/Ordenes/operation/crear-orden) de Culqi. Tu aplicación Android y el HTML deben consumir ese backend en lugar de enviar directamente operaciones que requieran una SK.
