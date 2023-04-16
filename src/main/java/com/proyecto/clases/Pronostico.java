package com.proyecto.clases;

public class Pronostico {
    private Partido partido;
    private Equipo equipo;
    private EnumResultado resultado;

    public Pronostico(Partido partido, Equipo equipo, EnumResultado resultado) {
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
    }

    public Partido getPartido() {
        return this.partido;
    }

    public Equipo getEquipo() {
        return this.equipo;
    }

    public EnumResultado getResultado() {
        return this.resultado;
    }

    public int puntos() {
        // this.resultado -> pred
        //comparo resultado con pronostico (resultadoReal)
        EnumResultado resultadoReal = this.partido.resultado(this.equipo);
        if (this.resultado.equals(resultadoReal)) {
            return 1;
        } else {
            return 0;
        }
    }
}
