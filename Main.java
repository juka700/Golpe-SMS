import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// =================== CORES ANSI ======================

class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
}



// ====================== ANIMAÇÃO ======================

class Animation {

    // Escreve texto com animação estilo “digitando”
    public static void type(String text, int delayMs) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delayMs); } catch (InterruptedException ignored) {}
        }
        System.out.println();
    }
}



// ================== BANCO DE DADOS ====================

class GolpeDatabase {

    public static final String[][] GOLPES = {

            { "Golpes por contato telefônico - Falsa Central de Atendimento",
              "O golpe da Central de Atendimento é quando golpistas se passam por funcionários.\nSaiba mais: https://blog.bb.com.br/golpe-falsa-central-de-atendimento/",
              "Nunca compartilhe senhas.",
              "Ligue para o banco você mesmo." },

            { "Golpe do Falso Motoboy",
              "Golpistas pegam cartões alegando fraude.\nSaiba mais: https://blog.bb.com.br/golpe-do-falso-motoboy-saiba-como-se-proteger/",
              "Nenhum banco recolhe cartões.",
              "Nunca entregue cartões a terceiros." },

            { "Golpe da Mão Fantasma",
              "Criminosos usam acesso remoto.\nSaiba mais: https://blog.bb.com.br/como-evitar-golpes-de-acesso-remoto/",
              "Não instale apps por telefone.",
              "Desligue o celular e vá ao banco." },

            { "Golpe Módulo de Segurança",
              "Golpistas dizem para instalar módulos falsos.\nSaiba mais: https://blog.bb.com.br/golpe-do-modulo-seguranca-bb/",
              "Não instale apps via link.",
              "Use apenas lojas oficiais." },

            { "Golpe do Empréstimo Consignado",
              "Usam dados pessoais para empréstimos falsos.\nSaiba mais: https://blog.bb.com.br/veja-como-se-proteger-do-golpe-do-emprestimo-consignado/",
              "Não aceite ofertas rápidas.",
              "Consulte canais oficiais." },

            { "Golpe da Liberação de Equipamentos",
              "Pedem que vá ao caixa eletrônico.\nSaiba mais: https://blog.bb.com.br/roubaram-o-meu-celular-e-agora/",
              "Não siga instruções desconhecidas.",
              "Ligue para o banco." },

            { "Golpe do 0800",
              "Fazem você ligar para número falso.\nSaiba mais: https://blog.bb.com.br/golpe-0800/",
              "Use só números do site.",
              "Nunca retorne números enviados por SMS." },

            { "Golpe da Videochamada",
              "Pedem documentos em vídeo.\nSaiba mais: https://blog.bb.com.br/golpe-da-videochamada/",
              "Não mostre documentos.",
              "Atendimento oficial nunca pede vídeo." },

            { "Golpes por Mensagens - Links Falsos",
              "SMS com links falsos.\nSaiba mais: https://blog.bb.com.br/golpe-via-sms-rouba-dados-bancarios/",
              "Não clique em links.",
              "Abra manualmente o app do banco." },

            { "Golpes no WhatsApp",
              "Fingem ser familiares.\nSaiba mais: https://www.bb.com.br/site/pra-voce/seguranca/conheca-os-principais-golpes/",
              "Confirme por ligação.",
              "Nunca transfira sem validar identidade." },

            { "Golpe do Desenrola Brasil",
              "Links falsos sobre renegociação.\nSaiba mais: https://blog.bb.com.br/desenrola-brasil-golpes-usam-links-falsos/",
              "Verifique no site oficial.",
              "Nunca envie documentos por WhatsApp." },

            { "Catfish e Golpes Online",
              "Perfis falsos manipulam vítimas.\nSaiba mais: https://blog.bb.com.br/catfish-e-golpes-online/",
              "Pesquise fotos.",
              "Não envie dinheiro." },

            { "Golpe do Emprego",
              "Cobram taxa por vaga.\nSaiba mais: https://blog.bb.com.br/golpe-do-emprego-como-identificar-e-evitar/",
              "Vagas reais não cobram.",
              "Pesquise o CNPJ." },

            { "Golpes Pix",
              "Pressionam para enviar Pix imediato.\nSaiba mais: https://blog.bb.com.br/golpes-do-pix-saiba-como-se-proteger/",
              "Não aja sob pressão.",
              "Confira o destinatário." },

            { "Conta Laranja",
              "Usam sua conta para crimes.\nSaiba mais: https://blog.bb.com.br/entenda-o-risco-de-emprestar-sua-conta-bancaria/",
              "Nunca empreste conta.",
              "Pode virar crime grave." },

            { "Compras Online",
              "Sites muito baratos demais.\nSaiba mais: https://blog.bb.com.br/voce-esta-seguro-para-comprar-online/",
              "Verifique HTTPS.",
              "Pesquise avaliações." },

            { "Golpe do Boleto Falso",
              "Boletos adulterados.\nSaiba mais: https://blog.bb.com.br/golpe-do-boleto-falso-saiba-como-se-proteger/",
              "Confira o beneficiário.",
              "Pague pelo app oficial." },

            { "Cartão Clonado",
              "Dados copiados.\nSaiba mais: https://blog.bb.com.br/cartao-clonado-saiba-como-se-proteger/",
              "Ative alertas.",
              "Não forneça dados por telefone." }
    };
}



