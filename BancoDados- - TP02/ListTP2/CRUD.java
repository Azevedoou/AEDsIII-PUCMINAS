import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CRUD {
    private RandomAccessFile file;

    // ------------------------------------ //
    
    /*
     * Construtor
     */
    CRUD(String nomeArq) throws IOException{
        file = new RandomAccessFile(nomeArq, "rw");
        file.seek(0);
    }
    
    // ------------------------------------ //

    // Primeira injeção do CSV para o banco de dados

    public long create(RandomAccessFile file, Game game) throws IOException{
        file.seek(0); // Ponteiro vai para o inicio do arquivo
        file.writeInt(game.getAppId()); // Escreve no inicio do registro o seu ID
        file.seek(file.length()); // Ponteiro vai para o final do arquivo
        long pos=file.getFilePointer();
        byte[] byteArr = game.toByteArray(); // Vetor de Bytes populado com os dados do CSV já filtrados
        file.writeInt(game.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
        file.write(byteArr); // Escreve o vetor de Bytes
        return pos;
    }

    public long create(Game game) throws IOException{
        return create(file, game);
    }

    // ------------------------------------ //

    //Read
     
    public Game Read(int entradaID) throws IOException{
        long pos;
        int qntBytesInic, id;
        Game gameLido = null;
        boolean lap;

        file.seek(0);
        file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
        try{
        while(file.getFilePointer() < file.length()){
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Game específico
            id = file.readInt(); // Armazena o ID do registro Game específico
            if(id == entradaID){ // Verifica se o id é o mesmo que o selecionado
                if(lap){ // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                    try{
                        gameLido = convertTo(file, pos); // Gera uma instância de Game e popula com as informações do banco de dados
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                    }                    
                }
                else{
                    file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
                }
            }
            else{
                file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro
            }
        }
    }catch (IOException e){
        e.printStackTrace();
        gameLido = null;
    }

    return gameLido;
    }

    public Game ReadArvore(long pos) throws IOException{
        // Semelhante ao Read padrão, porém o ponteiro do arquivo já irá direto para a posição certa
        Game gameLido = null;
        boolean lap;
        file.seek(pos);
        // Pular tamanho, lápide e ID
        file.readInt();
        lap = file.readBoolean();
        file.readInt();
        if(lap){ // Checa se ele não foi excluído
            try{
                gameLido=convertTo(file,pos); // Salva game
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return gameLido; // Retorna o game
        

    }
    // ------------------------------------ //

    /*
     * Popula a instância Game com as informações advindas do banco de dados
     */
    public Game convertTo(RandomAccessFile file, long pos) throws Exception{
        Game gameSelec = new Game();
        ArrayList<String> gnrTmp = new ArrayList<String>();

        file.seek(pos); // O ponteiro é direcionado para a posição inicial do registro
        file.readInt(); // Pula o ID do regitro
        gameSelec.setLapide(file.readBoolean()); // Seta o valor da lapide na instância de Game
        gameSelec.setAppId(file.readInt()); // Seta o ID
        gameSelec.setPrice(file.readFloat()); // Seta o preço
        gameSelec.setReleaseDate(gameSelec.transformaLongDate(file.readLong()));
        file.readInt(); // Recebo o valor em long da data e tranformo de volta para date
        gameSelec.setName(file.readUTF()); // Seta o nome
        String[] generos = new String[file.readInt()];
        for(int i=0; i<generos.length; i++){
            file.readInt(); // Pula o tamanho da String nome
            generos[i] = file.readUTF(); // Armazena em um vetor os gêneros
            gnrTmp.add(generos[i]); // Passa esses gêneros para um ArrayList<String>
        }
        gameSelec.setGenres(gnrTmp); // Pega o ArrayList<String> populado com todos os gêneros e seta os gêneros
        return gameSelec;
    }

    // Delete

    public long Delete(int entradaID) throws IOException{
        long pos=0;
        int qntBytesInic, id;
        boolean lap;

        file.seek(0); // Posiciona o ponteiro para o inicio do arquivo
        file.readInt(); // Pula o 
        try{
            while(file.getFilePointer()<file.length()){
                pos=file.getFilePointer(); // Salva a posição do ponteiro na instância de tempo
                qntBytesInic=file.readInt(); // Lê o tamanho do registro
                lap=file.readBoolean(); // Armazena o valor da lapide
                id=file.readInt(); // Armazena o valor do ID

                if(id==entradaID){
                    if(lap){
                        try{
                            file.seek(pos); // Volta para começo do registro
                            file.readInt(); // Pula primeira parte do registro
                            file.writeBoolean(false); // Acessa posição da lápide e deixa ela falsa
                            break;
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        file.skipBytes(qntBytesInic-5); // Pula para o próximo registro
                    }
                }
                else{
                    file.skipBytes(qntBytesInic-5); // Pula para o próximo registro
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return pos;
    }

    // Create

    public long Create(int ID, float preco, String dataEntrada, String nome, ArrayList<String> generos) throws IOException, ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date data = df.parse(dataEntrada);
        /* Passa para o contrutor de Game os parâmetros */
        Game gameTmp = new Game(ID, preco, data, nome, generos); 
        /* Segue o mesmo padrão que o código da primeira injeção do CSV no banco de dados */
        file.seek(0);
        file.writeInt(gameTmp.getAppId());
        file.seek(file.length());
        long pos = file.getFilePointer();
        byte[] byteArray = gameTmp.toByteArray();
        file.writeInt(gameTmp.toByteArray().length);
        file.write(byteArray);
        return pos;
    }
    
    // Update

    public void Update(int ID, float preco, String dataEntrada, String nome, ArrayList<String> generos) throws IOException, ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date data = df.parse(dataEntrada);
        Game gameTmp = new Game(ID, preco, data, nome, generos); 
        file.seek(0);
        file.writeInt(gameTmp.getAppId());
        file.seek(file.length());
        byte[] byteArray = gameTmp.toByteArray();
        file.writeInt(gameTmp.toByteArray().length);
        file.write(byteArray);
    }
}