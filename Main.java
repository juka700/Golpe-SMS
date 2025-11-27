import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ======================================================
//                 PROJETO GIGANTE COMPLETO
//               SISTEMA DE DETECÇÃO DE GOLPES
//       + EDUCAÇÃO E ORIENTAÇÃO DE SEGURANÇA DIGITAL
// ======================================================

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PhishingDetector detector = new PhishingDetector();

        while (true) {
            System.out.println("\n===== MENU DE GOLPES FINANCEIROS =====");
            for (int i = 0; i < GolpeDatabase.GOLPES.length; i++) {
                System.out.println((i + 1) + " - " + GolpeDatabase.GOLPES[i][0]);
            }
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            }

            if (opcao < 1 || opcao > GolpeDatabase.GOLPES.length) {
                System.out.println("Opção inválida!");
                continue;
            }

            exibirInformacoesGolpe(opcao);

            // Se for SMS (opção 9)
            if (opcao == 9) {
                System.out.println("\nDeseja analisar uma mensagem suspeita?");
                System.out.print("Digite S para analisar ou ENTER para voltar: ");
                String escolha = sc.nextLine();

                if (escolha.equalsIgnoreCase("s")) {
                    System.out.print("\nDigite a mensagem SMS recebida: ");
                    String texto = sc.nextLine();

                    System.out.print("Digite o número do remetente: ");
                    String remetente = sc.nextLine();

                    SMSMessage msg = new SMSMessage(texto, remetente);

                    // delay para ficar mais natural
                    delay("Analisando mensagem", 5);

                    DetectionResult resultado = detector.analisarMensagem(msg);
                    String relatorio = SecurityReport.gerar(msg, resultado);

                    System.out.println(relatorio);

                    System.out.println("\n=== SOLUÇÕES SUGERIDAS ===");
                    Solucoes.sugerirParaSMS(resultado);

                    System.out.println("\n=== GOLPES RELACIONADOS ===");
                    Relacionamentos.mostrarRelacionados(9);
                }
            }

            System.out.print("\nPressione ENTER para voltar ao menu...");
            sc.nextLine();
        }

        sc.close();
    }

    public static void exibirInformacoesGolpe(int opcao) {
        String[] dados = GolpeDatabase.GOLPES[opcao - 1];

        System.out.println("\n====== DETALHES DO GOLPE ======");
        System.out.println("Título: " + dados[0]);
        System.out.println("\nDescrição:\n" + dados[1]);
        System.out.println("\nComo se proteger:\n" + dados[2]);
        System.out.println("\nAções práticas:\n" + dados[3]);

        System.out.println("\nGolpes relacionados:");
        Relacionamentos.mostrarRelacionados(opcao);
        System.out.println("===============================");
    }

    // Delay personalizado
    public static void delay(String mensagem, int pontos) {
        System.out.print(mensagem);
        for (int i = 0; i < pontos; i++) {
            try {
                Thread.sleep(450);
            } catch (Exception e) {}
            System.out.print(".");
        }
        System.out.println();
    }
}


// ======================================================
//               BANCO DE DADOS DE GOLPES
// ======================================================

class GolpeDatabase {

