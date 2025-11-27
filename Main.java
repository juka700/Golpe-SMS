import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// =================== CORES ANSI ======================

class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
}

// =================== ANIMAÇÃO ========================
class Animation {
    public static void type(String text, int delayMs) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delayMs); } catch (InterruptedException ignored) {}
        }
        System.out.println();
    }
}

// =================== DASHBOARD =======================

class Stats {
    public static int totalAnalisados = 0;
    public static int totalSuspeitos = 0;
    public static int totalNaoSuspeitos = 0;

    public static Map<String, Integer> palavrasDetectadas = new HashMap<>();

    public static void registrarResultado(DetectionResult r) {
        totalAnalisados++;

        if (r.isSuspeita()) totalSuspeitos++;
        else totalNaoSuspeitos++;

        for (String p : r.getPalavrasDetectadas()) {
            palavrasDetectadas.put(p, palavrasDetectadas.getOrDefault(p, 0) + 1);
        }
    }

    public static void mostrarDashboard() {
        UI.clearScreen();
        System.out.println(Colors.CYAN + "======== DASHBOARD DE ESTATÍSTICAS ========\n" + Colors.RESET);

        System.out.println("Mensagens analisadas: " + totalAnalisados);
        System.out.println("Suspeitas: " + Colors.RED + totalSuspeitos + Colors.RESET);
        System.out.println("Não suspeitas: " + Colors.GREEN + totalNaoSuspeitos + Colors.RESET);

        System.out.println("\nPalavras mais detectadas:\n");

        if (palavrasDetectadas.isEmpty()) {
            System.out.println("(Nenhuma palavra detectada ainda)");
        } else {
            palavrasDetectadas.entrySet().stream()
                    .sorted((a,b) -> b.getValue() - a.getValue())
                    .limit(10)
                    .forEach(e -> {
                        System.out.printf("%-20s | %s (%d)\n",
                                e.getKey(),
                                "█".repeat(e.getValue()),
                                e.getValue());
                    });
        }

        System.out.println("\n===========================================");
        System.out.print("\nENTER para voltar...");
        new Scanner(System.in).nextLine();
    }
}


// =================== BANCO DE DADOS =======================

class GolpeDatabase {

    public static final String[][] GOLPES = {

            { "Golpes por contato telefônico - Falsa Central de Atendimento",
                    "Falso atendente se passando por banco.",
                    "Nunca revele senhas.",
                    "Ligue para o banco você mesmo." },

            { "Golpe do Falso Motoboy",
                    "Golpistas fingem recolher cartões.",
                    "Banco nunca coleta cartões.",
                    "Não entregue cartões." },

            { "Golpe da Mão Fantasma",
                    "Golpe de acesso remoto.",
                    "Não instale apps por telefone.",
                    "Desligue o celular e vá ao banco." },

            { "Golpe Módulo de Segurança",
                    "Golpistas pedem instalar módulo falso.",
                    "Não baixe apps fora da loja.",
                    "Verifique no site oficial." },

            { "Golpe do Empréstimo Consignado",
                    "Ofertas falsas de empréstimo.",
                    "Desconfie de urgência.",
                    "Verifique no banco." },

            { "Golpe da Liberação de Equipamentos",
                    "Pedem que vá ao caixa eletrônico.",
                    "Nunca siga ordens suspeitas.",
                    "Confirme com o banco." },

            { "Golpe do 0800",
                    "Número falso de atendimento.",
                    "Use apenas site oficial.",
                    "Nunca ligue para números enviados por SMS." },

            { "Golpe da Videochamada",
                    "Pedem documentos por vídeo.",
                    "Banco não pede isso.",
                    "Nunca mostre documentos." },

            { "Golpes por Links",
                    "Mensagens com links suspeitos.",
                    "Nunca clique.",
                    "Abra o app oficial." },

            { "Golpes no WhatsApp",
                    "Perfis falsos fingindo ser parentes.",
                    "Confirme por ligação.",
                    "Nunca transfira sem validar." },

            { "Golpe do Desenrola Brasil",
                    "Links falsos de renegociação.",
                    "Acesse apenas site oficial.",
                    "Não envie documentos." },

            { "Catfish",
                    "Relacionamentos falsos online.",
                    "Pesquise fotos.",
                    "Não envie dinheiro." },

            { "Golpe do Emprego",
                    "Cobra taxa por vaga.",
                    "Vagas reais não cobram.",
                    "Pesquise a empresa." },

            { "Golpes Pix",
                    "Pressão para enviar Pix.",
                    "Verifique o nome antes.",
                    "Não faça pagamento urgente." },

            { "Conta Laranja",
                    "Golpistas usam sua conta.",
                    "Não empreste conta.",
                    "É crime grave." },

            { "Compras Online",
                    "Sites falsos.",
                    "Verifique HTTPS.",
                    "Pesquise avaliações." },

            { "Boleto Falso",
                    "Boletos adulterados.",
                    "Verifique beneficiário.",
                    "Pague pelo app oficial." },

            { "Cartão Clonado",
                    "Dados copiadas.",
                    "Ative alertas.",
                    "Não forneça dados." }
    };
}

