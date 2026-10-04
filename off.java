//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ArteAsciiOffline {
    private static final Map<String, Desenho> CATALOGO = new LinkedHashMap();
    private static final Map<String, String> APELIDOS = Map.of("cao", "cachorro", "gata", "gato", "gatinho", "gato", "dragon", "dragao", "mansion", "mansao", "mar", "agua", "gota", "agua");
    private static final int LARGURA_MINIMALISTA = 40;
    private static final int LARGURA_REALISTA = 110;
    private static final String RAMPA_MINIMALISTA = " .+#@";
    private static final String RAMPA_REALISTA = " .'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
    private static final double PROPORCAO_CARACTERE = (double)0.5F;

    public ArteAsciiOffline() {
    }

    private static String arte(String... linhas) {
        return String.join("\n", linhas);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== GERADOR DE ARTE ASCII (offline) ===");
        System.out.println("Digite o nome de um desenho ou o caminho de uma imagem.");
        listar();

        while(true) {
            System.out.print("\nO que voce quer desenhar? (ou 'lista' / 'sair'): ");
            if (!sc.hasNextLine()) {
                break;
            }

            String bruto = sc.nextLine();
            String pedido = normalizar(bruto);
            if (pedido.equals("sair")) {
                break;
            }

            if (!pedido.isEmpty()) {
                if (pedido.equals("lista")) {
                    listar();
                } else {
                    String chave = (String)APELIDOS.getOrDefault(pedido, pedido);
                    Desenho desenho = (Desenho)CATALOGO.get(chave);
                    File imagem = new File(limparCaminho(bruto));
                    if (desenho != null) {
                        fluxoCatalogo(sc, chave, desenho);
                    } else if (imagem.isFile()) {
                        fluxoImagem(sc, imagem);
                    } else {
                        System.out.println("Nao tenho \"" + bruto.trim() + "\" no catalogo e nao achei nenhum arquivo com esse caminho.");
                        System.out.println("Para outros temas, informe o caminho de uma imagem (ex.: C:\\fotos\\leao.jpg).");
                        listar();
                    }
                }
            }
        }

        System.out.println("Ate a proxima!");
        sc.close();
    }

    private static void fluxoCatalogo(Scanner sc, String nome, Desenho desenho) {
        String estilo = perguntarEstilo(sc);
        if (estilo != null) {
            String arte = estilo.equals("minimalista") ? desenho.minimalista() : desenho.realista();
            System.out.println();
            System.out.println(arte);
            Boolean salvar = perguntarSimNao(sc, "\nSalvar em arquivo .txt? (s/n): ");
            if (salvar != null && salvar) {
                salvar((new File(nome + "_" + estilo + ".txt")).getAbsoluteFile(), arte);
            }

        }
    }

    private static void fluxoImagem(Scanner sc, File arquivo) {
        BufferedImage imagem = carregar(arquivo);
        if (imagem != null) {
            String estilo = perguntarEstilo(sc);
            if (estilo != null) {
                Boolean fundoEscuro = perguntarSimNao(sc, "Seu terminal tem fundo escuro? (s/n): ");
                if (fundoEscuro != null) {
                    boolean minimalista = estilo.equals("minimalista");
                    String arte = converter(imagem, minimalista ? 40 : 110, minimalista ? " .+#@" : " .'`^\",:;Il!i><~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$", fundoEscuro);
                    System.out.println();
                    System.out.println(arte);
                    Boolean salvar = perguntarSimNao(sc, "\nSalvar em arquivo .txt? (s/n): ");
                    if (salvar != null && salvar) {
                        String nome = arquivo.getName();
                        int ponto = nome.lastIndexOf(46);
                        String base = ponto > 0 ? nome.substring(0, ponto) : nome;
                        salvar(new File(arquivo.getAbsoluteFile().getParentFile(), base + "_ascii.txt"), arte);
                    }

                }
            }
        }
    }

    private static String converter(BufferedImage img, int largura, String rampa, boolean fundoEscuro) {
        int w = img.getWidth();
        int h = img.getHeight();
        int altura = Math.max(1, (int)Math.round((double)h / (double)w * (double)largura * (double)0.5F));
        double[][] lum = new double[altura][largura];
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;

        for(int y = 0; y < altura; ++y) {
            int y0 = (int)((long)y * (long)h / (long)altura);
            int y1 = Math.max(y0 + 1, (int)((long)(y + 1) * (long)h / (long)altura));

            for(int x = 0; x < largura; ++x) {
                int x0 = (int)((long)x * (long)w / (long)largura);
                int x1 = Math.max(x0 + 1, (int)((long)(x + 1) * (long)w / (long)largura));
                double soma = (double)0.0F;
                int n = 0;

                for(int py = y0; py < y1 && py < h; ++py) {
                    for(int px = x0; px < x1 && px < w; ++px) {
                        soma += luminosidade(img.getRGB(px, py));
                        ++n;
                    }
                }

                double media = soma / (double)n;
                lum[y][x] = media;
                if (media < min) {
                    min = media;
                }

                if (media > max) {
                    max = media;
                }
            }
        }

        double faixa = max - min;
        StringBuilder sb = new StringBuilder();

        for(int y = 0; y < altura; ++y) {
            StringBuilder linha = new StringBuilder();

            for(int x = 0; x < largura; ++x) {
                double brilho = faixa < (double)1.0F ? (double)0.5F : (lum[y][x] - min) / faixa;
                double densidade = fundoEscuro ? brilho : (double)1.0F - brilho;
                int idx = (int)Math.round(densidade * (double)(rampa.length() - 1));
                linha.append(rampa.charAt(idx));
            }

            sb.append(linha.toString().stripTrailing()).append('\n');
        }

        String var10000 = sb.toString();
        return var10000.replaceAll("\\A(\\s*\\n)+", "").stripTrailing() + "\n";
    }

    private static double luminosidade(int argb) {
        double a = (double)(argb >>> 24 & 255) / (double)255.0F;
        double r = (double)(argb >> 16 & 255) * a + (double)255.0F * ((double)1.0F - a);
        double g = (double)(argb >> 8 & 255) * a + (double)255.0F * ((double)1.0F - a);
        double b = (double)(argb & 255) * a + (double)255.0F * ((double)1.0F - a);
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }

    private static BufferedImage carregar(File arquivo) {
        try {
            BufferedImage img = ImageIO.read(arquivo);
            if (img == null) {
                System.out.println("Formato nao suportado. Use jpg, png, gif ou bmp.");
            }

            return img;
        } catch (IOException e) {
            System.out.println("Erro ao ler a imagem: " + e.getMessage());
            return null;
        }
    }

    private static void salvar(File destino, String arte) {
        try {
            Files.writeString(destino.toPath(), arte, StandardCharsets.UTF_8);
            System.out.println("Salvo em: " + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Nao foi possivel salvar: " + e.getMessage());
        }

    }

    private static void listar() {
        System.out.println("Desenhos prontos: " + String.join(", ", CATALOGO.keySet()));
    }

    private static String limparCaminho(String texto) {
        String t = texto.trim();
        if (t.length() >= 2 && (t.startsWith("\"") && t.endsWith("\"") || t.startsWith("'") && t.endsWith("'"))) {
            t = t.substring(1, t.length() - 1);
        }

        return t;
    }

    private static String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Form.NFD).replaceAll("\\p{M}", "");
        return semAcento.trim().toLowerCase();
    }

    private static String perguntarEstilo(Scanner sc) {
        while(true) {
            System.out.println("\nQual estilo voce prefere?");
            System.out.println("  1 - Minimalista");
            System.out.println("  2 - Realista");
            System.out.print("Escolha: ");
            if (!sc.hasNextLine()) {
                return null;
            }

            switch (normalizar(sc.nextLine())) {
                case "1":
                case "m":
                case "minimalista":
                    return "minimalista";
                case "2":
                case "r":
                case "realista":
                    return "realista";
                default:
                    System.out.println("Opcao invalida. Digite 1 ou 2.");
            }
        }
    }

    private static Boolean perguntarSimNao(Scanner sc, String pergunta) {
        while(true) {
            System.out.print(pergunta);
            if (!sc.hasNextLine()) {
                return null;
            }

            switch (normalizar(sc.nextLine())) {
                case "s":
                case "sim":
                    return true;
                case "n":
                case "nao":
                    return false;
                default:
                    System.out.println("Responda com s ou n.");
            }
        }
    }

    static {
        CATALOGO.put("gato", new Desenho(arte(" /\\_/\\", "( o.o )", " > ^ <"), arte("         /\\_____/\\", "        /  o   o  \\", "       ( ==  ^  == )", "        )         (", "       (           )", "      ( (  )   (  ) )", "     (__(__)___(__)__)")));
        CATALOGO.put("cachorro", new Desenho(arte("  __      _", "o'')}____//", " `_/      )", " (_(_/-(_/"), arte("     /^ ^\\", "    / 0 0 \\", "    V\\ Y /V", "     / - \\", "    /    |", "   V__) ||")));
        CATALOGO.put("coracao", new Desenho(arte(" ** **", "*  *  *", " *   *", "  * *", "   *"), arte("   .:::.   .:::.", "  :::::::.:::::::", "  :::::::::::::::", "  ':::::::::::::'", "    ':::::::::'", "      ':::::'", "        ':'")));
        CATALOGO.put("casa", new Desenho(arte("   /\\", "  /  \\", " /____\\", " | [] |", " |____|"), arte("         /\\    _", "        /  \\  | |", "       /    \\_| |", "      /          \\", "     /____________\\", "     |  []    []  |", "     |  []  __  []|", "     |_____|__|___|")));
        CATALOGO.put("peixe", new Desenho(arte("><(((o>"), arte("     .-''''-.   |\\", "    /  o     \\  | \\", "   <    __    >=|  >", "    \\       _/  | /", "     '-....-'   |/")));
        CATALOGO.put("arvore", new Desenho(arte("   *", "  ***", " *****", "   |"), arte("       .:::.", "     .:::::::.", "    :::::::::::", "   ':::::::::::'", "     ':::::::'", "       '::'", "        ||", "        ||", "      __||__")));
        CATALOGO.put("agua", new Desenho(arte("    ^", "   / \\", "  /   \\", " (     )", "  `._.'"), arte("        .", "       /|\\", "      / | \\", "     /  |  \\", "    (   o   )", "     `-._.-'", "", "  ~~~~~  ~~~~~  ~~~~~  ~~~~~", " ~~~  ~~~~  ~~~~~  ~~~~  ~~~~", "~~  ~~~  ~~~~  ~~~  ~~~~  ~~~", " ~~~~  ~~~  ~~~~~  ~~~  ~~~~", "  ~~~~~  ~~~~~  ~~~~~  ~~~~~")));
        CATALOGO.put("dragao", new Desenho(arte("\\\\          //", " \\\\ ______ //", "  \\/      \\/", "  ( (o)(o) )", "  (   ^^   )", "   \\ \\vv/ /", "    \\_||_/"), arte("                                                                      -|", "                                                                  /\\|", "                                                             //-||/                                   ///-------------", "                                                        ///-|///                                  ////;ii11111ii;;::::", "                                                     //-0///                     /||         //---ff1i1111111iii;;;;;;", "                                                  ///08//                 ---/\\||          //C11fCfft1111i;;;;;iii1111", "                                                //G08@|   //--------\\\\|//////            //Gt;;;L;ft1t1;;i11111i;;;;;;", "                                            ////G0080Lf-//00088///_///                  /CCi;;;;11;1f;itti;;;;;i11111i", "                                        ///-CCGG0080GCLG0088@@/                        /GC;;;;;;;L;;;f1;;itti;;;;;;;;i", "                                   ///--GGGGGCCCCCCCCCLLG00GG|     /\\                //GC;;;;;;;;t1;;;tf;;;;1tt;;;;;;;", "                               //--GCCCLLLC/GL\\LLLLLLLLLLLLLLC\\    /\\ --\\           /CGC;;;;;;;;;;L;;;;if1;;;;;1t1;;;;", "                              /LCLLLLLLLC8| /\\ |GLLLLLLLLLLCGGG\\---GG/G\\  -\\       /CGGi;;;;;;;;;;t1;;;;;1fi;;;;;itt1;", "                            //ffLLLLLLLLLLL\\Lt/ffLLLLLLLLLG0000008|/_\\____/0\\     /CG0i;;;;;;;;;;;;L;;;;;;;ft;;;;;;;it", "                         ///CGLLLLCLCCLCLLLLLfLLLLLLLLLLLLLLLLLLL|         \\\\\\\\-//GG0t;;;;;;;;;;;;;t1;;;;;;;ifi;;;;;;;", "                      ///fCGCGGLLLLCLLLLLLLLLLLLLLLLLLLLLLLLCCGCCL\\\\\\\\\\      |00GG00C;;;;;;;;;;;;;;;L;;;;;;;;;f1;;;;;;", "                     |ffLLLCLCLLLLLLLLLLLCLLLLLLLLLLLLLLLLLC000000GGCLf\\\\\\    |GG000i;;;;;;;;;;;;;;;fi;;;;;i/_\\\\f;;ii/", "                      |\\\\\\\\C__CLLLLLLLLLLLLLLLLLLLLLLLLLLLLLCLLLLLCCG00GCL\\\\\\-1fGLLL111;;;;;;;;;;;;;;f;;;i//    \\\\_//", "          |//------        |  |CLCGLCCCCCCLLCLCCCLLCLLCLLLCLLLtffffffffLLCGGLfftL08GLff;;;;;;;;;;;;;;fi;i/", "             /LCCCC-------/C--fC.,G,.tf.;@1;GL1t011GGtfG1f0LttfffffftfffftffLLCLLCGCCCL1;;;;;;i/____;iL1/", "        /////ttfCG000000000G;,i;..,..,...t.........1,,,,,,,,,:1t1fffffffffffffftfffGGLft;;;;i1/     \\\\_/", "    |||\\\\\\\\\\\\CCCCCCCCCCCCCCC1,,,,,,,,,,,,,,,.......,,,,,,,,,,,11tffftfffftfffftftfff08GL;;;i1/", "             |tfffLL______\\t1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,itfffftfffftfffftftfftfGGCLii1|", "           |\\\\______       \\\\1:,i,,:;1:::1:::i;:;;t;;;1:,,,,,,;fffffffffffffffftfffftfGCLLf|", "        |\\__                 \\\\\\@;,,fG,:i@i::GL;;i@i,,GL,::;;i1tfffftfffftfffffffffffff00Cff\\", "                                \\_______i1i:,;i,,;11;ifft1t11t1t1tffffffffffffftfffftfffCGGCf\\\\", "                                        __________\\1t11t1t11t1t1t1tftfffftfffffffffffffffCGCCCf\\\\\\", "                                                   \\\\tttt1tttt1t1tffffffffffffftfffftfffftf0Gfffff\\", "                                                     \\\\_______\\tfffftfffftfffffffffffffffftfffftfff\\\\", "                                                               \\tfffffffffffffftfffftfffffffffffffffL\\", "                                                                \\ttftfffftfffffffffffffffftfffftfffff|", "                                                                 |1ttfttftftfftftfftftfftftftfftftfffL|")));
        CATALOGO.put("mansao", new Desenho(arte("        ^", "       / \\", "  ____/___\\____", " | [] [] [] [] |", " | [] [] [] [] |", " |_____|_|_____|"), arte("                ^", "               / \\", "              /___\\", "    _         | o |         _", "   |_|        |___|        |_|", " ___|_|_______|   |_______|_|___", "/ ___  ___   ||   ||   ___  ___ \\", "|[___][___]  ||   ||  [___][___]|", "|            ||   ||            |", "|[___][___]  || _ ||  [___][___]|", "|____________||_|_||____________|", "            _|_____|_", "          _|_________|_")));
    }

    static record Desenho(String minimalista, String realista) {
    }
}
