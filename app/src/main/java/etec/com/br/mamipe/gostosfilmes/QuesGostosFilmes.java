package etec.com.br.mamipe.gostosfilmes;

public class QuesGostosFilmes {

    private int codGostos;
    private String FilmeFavorito;
    private String GeneroFavorito;
    private String FilmeOdiado;
    private String GeneroOdiado;
    private String AtorFavorito;
    private String FilmeSequencia;
    private String codCliente;

    public String getFilmeFavorito() {
        return FilmeFavorito;
    }

    public void setFilmeFavorito(String filmeFavorito) {
        FilmeFavorito = filmeFavorito;
    }

    public String getGeneroFavorito() {
        return GeneroFavorito;
    }

    public void setGeneroFavorito(String generoFavorito) {
        GeneroFavorito = generoFavorito;
    }

    public String getFilmeOdiado() {
        return FilmeOdiado;
    }

    public void setFilmeOdiado(String filmeOdiado) {
        FilmeOdiado = filmeOdiado;
    }

    public String getGeneroOdiado() {
        return GeneroOdiado;
    }

    public void setGeneroOdiado(String generoOdiado) {
        GeneroOdiado = generoOdiado;
    }

    public String getAtorFavorito() {
        return AtorFavorito;
    }

    public void setAtorFavorito(String atorFavorito) {
        AtorFavorito = atorFavorito;
    }

    public String getFilmeSequencia() {
        return FilmeSequencia;
    }

    public void setFilmeSequencia(String filmeSequencia) {
        FilmeSequencia = filmeSequencia;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public int getcodGostos() { return codGostos; }

    public void setcodGostos(String codGostos) { this.codGostos = Integer.parseInt(codGostos); }

    @Override
    public String toString() {
        return "FilmeFavorito: " + getFilmeFavorito() + ", GeneroFavorito: " + getGeneroFavorito() + ", CodCliente: " + getCodCliente();
    }
}
