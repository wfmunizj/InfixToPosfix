//CÓDIGO DO 1°PROJETO DE ESTRUTURA DE DADOS - ATIVIDADE APLICAÇÃO
//ALUNO: Wellington Fernandes Muniz de Jesus
//TIA: 32147538


import java.util.Scanner;

public class TesteInfPox {
    public static void main(String[] args) {
        // Inicializando a biblioteca Scanner e declarando os valores inciais para as
        // variáveis
        Scanner sc = new Scanner(System.in);
        String infixa = "";
        char[] variaveis = new char[26];
        double[] valores = new double[26];

        while (true) {

            int option;
            do {
                Menu();
                option = sc.nextInt();
            } while (option < 1 || option > 5);
            sc.nextLine();
            
            switch (option) {
                case 1:
                    System.out.print("Digite a expressao de forma infixa 'Ex - a*(c-b): ");
                    infixa = sc.nextLine().toUpperCase();
                    while (infixa.isEmpty()) {
                        System.out.println("Erro: Nenhuma expressão infixa foi inserida.\nInsira outra expressão:");
                        infixa = sc.nextLine().toUpperCase();
                    }
                    while (!isValid(infixa)) {
                        System.out.println("Erro: A expressão inserida é inválida.\nInsira outra expressão:\n");
                        infixa = sc.nextLine().toUpperCase();
                    }
                    break;
                case 2:
                    int index = 0;
                    for (char c : infixa.toCharArray()) {
                        if (Character.isLetter(c)) {
                            boolean found = false;
                            for (int i = 0; i < index; i++) {
                                if (variaveis[i] == c) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.print("Digite o valor para a variável " + c + ": ");
                                while (!sc.hasNextDouble()) {
                                    System.out.println("Erro: entrada inválida. Por favor, insira um número.");
                                    sc.next(); // descarta a entrada inválida
                                }
                                double valor = sc.nextDouble();
                                variaveis[index] = c;
                                valores[index] = valor;
                                index++;
                            }
                        } // Associa as variáveis as letras
                    }
                    break;
                case 3:
                    if (infixa.isEmpty()) {
                        System.out.println("Erro: Nenhuma expressão infixa foi inserida.");
                    } else if (!isValid(infixa)) {
                        System.out.println("Erro: A expressão inserida é inválida.");
                    } else {
                        String posfixa = InftoPos(infixa);
                        System.out.println("Expressão Postfixa: " + posfixa);
                    }
                    break;
                case 4:
                    if (infixa.isEmpty()) {
                        System.out.println("Erro: Nenhuma expressão infixa foi inserida.");
                    } else if (!isValid(infixa)) {
                        System.out.println("Erro: A expressão inserida é inválida.");
                    } else {
                        String posfixa = InftoPos(infixa);
                        double result = calcularValor(posfixa, variaveis, valores);
                        System.out.println("Resultado da operação: " + String.format("%.2f", result));
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção entre 1 e 5.");
            }
        }
    } // Funçaõ main para rodar o programa, mostrando os erros se houver e o resultado
      // final

    private static boolean isValid(String expressao) {
        int verificaParentese = 0;
        boolean eraVariavel = false;
        boolean eraOperador = false;

        for (char c : expressao.toCharArray()) {
            if (c == '(') {
                verificaParentese++;
                eraVariavel = false;
                eraOperador = false;
            } else if (c == ')') {
                verificaParentese--;
                if (eraOperador) {
                    return false; // O último caractere antes de um parêntese fechado foi um operador
                }
                eraVariavel = false;
            } else if (Character.isLetter(c)) {
                if (eraVariavel) {
                    return false; // Duas variáveis apareceram consecutivamente
                }
                eraVariavel = true;
                eraOperador = false;
            } else if ("+-*/^".indexOf(c) != -1) {
                if (eraOperador && !eraVariavel) {
                    return false; // O operador não tem um operando antes dele
                }
                eraVariavel = false;
                eraOperador = true;
            } else {
                return false; // Caractere inválido
            }
        }
        if (eraOperador) {
            return false; // O último caractere da expressão é um operador
        }
        return verificaParentese == 0; // A expressão é válida se todos os parênteses estiverem fechados
    }

    // Função para verificar se a expressão inserida é valida ou não
    // se tem um número igual de parênteses abertos e fechados e contém apenas
    // caracteres válidos
    // a função retorna true, indicando que a expressão é válida. Caso contrário,
    // retorna false

    private static int Prioridade(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        } else if (operador == '^') {
            return 3;
        }
        return 0;
    }// Uilizada para verificar a prioridade de cada operador matemático

    private static String InftoPos(String infixa) {
        StringBuilder posfixa = new StringBuilder();
        PilhaChar p = new PilhaChar();

        for (char c : infixa.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                posfixa.append(c);
            } else if (c == '(') {
                p.push(c);
            } else if (c == ')') {
                while (!p.isEmpty() && p.vet[p.topo - 1] != '(') {
                    posfixa.append(p.pop());
                }
                p.pop();
            } else {
                while (!p.isEmpty() && Prioridade(c) <= Prioridade(p.vet[p.topo - 1])) {
                    posfixa.append(p.pop());
                }
                p.push(c);
            }
        }

        while (!p.isEmpty()) {
            posfixa.append(p.pop());
        }

        return posfixa.toString();
    }// Converte a expressão infixa para posfixa fazendo comparações e trocas dentro
     // da pilha

    private static double calcularValor(String expressao, char[] variaveis, double[] valores) {
        PilhaDouble p = new PilhaDouble();

        for (char c : expressao.toCharArray()) {
            if (Character.isLetter(c)) {
                for (int i = 0; i < variaveis.length; i++) {
                    if (variaveis[i] == c) {
                        p.push(valores[i]);
                        break;
                    }
                }
            } else {
                double num2 = p.pop();
                double num1 = p.pop();

                switch (c) {
                    case '+':
                        p.push(num1 + num2);
                        break;
                    case '-':
                        p.push(num1 - num2);
                        break;
                    case '*':
                        p.push(num1 * num2);
                        break;
                    case '/':
                        if (num2 != 0) {
                            p.push(num1 / num2);
                        } else {
                            System.out.println("Erro de divisão por zero");
                        }
                        break;
                    case '^':
                        p.push((int) Math.pow(num1, num2)); // Necessária a conversão por conta de a função Math
                                                            // retornar valores double
                        break;
                }
            }
        }
        return p.pop();
    }// Lê os valores que foram inseridos pelo usuário e faz o calculo conforme a
     // função for inserida
     // Para qualquer outro caractere, a função retorna 0.

    public static void Menu() {
        System.out.println("\nMenu de opções:");
        System.out.println("1. Entrada da expressão aritmética na notação infixa.");
        System.out.println("2. Entrada dos valores numéricos associados às variáveis.");
        System.out.println("3. Conversão da expressão, da notação infixa para a notação posfixa.");
        System.out.println("4. Avaliação da expressão.");
        System.out.println("5. Encerramento do programa.");
        System.out.print("Escolha uma opção: ");
    }
}