// =================== RELACIONAMENTOS =======================

class Relacionamentos {

    private static final Map<Integer, List<Integer>> mapa = new HashMap<>();

    static {
        mapa.put(9, Arrays.asList(7,10,11));
        mapa.put(10, Arrays.asList(9,12));
        mapa.put(14, Arrays.asList(10));
        mapa.put(17, Arrays.asList(16));
    }

    public static void mostrarRelacionados(int op) {
        List<Integer> rel = mapa.get(op);
        if (rel == null) {
            System.out.println("(Nenhum relacionado)");
            return;
        }
        for (Integer r : rel) {
            System.out.println("- " + GolpeDatabase.GOLPES[r - 1][0]);
        }
    }
}

// =================== ANÁLISE DE SMS =======================

class SMSMessage {
    private String text;
    private String sender;
    private String receivedAt;

    public SMSMessage(String text, String sender) {
        this.text = text;
        this.sender = sender;
        this.receivedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getText() { return text; }
    public String getSender() { return sender; }
    public String getReceivedAt() { return receivedAt; }
}

class DetectionResult {
    private boolean suspeita;
    private List<String> palavrasDetectadas;

    public DetectionResult(boolean suspeita, List<String> palavrasDetectadas) {
        this.suspeita = suspeita;
        this.palavrasDetectadas = palavrasDetectadas;
    }

    public boolean isSuspeita() { return suspeita; }
    public List<String> getPalavrasDetectadas() { return palavrasDetectadas; }
}

class PhishingDetector {

    private List<String> blockList = Arrays.asList(
            "clique", "urgente", "pix", "transferência",
            "acesso suspeito", "senha", "bloqueada",
            "premio", "ganhou", "até hoje"
    );

    public DetectionResult analisarMensagem(SMSMessage m) {
        String txt = m.getText().toLowerCase();
        List<String> achados = new ArrayList<>();

        for (String p : blockList) {
            if (txt.contains(p)) achados.add(p);
        }

        return new DetectionResult(!achados.isEmpty(), achados);
    }
}

// =================== SALVAR EM ARQUIVO =======================

class MessageStorage {
    public static void salvar(SMSMessage msg, DetectionResult r) {
        try (FileWriter fw = new FileWriter("mensagens_analisadas.txt", true)) {

            fw.write("=======================================\n");
            fw.write("Mensagem analisada: " + LocalDateTime.now() + "\n");
            fw.write("Texto: " + msg.getText() + "\n");
            fw.write("De: " + msg.getSender() + "\n");
            fw.write("Data recebida: " + msg.getReceivedAt() + "\n");
            fw.write("Suspeita: " + r.isSuspeita() + "\n");
            fw.write("Palavras: " + r.getPalavrasDetectadas() + "\n\n");

        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo.");
        }
    }
}

// =================== RELATÓRIO =======================

class SecurityReport {
    public static String gerar(SMSMessage msg, DetectionResult r) {

        return Colors.CYAN +
                "\n===== RELATÓRIO DE SEGURANÇA =====\n" +
                Colors.RESET +
                "Mensagem: " + msg.getText() + "\n" +
                "Remetente: " + msg.getSender() + "\n" +
                "Data: " + msg.getReceivedAt() + "\n" +
                "Suspeita: " + r.isSuspeita() + "\n" +
                "Padrões: " + r.getPalavrasDetectadas() + "\n";
    }
}

// =================== INTERFACE =======================

class UI {

    public static Scanner sc = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // ---- Tratamento de Erro --------------------

    public static int inputIntSafe() {
        try { return Integer.parseInt(sc.nextLine()); }
        catch (Exception e) { return -9999; }
    }