// ================= RELACIONAMENTOS =====================

class Relacionamentos {

    private static final Map<Integer, List<Integer>> mapa = new HashMap<>();

    static {
        mapa.put(9, Arrays.asList(7, 10, 11, 12));
        mapa.put(10, Arrays.asList(9, 12));
        mapa.put(17, Arrays.asList(16));
        mapa.put(14, Arrays.asList(10));
    }

    public static void mostrarRelacionados(int opcao) {
        List<Integer> rel = mapa.get(opcao);
        if (rel == null) {
            System.out.println("Nenhum relacionado encontrado.");
            return;
        }
        for (int r : rel) {
            System.out.println("- " + GolpeDatabase.GOLPES[r - 1][0]);
        }
    }
}



// ================= DETECÇÃO DE SMS =====================

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
            "clique no link", "bloqueada", "urgente", "pix", "transferência",
            "me ajuda", "acesso suspeito", "premio", "ganhou", "até hoje"
    );

    public DetectionResult analisarMensagem(SMSMessage m) {
        String txt = m.getText().toLowerCase();
        List<String> achados = new ArrayList<>();

        for (String p : blockList) {
            if (txt.contains(p))
                achados.add(p);
        }

        return new DetectionResult(!achados.isEmpty(), achados);
    }
}



// ================= SALVAMENTO EM ARQUIVO =====================

class MessageStorage {

