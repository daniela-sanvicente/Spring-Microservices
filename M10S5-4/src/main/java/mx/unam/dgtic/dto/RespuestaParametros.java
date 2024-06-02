package mx.unam.dgtic.dto;

public class RespuestaParametros {
    private Parametros parametros;

    public RespuestaParametros(String p1, String p2) {
        this.parametros = new Parametros(p1, p2);
    }

    public RespuestaParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public static class Parametros{
        private String p1;
        private String p2;

        public Parametros(String p1, String p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }
    }
}