    public static final String[][] GOLPES = {

            {
                    "Golpes por contato telefônico - Falsa Central de Atendimento",
                    "O golpe da Central de Atendimento é quando golpistas se passam por funcionários de bancos para roubar dados.\nSaiba mais: https://blog.bb.com.br/golpe-falsa-central-de-atendimento/",
                    "Nunca compartilhe senhas.",
                    "Desligue a ligação e contate seu banco pelos canais oficiais."
            },

            {
                    "Golpe do Falso Motoboy",
                    "Golpistas pegam cartões alegando fraude.\nSaiba mais: https://blog.bb.com.br/golpe-do-falso-motoboy-saiba-como-se-proteger/",
                    "Bancos não pegam cartões na sua casa.",
                    "Jamais entregue cartões ou documentos."
            },

            {
                    "Golpe da Mão Fantasma",
                    "Golpistas usam acesso remoto simulando suporte.\nSaiba mais: https://blog.bb.com.br/como-evitar-golpes-de-acesso-remoto/",
                    "Nunca instale apps solicitados por telefone.",
                    "Desligue o aparelho e vá ao banco."
            },

            {
                    "Golpe Módulo de Segurança",
                    "Criminosos pedem instalação de módulo falso.\nSaiba mais: https://blog.bb.com.br/golpe-do-modulo-seguranca-bb/",
                    "Não instale aplicativos fora da loja oficial.",
                    "Confirme com o banco sempre que receber instruções suspeitas."
            },

            {
                    "Golpe do Empréstimo Consignado",
                    "Usam dados pessoais para empréstimos falsos.\nSaiba mais: https://blog.bb.com.br/veja-como-se-proteger-do-golpe-do-emprestimo-consignado/",
                    "Não aceite ofertas rápidas.",
                    "Utilize apenas canais oficiais."
            },

            {
                    "Golpe da Liberação de Equipamentos",
                    "Criminosos pedem que vá ao caixa eletrônico.\nSaiba mais: https://blog.bb.com.br/roubaram-o-meu-celular-e-agora/",
                    "Não siga orientações de desconhecidos.",
                    "Ligue diretamente para o banco."
            },

            {
                    "Golpe do 0800",
                    "Pedem para ligar para número falso.\nSaiba mais: https://blog.bb.com.br/golpe-0800/",
                    "Use apenas telefones do site oficial.",
                    "Nunca retorne ligações de mensagens."
            },

            {
                    "Golpe da Videochamada",
                    "Pedem para mostrar documentos por vídeo.\nSaiba mais: https://blog.bb.com.br/golpe-da-videochamada/",
                    "Não envie imagens de documentos.",
                    "Solicite atendimento oficial."
            },

            {
                    "Golpes por Mensagens - Links Falsos",
                    "Phishing com links falsos.\nSaiba mais: https://blog.bb.com.br/golpe-via-sms-rouba-dados-bancarios/",
                    "Não clique em links recebidos.",
                    "Acesse o app digitando manualmente."
            },

            {
                    "Golpes no WhatsApp",
                    "Se passam por familiares.\nSaiba mais: https://www.bb.com.br/site/pra-voce/seguranca/conheca-os-principais-golpes/",
                    "Confirme por ligação.",
                    "Nunca transfira sem validar identidade."
            },

            {
                    "Golpes Desenrola Brasil",
                    "Links falsos sobre renegociação.\nSaiba mais: https://blog.bb.com.br/desenrola-brasil-golpes-usam-links-falsos/",
                    "Confira no site oficial do governo.",
                    "Nunca envie documentos por WhatsApp."
            },

            {
                    "Catfish e Golpes Online",
                    "Perfis falsos enganam vítimas.\nSaiba mais: https://blog.bb.com.br/catfish-e-golpes-online/",
                    "Pesquise imagens no Google.",
                    "Não envie dinheiro para desconhecidos."
            },

            {
                    "Golpe do Emprego",
                    "Cobrando taxa por vaga.\nSaiba mais: https://blog.bb.com.br/golpe-do-emprego-como-identificar-e-evitar/",
                    "Vagas reais não cobram nada.",
                    "Verifique CNPJ e reputação."
            },

            {
                    "Golpes Pix",
                    "Pressão para envio imediato.\nSaiba mais: https://blog.bb.com.br/golpes-do-pix-saiba-como-se-proteger/",
                    "Não aja sob pressão.",
                    "Confirme o recebedor antes de enviar."
            },

            {
                    "Conta Laranja",
                    "Uso da conta para crimes.\nSaiba mais: https://blog.bb.com.br/entenda-o-risco-de-emprestar-sua-conta-bancaria/",
                    "Nunca empreste sua conta.",
                    "Isso pode te envolver em crimes."
            },

            {
                    "Compras Online",
                    "Sites falsos com preços baixos.\nSaiba mais: https://blog.bb.com.br/voce-esta-seguro-para-comprar-online/",
                    "Verifique HTTPS.",
                    "Pesquise avaliações."
            },

            {
                    "Golpe do Boleto Falso",
                    "Boletos adulterados.\nSaiba mais: https://blog.bb.com.br/golpe-do-boleto-falso-saiba-como-se-proteger/",
                    "Cheque o beneficiário.",
                    "Use o app oficial do banco."
            },

            {
                    "Cartão Clonado",
                    "Dados copiados indevidamente.\nSaiba mais: https://blog.bb.com.br/cartao-clonado-saiba-como-se-proteger/",
                    "Ative alertas.",
                    "Nunca informe dados por telefone."
            }
    };
}



