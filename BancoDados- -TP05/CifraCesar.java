public class CifraCesar {
    private int chave;

    public CifraCesar(int chave) {
        this.chave = chave;
    }

    public String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();
        char novoCaractere = '\0';
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            if((int)caractere + chave > 255 || (int)caractere - chave < 0){

            }
            else{
                novoCaractere = (char) ((int)caractere + chave);
            }
            resultado.append(novoCaractere);
        }

        return resultado.toString();
    }

    public String decifrar(String textoCifrado) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < textoCifrado.length(); i++) {
            char caractere = textoCifrado.charAt(i);
            char novoCaractere = (char)((int)caractere-chave);
            resultado.append(novoCaractere);
        }

        return resultado.toString();
    }
}
