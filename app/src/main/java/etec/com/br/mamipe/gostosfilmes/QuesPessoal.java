package etec.com.br.mamipe.gostosfilmes;

public class QuesPessoal {
    private int codCliente;

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCinemaFrequentado() {
        return cinemaFrequentado;
    }

    public void setCinemaFrequentado(String cinemaFrequentado) {
        this.cinemaFrequentado = cinemaFrequentado;
    }

    public String getPrecoIngresso() {
        return precoIngresso;
    }

    public void setPrecoIngresso(String precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    private String nomeCliente ;
    private String email ;
    private String cidade ;
    private String telefone ;
    private String cinemaFrequentado ;
    private String precoIngresso ;

    @Override
    public String toString() {
        return "Nome: " + getNomeCliente() + ", Email: " + getEmail() + ", Cidade: " + getCidade();
    }

}

