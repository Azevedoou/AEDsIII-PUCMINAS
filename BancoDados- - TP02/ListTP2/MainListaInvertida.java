import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class MainListaInvertida {
    public static void main(String[] args) {
        try{
            CRUD fileAF = new CRUD("BancoDados");
            try {

                // Ler o CSV file
                String basefile = "games.csv";
    
                FileInputStream fstream = new FileInputStream(basefile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    
                // ------------------------------------ //
    
                // Leitura de todo o CSV.
                String line;
                // Criar lista invertida para gêneros
                ListaInvertida listaGeneros = new ListaInvertida("listaGeneros");
                // Criar lista invertida para nome
                ListaInvertida listaNomes = new ListaInvertida("listaNomes");
                //int cont=0;
                while((line = br.readLine()) != null) {
                    Game game = new Game();
                    game.read(line);
                    /*if(cont==0){
                        cont++;
                        System.out.println(game.getGenres());
                    }*/
                    long pos = fileAF.create(game);
                    listaGeneros.addDocument((int)pos, game.getGenres());
                    listaNomes.addDocument((int)pos, game.getName());
                }
                Scanner sc = new Scanner(System.in);
                /*while(true){
                    System.out.print("Gênero: ");
                    String busca = sc.nextLine();
                    System.out.println(listaGeneros.search(busca));
                    System.out.print("Nome: ");
                    busca = sc.nextLine();
                    System.out.println(listaNomes.search(busca));
                    if(busca.equals("0")){
                        break;
                    }
                }*/
                //System.out.println(arvore.busca(840010).getPos(1));
                //System.out.println(arvore.buscaPos(840010));
                
                int opcao=0;
                while(opcao!=5){
                    System.out.println(" _____________________________________");
                    System.out.println("|Digite qual operação deseja realizar |");
                    System.out.println("|1 - Create                           |");
                    System.out.println("|2 - Read                             |");
                    System.out.println("|3 - Update                           |");
                    System.out.println("|4 - Delete                           |");
                    System.out.println("|5 - Sair e salvar arquivo            |");
                    System.out.println("|_____________________________________|");
                    System.out.println();
                    System.out.print("Opção: ");
                    try{
                    opcao = sc.nextInt();
                    System.out.println();
                    switch(opcao){
                        case 1: 
                        System.out.print("Digite o ID do Game que deseja adicionar nas listas invertidas: ");
                        int createID = sc.nextInt();
                        String lixo=sc.nextLine();
                        if(lixo.equals("CapturaEnter")){}
                        boolean check;
                        try{
                            fileAF.Read(createID).getAppId();
                            check=false;
                        } catch(Exception e){
                            check=true;
                        }
                        if(!check){
                            System.out.println("\nO registro já existe!");
                            break;
                        }
                        System.out.print("Digite o nome do game: ");
                        String nome = sc.nextLine();
                        System.out.print("Digite o preço do game: ");
                        float preco = sc.nextFloat();
                        lixo=sc.nextLine();
                        System.out.print("Digite a data de lançamento do game (DD/MM/YYYY): ");
                        String data = sc.nextLine();
                        System.out.print("Digite os gêneros do game (separados por vírgula e espaço): ");
                        String genero = sc.nextLine();
                        String[] generos = genero.split(", ");
                        ArrayList<String> generosArray = new ArrayList<String>();
                        for(int i=0;i<generos.length;i++){
                            generosArray.add(generos[i]);
                        }

                        long pos = fileAF.Create(createID,preco,data,nome,generosArray);
                        listaGeneros.addDocument((int)pos, generosArray);
                        listaNomes.addDocument((int)pos, nome);
                        System.out.println("\nRegistro criado com sucesso!");
                        break;
                        case 2:
                        lixo=sc.nextLine();
                        System.out.print("Digite o nome principal do game que deseja pesquisar nas listas invertidas: ");
                        nome = sc.nextLine();
                        System.out.print("Digite o gênero principal do game: ");
                        genero = sc.nextLine();
                        List<Integer> vetGeneros = listaGeneros.search(genero);
                        List<Integer> vetNomes = listaNomes.search(nome);
                        boolean find = false;
                        for(int IDgenero:vetGeneros){
                            for(int IDNome:vetNomes){
                                if(IDgenero==IDNome){
                                    find=true;
                                    int readID=IDgenero;
                                    try{
                                        fileAF.ReadArvore(readID).getAppId(); // Teste para ver se game existe (teste de pointer)
                                        System.out.println("\nRegistro encontrado!\n");
                                        System.out.println(fileAF.ReadArvore(readID).getAppId() +" "+ fileAF.ReadArvore(readID).getName() + " " + fileAF.ReadArvore(readID).getPrice()+" "+ fileAF.ReadArvore(readID).getReleaseDate());
                                        for(int i=0; i<fileAF.ReadArvore(readID).getGenres().size(); i++){
                                            System.out.print(fileAF.ReadArvore(readID).getGenres().get(i));
                                            if(i!=fileAF.ReadArvore(readID).getGenres().size()-1){
                                                System.out.print(", ");
                                            }
                                        }
                                        System.out.println();
                                    } catch(Exception e){
                                        System.out.println("\nRegistro não encontrado!");
                                    }
                                    break;
                                }
                            }
                            if(find){
                                break;
                            }
                        }
                        if(!find){
                            System.out.println("\nRegistro não encontrado!");
                        }
                        
                        break;
                        case 3:
                        pos=0;
                        System.out.print("Digite o ID do game que deseja atualizar no registro: ");
                        int updateID = sc.nextInt();
                        lixo=sc.nextLine();
                        boolean checkArq;
                        try{
                            pos = fileAF.Delete(updateID);
                            listaGeneros.removeDocument((int)pos);
                            listaGeneros.removeDocument((int)pos);
                            checkArq=true;
                        } catch(Exception e){
                            checkArq=false;
                        }
                        if(!checkArq){
                            System.out.println("\nO registro não existe!");
                            break;
                        }
                        System.out.print("Digite o novo nome do game: ");
                        String nomeUpdate = sc.nextLine();
                        System.out.print("Digite o novo preço do game: ");
                        Float precoUpdate = sc.nextFloat();
                        lixo=sc.nextLine();
                        System.out.print("Digite a nova data do game (DD/MM/YYYY): ");
                        String dataUpdate = sc.nextLine();
                        System.out.print("Digite os novos gêneros do game (separados por vírgula e espaço): ");
                        String generoUpdate = sc.nextLine();
                        String[] generosUpdate = generoUpdate.split(", ");
                        ArrayList<String> arrayUpdate = new ArrayList<String>();
                        for(int i=0;i<generosUpdate.length;i++){
                            arrayUpdate.add(generosUpdate[i]);
                        }
                        pos = fileAF.Create(updateID, precoUpdate, dataUpdate, nomeUpdate, arrayUpdate);
                        listaGeneros.addDocument((int)pos, arrayUpdate);
                        listaNomes.addDocument((int)pos, nomeUpdate);
                        System.out.println("\nRegistro atualizado com sucesso!");
                        // Enviar para Update no CRUD
                        break;
                        case 4:
                        System.out.print("Digite o ID do game que deseja deletar no arquivo: ");
                        int deleteID = sc.nextInt();
                        try{
                            pos = fileAF.Read(deleteID).getAppId(); // Teste para ver se existe game
                            System.out.println("\nArquivo encontrado!\n");
                            System.out.println(fileAF.Read(deleteID).getAppId() +" "+ fileAF.Read(deleteID).getName() + " " + fileAF.Read(deleteID).getPrice()+" "+ fileAF.Read(deleteID).getReleaseDate());
                            for(int i=0; i<fileAF.Read(deleteID).getGenres().size(); i++){
                                System.out.print(fileAF.Read(deleteID).getGenres().get(i));
                                if(i!=fileAF.Read(deleteID).getGenres().size()-1){
                                    System.out.print(", ");
                                }
                            }
                            System.out.println();
                            fileAF.Delete(deleteID); // Deleta o game
                            listaGeneros.removeDocument((int)pos);
                            listaNomes.removeDocument((int)pos);
                            System.out.println("\nArquivo deletado com sucesso!");                            
                        }catch(Exception e){
                            System.out.println("\nArquivo não encontrado!");
                        }
                        // Enviar para Delete no CRUD
                        break;
                        case 5:
                        System.out.print("\nSalvando arquivo e encerrando programa...");
                        break;
                        default:
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    } catch(Exception e){
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    }
                    System.out.println("\n");
                    System.out.println(" _______________");
                    System.out.println("|               |");
                    System.out.println("|FIM DO PROGRAMA|");
                    System.out.println("|_______________|");
                // Fechamento do CSV
                fstream.close();
                sc.close();
            }
            catch(IOException e) { e.printStackTrace(); }
        }catch (IOException e){
            e.getStackTrace();
        }

        //----------------------------------------------------------------------------- //

        

        // Comandos:

       /*
       System.out.println(games.get(1).getName()); <-- teste, o ArrayList contém todos os games do csv, já "penerados".
       */
        
       
       
       
        
    }
}