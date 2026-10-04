import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class ArteAsciiOffline {

     Cada desenho do catalogo tem duas versoes
    record Desenho(String minimalista, String realista) {}

    private static final MapString, Desenho CATALOGO = new LinkedHashMap();


    private static final MapString, String APELIDOS = Map.of(
            cao, cachorro,
            gata, gato,
            gatinho, gato,
            dragon, dragao,
            mansion, mansao,
            mar, agua,
            gota, agua
    );

     Conversao de imagem
    private static final int LARGURA_MINIMALISTA = 40;
    private static final int LARGURA_REALISTA = 110;
    private static final String RAMPA_MINIMALISTA =  .+#@;
    private static final String RAMPA_REALISTA =
             .'`^,;Il!i~+_-][}{1)(tfjrxnuvczXYUJCLQ0OZmwqpdbkhao#MW&8%B@$;

    private static final double PROPORCAO_CARACTERE = 0.5;

    private static String arte(String... linhas) {
        return String.join(n, linhas);
    }

    static {
        CATALOGO.put(gato, new Desenho(
                arte(
                         _,
                        ( o.o ),
                          ^ ),
                arte(
                                 _____,
                                  o   o  ,
                               ( ==  ^  == ),
                                )         (,
                               (           ),
                              ( (  )   (  ) ),
                             (__(__)___(__)__))));

        CATALOGO.put(cachorro, new Desenho(
                arte(
                          __      _,
                        o'')}____,
                         `_      ),
                         (_(_-(_),
                arte(
                             ^ ^,
                             0 0 ,
                            V Y V,
                              - ,
                                ,
                           V__) )));

        CATALOGO.put(coracao, new Desenho(
                arte(
                          ,
                            ,
                            ,
                           ,
                           ),
                arte(
                           ..   ..,
                          .,
                          ,
                          '',
                            '',
                              '',
                                '')));

        CATALOGO.put(casa, new Desenho(
                arte(
                           ,
                            ,
                         ____,
                          [] ,
                         ____),
                arte(
                                     _,
                                     ,
                                   _ ,
                                        ,
                             ____________,
                               []    []  ,
                               []  __  [],
                             __________)));

        CATALOGO.put(peixe, new Desenho(
                arte(
                        (((o),
                arte(
                             .-''''-.   ,
                              o        ,
                               __    =  ,
                                   _   ,
                             '-....-'   )));

        CATALOGO.put(arvore, new Desenho(
                arte(
                           ,
                          ,
                         ,
                           ),
                arte(
                               ..,
                             ..,
                            ,
                           '',
                             '',
                               '',
                                ,
                                ,
                              ____)));

        CATALOGO.put(agua, new Desenho(
                arte(
                            ^,
                            ,
                             ,
                         (     ),
                          `._.'),
                arte(
                                .,
                               ,
                                ,
                                 ,
                            (   o   ),
                             `-._.-',
                        ,
                          ~~~~~  ~~~~~  ~~~~~  ~~~~~,
                         ~~~  ~~~~  ~~~~~  ~~~~  ~~~~,
                        ~~  ~~~  ~~~~  ~~~  ~~~~  ~~~,
                         ~~~~  ~~~  ~~~~~  ~~~  ~~~~,
                          ~~~~~  ~~~~~  ~~~~~  ~~~~~)));

        CATALOGO.put(dragao, new Desenho(
                arte(
                                  ,
                          ______ ,
                                ,
                          ( (o)(o) ),
                          (   ^^   ),
                            vv ,
                            __),
                arte(
                                                                                              -,
                                                                                          ,
                                                                                     -                                   -------------,
                                                                                -                                  ;ii11111ii;;,
                                                                             -0                              ---ff1i1111111iii;;;;;;,
                                                                          08                 ---          C11fCfft1111i;;;;;iii1111,
                                                                        G08@   --------            Gt;;;L;ft1t1;;i11111i;;;;;;,
                                                                    G0080Lf-00088_                  CCi;;;;11;1f;itti;;;;;i11111i,
                                                                -CCGG0080GCLG0088@@                        GC;;;;;;;L;;;f1;;itti;;;;;;;;i,
                                                           --GGGGGCCCCCCCCCLLG00GG                     GC;;;;;;;;t1;;;tf;;;;1tt;;;;;;;,
                                                       --GCCCLLLCGLLLLLLLLLLLLLLLC     --           CGC;;;;;;;;;;L;;;;if1;;;;;1t1;;;;,
                                                      LCLLLLLLLC8  GLLLLLLLLLLCGGG---GGG  -       CGGi;;;;;;;;;;t1;;;;;1fi;;;;;itt1;,
                                                    ffLLLLLLLLLLLLtffLLLLLLLLLG0000008_____0     CG0i;;;;;;;;;;;;L;;;;;;;ft;;;;;;;it,
                                                 CGLLLLCLCCLCLLLLLfLLLLLLLLLLLLLLLLLLL         -GG0t;;;;;;;;;;;;;t1;;;;;;;ifi;;;;;;;,
                                              fCGCGGLLLLCLLLLLLLLLLLLLLLLLLLLLLLLCCGCCL      00GG00C;;;;;;;;;;;;;;;L;;;;;;;;;f1;;;;;;,
                                             ffLLLCLCLLLLLLLLLLLCLLLLLLLLLLLLLLLLLC000000GGCLf    GG000i;;;;;;;;;;;;;;;fi;;;;;i_f;;ii,
                                              C__CLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCLLLLLCCG00GCL-1fGLLL111;;;;;;;;;;;;;;f;;;i    _,
                                  ------          CLCGLCCCCCCLLCLCCCLLCLLCLLLCLLLtffffffffLLCGGLfftL08GLff;;;;;;;;;;;;;;fi;i,
                                     LCCCC-------C--fC.,G,.tf.;@1;GL1t011GGtfG1f0LttfffffftfffftffLLCLLCGCCCL1;;;;;;i____;iL1,
                                ttfCG000000000G;,i;..,..,...t.........1,,,,,,,,,1t1fffffffffffffftfffGGLft;;;;i1     _,
                            CCCCCCCCCCCCCCC1,,,,,,,,,,,,,,,.......,,,,,,,,,,,11tffftfffftfffftftfff08GL;;;i1,
                                     tfffLL______t1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,itfffftfffftfffftftfftfGGCLii1,
                                   ______       1,i,,;11i;;;t;;;1,,,,,,;fffffffffffffffftfffftfGCLLf,
                                __                 @;,,fG,i@iGL;;i@i,,GL,;;i1tfffftfffftfffffffffffff00Cff,
                                                        _______i1i,;i,,;11;ifft1t11t1t1tffffffffffffftfffftfffCGGCf,
                                                                __________1t11t1t11t1t1t1tftfffftfffffffffffffffCGCCCf,
                                                                           tttt1tttt1t1tffffffffffffftfffftfffftf0Gfffff,
                                                                             _______tfffftfffftfffffffffffffffftfffftfff,
                                                                                       tfffffffffffffftfffftfffffffffffffffL,
                                                                                        ttftfffftfffffffffffffffftfffftfffff,
                                                                                         1ttfttftftfftftfftftfftftftfftftfffL)));

        CATALOGO.put(mansao, new Desenho(
                arte(
                                ^,
                                ,
                          ___________,
                          [] [] [] [] ,
                          [] [] [] [] ,
                         ___________),
                arte(
                                        ^,
                                        ,
                                      ___,
                            _          o          _,
                           _        ___        _,
                         ___________   ___________,
                         ___  ___         ___  ___ ,
                        [___][___]       [___][___],
                                                   ,
                        [___][___]   _   [___][___],
                        __________________________,
                                    _______,
                                  ___________)));

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(=== GERADOR DE ARTE ASCII (offline) ===);
        System.out.println(Digite o nome de um desenho ou o caminho de uma imagem.);
        listar();

        while (true) {
            System.out.print(nO que voce quer desenhar (ou 'lista'  'sair') );
            if (!sc.hasNextLine()) break;
            String bruto = sc.nextLine();
            String pedido = normalizar(bruto);

            if (pedido.equals(sair)) break;
            if (pedido.isEmpty()) continue;
            if (pedido.equals(lista)) {
                listar();
                continue;
            }

            String chave = APELIDOS.getOrDefault(pedido, pedido);
            Desenho desenho = CATALOGO.get(chave);
            File imagem = new File(limparCaminho(bruto));

            if (desenho != null) {
                fluxoCatalogo(sc, chave, desenho);
            } else if (imagem.isFile()) {
                fluxoImagem(sc, imagem);
            } else {
                System.out.println(Nao tenho  + bruto.trim() +  no catalogo e nao achei 
                        + nenhum arquivo com esse caminho.);
                System.out.println(Para outros temas, informe o caminho de uma imagem 
                        + (ex. Cfotosleao.jpg).);
                listar();
            }
        }

        System.out.println(Ate a proxima!);
        sc.close();
    }



    private static void fluxoCatalogo(Scanner sc, String nome, Desenho desenho) {
        String estilo = perguntarEstilo(sc);
        if (estilo == null) return;

        String arte = estilo.equals(minimalista)  desenho.minimalista()  desenho.realista();
        System.out.println();
        System.out.println(arte);

        Boolean salvar = perguntarSimNao(sc, nSalvar em arquivo .txt (sn) );
        if (salvar != null && salvar) {
            salvar(new File(nome + _ + estilo + .txt).getAbsoluteFile(), arte);
        }
    }

    private static void fluxoImagem(Scanner sc, File arquivo) {
        BufferedImage imagem = carregar(arquivo);
        if (imagem == null) return;

        String estilo = perguntarEstilo(sc);
        if (estilo == null) return;

        Boolean fundoEscuro = perguntarSimNao(sc, Seu terminal tem fundo escuro (sn) );
        if (fundoEscuro == null) return;

        boolean minimalista = estilo.equals(minimalista);
        String arte = converter(
                imagem,
                minimalista  LARGURA_MINIMALISTA  LARGURA_REALISTA,
                minimalista  RAMPA_MINIMALISTA  RAMPA_REALISTA,
                fundoEscuro);

        System.out.println();
        System.out.println(arte);

        Boolean salvar = perguntarSimNao(sc, nSalvar em arquivo .txt (sn) );
        if (salvar != null && salvar) {
            String nome = arquivo.getName();
            int ponto = nome.lastIndexOf('.');
            String base = ponto  0  nome.substring(0, ponto)  nome;
            salvar(new File(arquivo.getAbsoluteFile().getParentFile(), base + _ascii.txt), arte);
        }
    }



    private static String converter(BufferedImage img, int largura, String rampa,
                                    boolean fundoEscuro) {
        int w = img.getWidth();
        int h = img.getHeight();
        int altura = Math.max(1,
                (int) Math.round((double) h  w  largura  PROPORCAO_CARACTERE));

        double[][] lum = new double[altura][largura];
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for (int y = 0; y  altura; y++) {
            int y0 = (int) ((long) y  h  altura);
            int y1 = Math.max(y0 + 1, (int) ((long) (y + 1)  h  altura));
            for (int x = 0; x  largura; x++) {
                int x0 = (int) ((long) x  w  largura);
                int x1 = Math.max(x0 + 1, (int) ((long) (x + 1)  w  largura));

                double soma = 0;
                int n = 0;
                for (int py = y0; py  y1 && py  h; py++) {
                    for (int px = x0; px  x1 && px  w; px++) {
                        soma += luminosidade(img.getRGB(px, py));
                        n++;
                    }
                }
                double media = soma  n;
                lum[y][x] = media;
                if (media  min) min = media;
                if (media  max) max = media;
            }
        }

        double faixa = max - min;
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y  altura; y++) {
            StringBuilder linha = new StringBuilder();
            for (int x = 0; x  largura; x++) {
                double brilho = faixa  1  0.5  (lum[y][x] - min)  faixa;
                double densidade = fundoEscuro  brilho  1 - brilho;
                int idx = (int) Math.round(densidade  (rampa.length() - 1));
                linha.append(rampa.charAt(idx));
            }
            sb.append(linha.toString().stripTrailing()).append('n');
        }

        return sb.toString().replaceAll(A(sn)+, ).stripTrailing() + n;
    }


    private static double luminosidade(int argb) {
        double a = ((argb  24) & 0xFF)  255.0;
        double r = ((argb  16) & 0xFF)  a + 255  (1 - a);
        double g = ((argb  8) & 0xFF)  a + 255  (1 - a);
        double b = (argb & 0xFF)  a + 255  (1 - a);
        return 0.2126  r + 0.7152  g + 0.0722  b;
    }


    private static BufferedImage carregar(File arquivo) {
        try {
            BufferedImage img = ImageIO.read(arquivo);
            if (img == null) {
                System.out.println(Formato nao suportado. Use jpg, png, gif ou bmp.);
            }
            return img;
        } catch (IOException e) {
            System.out.println(Erro ao ler a imagem  + e.getMessage());
            return null;
        }
    }

    private static void salvar(File destino, String arte) {
        try {
            Files.writeString(destino.toPath(), arte, StandardCharsets.UTF_8);
            System.out.println(Salvo em  + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println(Nao foi possivel salvar  + e.getMessage());
        }
    }

    private static void listar() {
        System.out.println(Desenhos prontos  + String.join(, , CATALOGO.keySet()));
    }

     Remove espacos e aspas (ao arrastar o arquivo para o terminal)
    private static String limparCaminho(String texto) {
        String t = texto.trim();
        if (t.length() = 2
                && ((t.startsWith() && t.endsWith())
                 (t.startsWith(') && t.endsWith(')))) {
            t = t.substring(1, t.length() - 1);
        }
        return t;
    }


    private static String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll(p{M}, );
        return semAcento.trim().toLowerCase();
    }

    private static String perguntarEstilo(Scanner sc) {
        while (true) {
            System.out.println(nQual estilo voce prefere);
            System.out.println(  1 - Minimalista);
            System.out.println(  2 - Realista);
            System.out.print(Escolha );
            if (!sc.hasNextLine()) return null;

            String r = normalizar(sc.nextLine());
            switch (r) {
                case 1, m, minimalista - { return minimalista; }
                case 2, r, realista    - { return realista; }
                default - System.out.println(Opcao invalida. Digite 1 ou 2.);
            }
        }
    }

    private static Boolean perguntarSimNao(Scanner sc, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            if (!sc.hasNextLine()) return null;

            String r = normalizar(sc.nextLine());
            switch (r) {
                case s, sim - { return true; }
                case n, nao - { return false; }
                default - System.out.println(Responda com s ou n.);
            }
        }
    }
}