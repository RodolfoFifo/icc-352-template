package org.example;

import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static HttpResponse<String> get(String uri) throws Exception {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response;

        } catch (MalformedURLException e) {
            return null;
        } catch (URISyntaxException e) {
            return null;
        }

    }

    public static void main(String[] args) throws Exception {


        System.out.println("Practica 1");
        System.out.println("Ingrese la url: ");

        String url;
        Scanner scanner = new Scanner(System.in);
        url = scanner.next();

        HttpResponse<String> respuesta = get(url);





        if (respuesta != null){


            System.out.println("\nA- Indicar el tipo de recurso que estamos seleccionando  \n" );
            Optional<String> contentType = respuesta.headers().firstValue("Content-Type");
            contentType.ifPresent(type -> System.out.println("Content-Type: " + type));

            //1. Indicar la cantidad de lineas del recurso retornado.
            System.out.println("\nB- \n1. Indicar la cantidad de lineas del recurso retornado. \n" + respuesta.body().split("\n").length );

            try {

                Document documento = Jsoup.connect(url).get();

                System.out.println("\n2. Indicar la cantidad de párrafos (p) que contiene el documento HTML. \n" + documento.getElementsByTag("p").size());

                System.out.println("\n3. Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML \n" + documento.select("p img").size());


                System.out.println("\n4. indicar la cantidad de formularios (form) que contiene el HTML por categorizando por el método implementado POST o GET\n");

                System.out.println("GET = " + documento.select("form[method='get']").size());

                System.out.println("POST = " + documento.select("form[method='post']").size());

                System.out.println("\n5. Para cada formulario mostrar los campos del tipo input y su respectivo tipo que contiene en el documento HTML. \n");

                int campo = 1;
                int forms = 1;
                ArrayList<String> lista = new ArrayList<>();

                for (FormElement misforms : documento.getElementsByTag("form").forms()) {
                    for (Element camposInput : misforms.getElementsByTag("input")) {
                        lista.add(" -> Formulario # " + forms + " input # " + campo + " -tipo " + camposInput.attr("type") + "- \n");
                        campo++;
                    }
                    forms++;

                }

                for (String formulario : lista) {
                    System.out.println(formulario);
                }

                System.out.println("\n6. Para cada formulario parseado, identificar que el método de \n" +
                        "envío del formulario sea POST y enviar una petición al servidor \n" +
                        "con el parámetro llamado asignatura y valor practica1 y un \n" +
                        "header llamado matricula-id con el valor correspondiente a \n" +
                        "matrícula o id asignado. \n");

                String myurl;
                Document midocumento;

                for (Element miform : documento.select("form[method='post']")) {

                    myurl = miform.absUrl("action");

                    try {
                        midocumento = Jsoup.connect(myurl).data("asignatura", "practica1").header("matricula", "2018-1601").post();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Resultado: \n" + midocumento.body().toString());


                }
            }catch (UnsupportedMimeTypeException e) {

                System.out.println("Error en manejo del tipo de contenido, intente nuevamente.\n\n" + e);

            }


        }else {
            System.out.println("Intente nuevamente con una url valida.");
        }



    }
}