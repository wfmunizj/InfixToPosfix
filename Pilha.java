//CÓDIGO DO 1°PROJETO DE ESTRUTURA DE DADOS
//ALUNO: Wellington Fernandes Muniz de Jesus
//TIA: 32147538

class PilhaChar {

    public char[] vet = new char[30];
    public int topo = 0;

    public boolean push(char dado) {
        boolean resultado = false;

        if (!isFull()) {
            vet[topo++] = dado;
            resultado = true;
        }
        return resultado;
    }

    public char pop() {
        char resultado = '0';

        if (!isEmpty()) {
            topo--;
            resultado = vet[topo];
        }
        return resultado;
    }

    public boolean isFull() {
        boolean resultado = (topo == vet.length) ? true : false;
        return resultado;
    }

    public boolean isEmpty() {
        boolean resultado = (topo == 0) ? true : false;
        return resultado;
    }
}

class PilhaInt {
    int[] elementos;
    int topo;

    public PilhaInt() {
        elementos = new int[100];
        topo = 0;
    }

    public void push(int valor) {
        elementos[topo] = valor;
        topo++;
    }

    public int pop() {
        topo--;
        return elementos[topo];
    }

    public boolean isEmpty() {
        return topo == 0;
    }
}

class PilhaDouble {
    double[] elementos;
    int topo;

    public PilhaDouble() {
        elementos = new double[100];
        topo = 0;
    }

    public void push(double valor) {
        elementos[topo++] = valor;
    }

    public double pop() {
        topo--;
        return elementos[topo];
    }

    public boolean isEmpty() {
        return topo == 0;
    }
}