    public static boolean tratarErro() {
        System.out.println(Colors.RED + "\nEntrada inválida!" + Colors.RESET);

        System.out.println("""
            O que deseja fazer?
            1 - Recomeçar
            2 - Voltar
            3 - Sair
            """);
        System.out.print("Escolha: ");

        int op = inputIntSafe();

        return switch (op) {
            case 1 -> true;
            case 2 -> false;
            case 3 -> {
                System.out.println("Saindo...");
                System.exit(0);
                yield false;
            }
            default -> tratarErro();
        };
    }

    // ---- Menu Principal ------------------------

    public static void mostrarMenuPrincipal() {
        clearScreen();
        System.out.println(Colors.CYAN +
                "┌────────────────────────────────────────────┐\n" +
                "│        SISTEMA DE DETECÇÃO DE GOLPES       │\n" +
                "├────────────────────────────────────────────┤\n" +
                "│ 1 - Golpes Telefônicos                     │\n" +
                "│ 2 - Golpes por Mensagens                   │\n" +
                "│ 3 - Golpes Online                          │\n" +
                "│ 4 - Golpes do Dia a Dia                    │\n" +
                "│ 5 - Dashboard                              │\n" +
                "│ 0 - Sair                                   │\n" +
                "└────────────────────────────────────────────┘" +
                Colors.RESET);
    }

    // ---- Iniciar Sistema -----------------------

    public static void iniciar() {
        PhishingDetector detector = new PhishingDetector();

        while (true) {
            mostrarMenuPrincipal();
            System.out.print("Escolha: ");
            int op = inputIntSafe();

            if (op == -9999) {
                if (tratarErro()) continue;
                else return;
            }

            switch (op) {
                case 1 -> submenu(1, 8, detector);
                case 2 -> submenu(9, 11, detector);
                case 3 -> submenu(12, 16, detector);
                case 4 -> submenu(14, 18, detector);
                case 5 -> Stats.mostrarDashboard();
                case 0 -> {
                    Animation.type("Encerrando...", 30);
                    return;
                }
                default -> tratarErro();
            }
        }
    }

    // ---- Submenus ------------------------------

    public static void submenu(int inicio, int fim, PhishingDetector detector) {

        while (true) {
            clearScreen();
            System.out.println(Colors.YELLOW + "===== GOLPES DISPONÍVEIS =====" + Colors.RESET);

            for (int i = inicio; i <= fim; i++) {
                System.out.println(i + " - " + GolpeDatabase.GOLPES[i - 1][0]);
            }

            System.out.println("\n0 - Voltar");
            System.out.print("Escolha: ");

            int op = inputIntSafe();

            if (op == -9999) {
                if (tratarErro()) continue;
                else return;
            }

            if (op == 0) return;

            if (op < inicio || op > fim) {
                tratarErro();
                continue;
            }

            exibirGolpe(op);

            if (op == 9) analisarSMS(detector);

            System.out.print("\nENTER para continuar...");
            sc.nextLine();
        }
    }

    // ---- Exibir Detalhes -----------------------

    public static void exibirGolpe(int op) {
        clearScreen();
        String[] g = GolpeDatabase.GOLPES[op - 1];

        System.out.println(Colors.BLUE + "===== DETALHES DO GOLPE =====" + Colors.RESET);
        System.out.println("Título: " + g[0]);
        System.out.println("\nDescrição: " + g[1]);
        System.out.println("\nComo se proteger: " + g[2]);
        System.out.println("\nAções práticas: " + g[3]);

        System.out.println("\nRelacionados:");
        Relacionamentos.mostrarRelacionados(op);
    }

    // ---- Análise de SMS ------------------------

    public static void analisarSMS(PhishingDetector detector) {

        System.out.print("\nDeseja analisar SMS? (s/N): ");
        if (!sc.nextLine().equalsIgnoreCase("s")) return;

        System.out.print("Mensagem: ");
        String texto = sc.nextLine();

        System.out.print("Remetente: ");
        String rem = sc.nextLine();

        SMSMessage sms = new SMSMessage(texto, rem);

        Animation.type("Analisando...", 30);

        DetectionResult r = detector.analisarMensagem(sms);
        Stats.registrarResultado(r);

        Animation.type("Gerando relatório...", 30);

        System.out.println(SecurityReport.gerar(sms, r));

        MessageStorage.salvar(sms, r);

        Animation.type("Mensagem salva em arquivo.", 20);
    }
}

// =================== MAIN =======================

public class Main {
    public static void main(String[] args) {
        UI.iniciar();
    }
}
