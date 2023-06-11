import java.util.*;

public class BoyerMoore {
    private int[] buildBadCharTable(char[] pattern) {
        // Cria tabela dos maus caracteres
        int[] badCharTable = new int[256];
        Arrays.fill(badCharTable, -1);
        // Preenchimento dela
        for (int i = 0; i < pattern.length; i++) {
            badCharTable[pattern[i]] = i;
        }
        // Retorno da tabela
        return badCharTable;
    }

    private int[] buildGoodSuffixTable(char[] pattern) {
        // Cria tabela dos bons sufixos
        int patternLength = pattern.length;
        int[] goodSuffixTable = new int[patternLength];
        int[] suffixes = new int[patternLength];
        // Preenchimento da tabela
        Arrays.fill(goodSuffixTable, patternLength);
        computeSuffixes(pattern, suffixes);

        int j = 0;
        for (int i = patternLength - 1; i >= 0; i--) {
            if (suffixes[i] == i + 1) {
                for (; j < patternLength - 1 - i; j++) {
                    if (goodSuffixTable[j] == patternLength) {
                        goodSuffixTable[j] = patternLength - 1 - i;
                    }
                }
            }
        }

        for (int i = 0; i <= patternLength - 2; i++) {
            goodSuffixTable[patternLength - 1 - suffixes[i]] = patternLength - 1 - i;
        }
        // Retorna tabela
        return goodSuffixTable;
    }

    private void computeSuffixes(char[] pattern, int[] suffixes) {
        // Realiza a validação de sufixos
        int patternLength = pattern.length;
        int f = 0, g;

        suffixes[patternLength - 1] = patternLength;
        g = patternLength - 1;
        for (int i = patternLength - 2; i >= 0; --i) {
            if (i > g && suffixes[i + patternLength - 1 - f] < i - g) {
                suffixes[i] = suffixes[i + patternLength - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;
                while (g >= 0 && pattern[g] == pattern[g + patternLength - 1 - f]) {
                    --g;
                }
                suffixes[i] = f - g;
            }
        }
        // Validação executada com sucesso
    }

    public List<Integer> search(String text, String pattern, Boolean useBadCharHeuristic) {
        // Método que realiza busca no nosso arquivo a partir de um padrão informado pelo usuário
        List<Integer> occurrences = new ArrayList<>();
        // Ciração de parâmetros e tabelas
        char[] textChars = text.toCharArray();
        char[] patternChars = pattern.toCharArray();
        int textLength = textChars.length;
        int patternLength = patternChars.length;

        int[] badCharTable = buildBadCharTable(patternChars);
        int[] goodSuffixTable = buildGoodSuffixTable(patternChars);
        // Começo da pesquisa no arquivo (o arquivo virou uma única String gigante)
        int shift = 0;
        // Shift será a posição do ponteiro durante a leitura e comparação dos padrões
        while (shift <= textLength - patternLength) {
            try{
            int mismatchIndex = patternLength - 1;

            while (mismatchIndex >= 0 && patternChars[mismatchIndex] == textChars[shift + mismatchIndex]) {
                mismatchIndex--;
            }

            if (mismatchIndex < 0) {
                occurrences.add(shift);
                shift += (shift + patternLength < textLength) ? patternLength - badCharTable[textChars[shift + patternLength]] : 1;
            } else {
                int badCharShift = mismatchIndex - badCharTable[textChars[shift + mismatchIndex]];
                int goodSuffixShift = 0;
                // Caso estejamos usando o mau caractere
                if (useBadCharHeuristic) {
                    shift += Math.max(1, badCharShift);
                } else {
                    // Caso seja o bom sufixo
                    if (mismatchIndex < patternLength - 1) {
                        goodSuffixShift = goodSuffixTable[mismatchIndex + 1];
                    } else {
                        goodSuffixShift = patternLength - badCharTable[textChars[shift + patternLength]];
                    }

                    shift += Math.max(badCharShift, goodSuffixShift);
                }
            }} catch(Exception e){
                // Em caso de caractere inválido no arquivo
                shift++;
            }
        }
        // Retorna lista com os índices da ocorrência do padrão procurado
        return occurrences;
    }
}
