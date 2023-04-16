package com.proyecto;

import com.proyecto.clases.EnumResultado;
import com.proyecto.clases.Equipo;
import com.proyecto.clases.Partido;
import com.proyecto.clases.Pronostico;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Leer resultados
        List<String> lineasResultados = null;

        String ruta ="src/test/resultados.csv";
        lineasResultados = obtenerCSV(ruta, "resultados");

        Collection<Partido> partidos = new ArrayList<Partido>();
        partidos = cargarPartidos(lineasResultados);

        // Leer pronostico
        ruta = "src/test/pronostico.csv";
        List<String> lineasPronostico = null;
        lineasPronostico = obtenerCSV(ruta, "pronostico");

        // Una vez cargados ambos archivos csv, calcular aciertos en la variable puntos
        int puntos = 0;
        puntos = obtenerPuntos(partidos, lineasPronostico);

        // Mostrar puntos
        String msj = "El usuario acertó " + puntos + " puntos ! ! !";
        String linea = "-".repeat(msj.length());

        System.out.println(linea  + "\n" + msj + "\n" + linea);
    }

    private static int obtenerPuntos(Collection<Partido> partidos, List<String> lineasPronostico) {
        int puntos = 0;
        boolean primera = true;
        for (String lineaPronostico : lineasPronostico) {
            if (primera) {
                primera = false;
            } else {
                String[] campos = lineaPronostico.split(",");
                Equipo equipo1 = new Equipo(campos[0]);
                Equipo equipo2 = new Equipo(campos[4]);
                Partido partido = null;

                for (Partido partidoCol : partidos) {
                    if (partidoCol.getEquipo1().getNombre(
                    ).equals(equipo1.getNombre())
                            && partidoCol.getEquipo2().getNombre(
                    ).equals(equipo2.getNombre())) {
                        partido = partidoCol;
                    }
                }
                Equipo equipo = null;
                EnumResultado resultado = null;
                if("X".equals(campos[1])) {
                    equipo = equipo1;
                    resultado = EnumResultado.GANADOR;
                }
                if("X".equals(campos[2])) {
                    equipo = equipo1;
                    resultado = EnumResultado.EMPATE;
                }
                if("X".equals(campos[3])) {
                    equipo = equipo1;
                    resultado = EnumResultado.PERDEDOR;
                }

                Pronostico pronostico = new Pronostico(partido, equipo, resultado);
                // sumar puntos, si corresponde
                puntos += pronostico.puntos();
            }
        }
        return puntos;
    }

    private static Collection<Partido> cargarPartidos(List<String> lineasResultados) {
        Collection<Partido> p =new ArrayList<Partido>();

        boolean primera = true;
        for (String linea : lineasResultados) {
            if (primera) {
                primera = false;
            } else {
                // Argentina,1,2,Arabia Saudita
                String[] campos = linea.split(",");
                Equipo equipo1 = new Equipo(campos[0]);
                Equipo equipo2 = new Equipo(campos[3]);
                Partido partido = new Partido(equipo1, equipo2);
                partido.setGolesEq1(Integer.parseInt(campos[1]));
                partido.setGolesEq2(Integer.parseInt(campos[2]));
                p.add(partido);
            }
        }
        return  p;
    }

    private static List<String> obtenerCSV(String ruta, String nombreArchivo) {
        Path path = Paths.get(ruta);
        List<String> lineas = null;
        try {
            lineas = Files.readAllLines(path);
            // Para controlar que se obtuvieron lineas
            /*
            System.out.println("lineas obtenidas de " + nombreArchivo +
                               ": " + lineas.size());
            */

        } catch (IOException e) {
            System.out.println("No se puede leer archivo " + nombreArchivo + ": " + e.getMessage());
        }
        return  lineas;
    }
}