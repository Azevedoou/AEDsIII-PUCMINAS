class KMP {
    // Faz a tabela do padrão (o autômato).
    private int[] computeLPS(String pattern) {
        int length = pattern.length();
        int[] lps = new int[length];

        // i e j são referentes a qual letra/caractere que o programa deve voltar no padrão
        int i = 0;
        int j = 1;

        while (j < length) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                lps[j] = i + 1;
                i++;
                j++;
            } else {
                if (i != 0) {
                    i = lps[i - 1];
                } else {
                    lps[j] = 0;
                    j++;
                }
            }
        }

        return lps;
    }

    // Busca pelo padrão no texto.
    public void search(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();
        int[] lps = computeLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < textLength) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == patternLength) {
                    // Resposta para cada instância do padrão encontrado no texto.
                    System.out.println("Padrão encontrado no índice " + (i - j));
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
    }
}