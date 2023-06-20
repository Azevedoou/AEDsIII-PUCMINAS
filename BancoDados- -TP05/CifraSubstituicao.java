import java.util.HashMap;
import java.util.Map;

public class CifraSubstituicao {
    // Cria tabela para substiuição
    private Map<Character, Character> chave;

    public CifraSubstituicao() {
        // Seta tabela com valores padrões para cada letra
        Map<Character, Character> chave = new HashMap<>();
        chave.put('a', 'q');
        chave.put('b', 'w');
        chave.put('c', 'e');
        chave.put('d', 'r');
        chave.put('e', 't');
        chave.put('f', 'y');
        chave.put('g', 'u');
        chave.put('h', 'i');
        chave.put('i', 'o');
        chave.put('j', 'p');
        chave.put('k', 'a');
        chave.put('l', 's');
        chave.put('m', 'd');
        chave.put('n', 'f');
        chave.put('o', 'g');
        chave.put('p', 'h');
        chave.put('q', 'j');
        chave.put('r', 'k');
        chave.put('s', 'l');
        chave.put('t', 'z');
        chave.put('u', 'x');
        chave.put('v', 'c');
        chave.put('w', 'v');
        chave.put('x', 'b');
        chave.put('y', 'n');
        chave.put('z', 'm');
        chave.put('A', 'Q');
        chave.put('B', 'W');
        chave.put('C', 'E');
        chave.put('D', 'R');
        chave.put('E', 'T');
        chave.put('F', 'Y');
        chave.put('G', 'U');
        chave.put('H', 'I');
        chave.put('I', 'O');
        chave.put('J', 'P');
        chave.put('K', 'A');
        chave.put('L', 'S');
        chave.put('M', 'D');
        chave.put('N', 'F');
        chave.put('O', 'G');
        chave.put('P', 'H');
        chave.put('Q', 'J');
        chave.put('R', 'K');
        chave.put('S', 'L');
        chave.put('T', 'Z');
        chave.put('U', 'X');
        chave.put('V', 'C');
        chave.put('W', 'V');
        chave.put('X', 'B');
        chave.put('Y', 'N');
        chave.put('Z', 'M');
        setarChave(chave);
    }

    private void setarChave(Map<Character, Character> chave) {
        this.chave = chave;
    }

    public String criptografa(String texto) {
        // Criptografa texto
        StringBuilder textoCriptografado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            // Passa caractere por caractere encriptografando
            Character caracterCriptografado = chave.get(Character.toLowerCase(c));
            if (caracterCriptografado != null) {
                if (Character.isUpperCase(c)) {
                    caracterCriptografado = Character.toUpperCase(caracterCriptografado);
                }
                textoCriptografado.append(caracterCriptografado);
            } else {
                textoCriptografado.append(c);
            }
        }
        // Retorna texto criptografado
        return textoCriptografado.toString();
    }

    public String descriptografa(String textoCriptografado) {
        // Descriptografa texto
        StringBuilder textoDescriptografado = new StringBuilder();
        for (char c : textoCriptografado.toCharArray()) {
            // Passa caractere por caractere descriptografando
            Character caracterDescriptografado = null;
            for (Map.Entry<Character, Character> entry : chave.entrySet()) {
                if (entry.getValue().equals(Character.toLowerCase(c))) {
                    caracterDescriptografado = entry.getKey();
                    break;
                }
            }
            if (caracterDescriptografado != null) {
                if (Character.isUpperCase(c)) {
                    caracterDescriptografado = Character.toUpperCase(caracterDescriptografado);
                }
                textoDescriptografado.append(caracterDescriptografado);
            } else {
                textoDescriptografado.append(c);
            }
        }
        // Retorna texto descriptografado
        return textoDescriptografado.toString();
    }
}