// ======================================================
//           RELACIONAMENTOS ENTRE GOLPES
// ======================================================

class Relacionamentos {

    private static final Map<Integer, List<Integer>> mapa = new HashMap<>();

    static {
        mapa.put(9, Arrays.asList(7, 10, 11, 12)); // links falsos
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


// ======================================================
//                 SMS + PHISHING DETECTOR
// ======================================================

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

            // FRASES CLÁSSICAS
            "clique no link",
            "sua conta será bloqueada",
            "atualize seus dados",
            "ganhou um prêmio",
            "confirme seu código",
            "urgente",
            "rastreamento",
            "premio",
            "acesso suspeito",

            // PIX
            "preciso de um pix",
            "faz um pix pra mim",
            "me ajuda com um pix",
            "pix urgente",
            "faz um pix urgente",
            "pix hoje",

            // DINHEIRO
            "preciso de dinheiro",
            "estou precisando de dinheiro",
            "to precisando de dinheiro",
            "tô precisando de dinheiro",
            "me ajuda preciso de dinheiro",
            "me ajuda preciso de dinhero",

            // ERROS DE DIGITAÇÃO
            "dinhero",
            "dinheir",
            "dinehiro",
            "dinhiero",
            "dinheoro",
            "dinehro",
            "dinheru",

            // PRESSÃO / URGÊNCIA DE HORÁRIO
            "até meio dia",
            "até a uma da tarde",
            "até uma da tarde",
            "até hoje",
            "hoje ainda",
            "é urgente demais",
            "é muito urgente",
            "preciso hoje",
            "pra hoje",
            "me ajuda aqui rapidinho",
            "estou contando com você",

            // TRANSFERÊNCIA + VARIAÇÕES
            "transferência",
            "transferencia",
            "trasferencia",
            "transfrencia",
            "transferecia",
            "transfere",
            "realizar a transferência",
            "realizar a transferencia",
            "realizar a trasferencia",
            "fazer transferência",
            "fazer transferencia",
            "fazer trasferencia",
            "consegue fazer uma transferencia",
            "precisa realizar a transferencia",
            "precisa realizar a transferência",
            "preciso que faça a transferência",
            "faz a transferência pra mim",
            "faz a trasferencia pra mim"
    );

    public DetectionResult analisarMensagem(SMSMessage message) {
        String texto = message.getText().toLowerCase();
        List<String> encontrados = new ArrayList<>();

        for (String p : blockList) {
            if (texto.contains(p)) {
                encontrados.add(p);
            }
        }

        boolean suspeita = !encontrados.isEmpty();
        return new DetectionResult(suspeita, encontrados);
    }
}


// ======================================================
//             RELATÓRIO + SOLUÇÕES
// ======================================================

class SecurityReport {
    public static String gerar(SMSMessage msg, DetectionResult r) {

        String rec = r.isSuspeita()
                ? "⚠ NÃO clique em links. Apague a mensagem imediatamente."
                : "✓ Mensagem aparentemente segura.";

        return "\n===== RELATÓRIO DE SEGURANÇA =====\n" +
                "Mensagem: " + msg.getText() + "\n" +
                "Remetente: " + msg.getSender() + "\n" +
                "Data: " + msg.getReceivedAt() + "\n" +
                "Suspeita: " + r.isSuspeita() + "\n" +
                "Padrões Encontrados: " + r.getPalavrasDetectadas() + "\n" +
                "Recomendação: " + rec + "\n";
    }
}

class Solucoes {

    public static void sugerirParaSMS(DetectionResult r) {

        System.out.println("\nRecomendações práticas:");

        if (r.isSuspeita()) {
            System.out.println("- Apague a mensagem imediatamente.");
            System.out.println("- Bloqueie o remetente.");
            System.out.println("- Nunca clique em links enviados por SMS.");
            System.out.println("- Evite responder ou interagir.");
            System.out.println("- Verifique sempre no app oficial do banco.");
            System.out.println("- Compare o texto com golpes comuns no Brasil.");
        } else {
            System.out.println("Nenhum padrão crítico encontrado.");
            System.out.println("Ainda assim, verifique se o remetente é confiável.");
        }
    }
}