    public static void salvar(SMSMessage msg, DetectionResult r) {

        try (FileWriter fw = new FileWriter("mensagens_analisadas.txt", true)) {

            fw.write("=======================================\n");
            fw.write("Mensagem analisada em: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            fw.write("Texto: " + msg.getText() + "\n");
            fw.write("Remetente: " + msg.getSender() + "\n");
            fw.write("Data recebida: " + msg.getReceivedAt() + "\n");
            fw.write("Suspeita: " + r.isSuspeita() + "\n");
            fw.write("Palavras detectadas: " + r.getPalavrasDetectadas() + "\n\n");

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}



// ================= RELATÓRIO DE SEGURANÇA ===============

class SecurityReport {
    public static String gerar(SMSMessage msg, DetectionResult r) {

        String rec = r.isSuspeita()
                ? Colors.RED + "⚠ NÃO clique em links!" + Colors.RESET
                : Colors.GREEN + "✓ Mensagem aparentemente segura." + Colors.RESET;

        return Colors.CYAN +
                "\n===== RELATÓRIO DE SEGURANÇA =====\n" +
                Colors.RESET +
                "Mensagem: " + msg.getText() + "\n" +
                "Remetente: " + msg.getSender() + "\n" +
                "Data: " + msg.getReceivedAt() + "\n" +
                "Suspeita: " + r.isSuspeita() + "\n" +
                "Padrões Encontrados: " + r.getPalavrasDetectadas() + "\n" +
                "Recomendação: " + rec + "\n";
    }
}



// ================= SOLUÇÕES PARA GOLPES =================

class Solucoes {
    public static void sugerirParaSMS(DetectionResult r) {
        System.out.println("\nRecomendações:");
        if (r.isSuspeita()) {
            System.out.println("- Apague a mensagem.");
            System.out.println("- Não clique em links.");
            System.out.println("- Bloqueie o remetente.");
        } else {
            System.out.println("- Verifique a credibilidade do remetente.");
        }
    }
}



// ==================== INTERFACE (UI) ====================

class UI {

    private static Scanner sc = new Scanner(System.in);

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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
                "│ 0 - Sair                                   │\n" +
                "└────────────────────────────────────────────┘" +
                Colors.RESET);
    }

    public static void iniciar() {
        PhishingDetector detector = new PhishingDetector();

        while (true) {
            mostrarMenuPrincipal();
            System.out.print("\nEscolha uma opção: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> submenu(1, 8, detector);
                case 2 -> submenu(9, 11, detector);
                case 3 -> submenu(12, 16, detector);
                case 4 -> submenu(14, 18, detector);
                case 0 -> {
                    clearScreen();
                    Animation.type("Encerrando sistema...", 40);
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    public static void submenu(int inicio, int fim, PhishingDetector detector) {
        while (true) {
            clearScreen();

            System.out.println(Colors.YELLOW +
                    "===== GOLPES DISPONÍVEIS =====" +
                    Colors.RESET);

            for (int i = inicio; i <= fim; i++) {
                System.out.println(i + " - " + GolpeDatabase.GOLPES[i - 1][0]);
            }

            System.out.println("\n0 - Voltar");
            System.out.print("\nEscolha um golpe: ");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 0) return;
            if (op < inicio || op > fim) continue;

            exibirGolpe(op);

            if (op == 9) analisarSMS(detector);

            System.out.print("\nENTER para continuar...");
            sc.nextLine();
        }
    }

    public static void exibirGolpe(int op) {
        clearScreen();
        String[] g = GolpeDatabase.GOLPES[op - 1];

        System.out.println(Colors.BLUE + "===== DETALHES DO GOLPE =====" + Colors.RESET);
        System.out.println("Título: " + g[0] + "\n");
        System.out.println("Descrição:\n" + g[1] + "\n");
        System.out.println("Como se proteger:\n" + g[2] + "\n");
        System.out.println("Ações práticas:\n" + g[3] + "\n");

        System.out.println(Colors.GREEN + "Golpes relacionados:" + Colors.RESET);
        Relacionamentos.mostrarRelacionados(op);
    }

    public static void analisarSMS(PhishingDetector detector) {
        System.out.print("\nDeseja analisar mensagem SMS? (s/N): ");
        String r = sc.nextLine();
        if (!r.equalsIgnoreCase("s")) return;

        System.out.print("Digite a mensagem: ");
        String msg = sc.nextLine();

        System.out.print("Remetente: ");
        String rem = sc.nextLine();

        SMSMessage sms = new SMSMessage(msg, rem);

        Animation.type("Analisando mensagem...", 25);

        DetectionResult result = detector.analisarMensagem(sms);

        Animation.type("Gerando relatório...", 25);

        System.out.println(SecurityReport.gerar(sms, result));
        Solucoes.sugerirParaSMS(result);

        // ✔ SALVA NO ARQUIVO
        MessageStorage.salvar(sms, result);

        Animation.type("\nMensagem salva em 'mensagens_analisadas.txt'.", 25);
    }
}



// ======================= MAIN ==========================

public class Main {
    public static void main(String[] args) {
        UI.iniciar();
    }
}
