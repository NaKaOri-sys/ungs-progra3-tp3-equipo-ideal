package equipoideal.model.dto;

public class RequerimientosDto {

    private int lideres;
    private int arquitectos;
    private int programadores;
    private int testers;

    public RequerimientosDto(int lideres, int arquitectos, int programadores, int testers) {
        this.lideres = lideres;
        this.arquitectos = arquitectos;
        this.programadores = programadores;
        this.testers = testers;
    }

    public int getLideres() {
        return lideres;
    }

    public int getArquitectos() {
        return arquitectos;
    }

    public int getProgramadores() {
        return programadores;
    }

    public int getTesters() {
        return testers;
    }
}
